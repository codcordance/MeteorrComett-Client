package net.meteorr.dev.meteorrcomett.client;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.client.console.command.ComettClientCommand;
import net.meteorr.dev.meteorrcomett.client.console.command.CommandManager;
import net.meteorr.dev.meteorrcomett.client.console.logger.ClientLogger;
import net.meteorr.dev.meteorrcomett.client.console.terminal.ClientTerminal;
import net.meteorr.dev.meteorrcomett.client.console.terminal.TerminalReader;
import net.meteorr.dev.meteorrcomett.client.utils.ReflectionUtils;
import net.meteorr.dev.meteorrcomett.client.utils.ThreadsUtils;
import net.meteorr.dev.meteorrcomett.client.utils.annotations.MeteorrComettImportantThread;
import net.meteorr.dev.meteorrcomett.client.utils.annotations.MeteorrComettWaitableThread;
import net.meteorr.dev.meteorrcomett.client.utils.codetools.ComettRunnable;
import net.meteorr.dev.meteorrcomett.client.utils.codetools.ExceptionHandler;
import net.meteorr.dev.meteorrcomett.client.utils.codetools.WaitableInlineThread;
import net.meteorr.dev.meteorrcomett.client.utils.exception.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author RedLux
 *
 * Classe principale du serveur MeteorrComett
 */
public class MeteorrComettClient {

    private final MeteorrComettClient instance;
    private Boolean running;
    private ExceptionHandler exceptionHandler;
    private ClientTerminal clientTerminal;
    private CommandManager commandManager;
    private ThreadGroup threadGroup;
    private ClientLogger clientLogger;
    private Boolean checked;

    public MeteorrComettClient() {
        instance = this;
        running = false;
        exceptionHandler = null;
        clientTerminal = null;
        commandManager = null;
        threadGroup = null;
        checked = false;
    }

    public synchronized void start(List<String> args) throws InterruptedException, ThreadGroupNotInitializedException, IOException, GzipIOException, TerminalNotRunningException {
        this.exceptionHandler = new ExceptionHandler(getInstance());
        this.clientTerminal = new ClientTerminal(getInstance());
        this.threadGroup = null;
        try {
            getClientTerminal().init();
        } catch (Exception e) {
            this.getExceptionHandler().handle(e);
        }
        initThreadGroup();
        initClientLogger();
        initCommandManager();
        initCommandExecutor();
        initCommands();
        initTerminalReader();
        if (!args.contains("--nocheck")) {
            checked = false;
            print(MessageLevel.INFO, "You're running the MeteorrComett $PURPLECLIENT");
            print(MessageLevel.INFO, "Please enter 'yes' below if you agree and want to run it or 'no' otherwise.");
            wait();
        } else checked = true;
        if (!checked) {
            print(MessageLevel.WARNING, "The input was not 'no', stopping program...");
            stop();
        } else print(MessageLevel.INFO, "The input was not 'yes', running program!");
    }

    public synchronized void checkconsume(boolean result) {
        this.checked = result;
        notify();
    }

    public void print(MessageLevel level, String... content) {
        try {
            getClientTerminal().print(level, content);
        } catch (Exception e) {
            this.getExceptionHandler().handle(e);
        }
    }

    private void initComponent(String componentName, ComettRunnable lambdainit) {
        getInstance().print(MessageLevel.INFO, "Initializing " + componentName + "...");
        try {
            lambdainit.run(getInstance());
        } catch (Exception e) {
            try {
                getInstance().print(MessageLevel.INFO, componentName + " initialization $REDfailed$RESET!");
                throw new ComponentFailedToInitializeException(e);
            } catch (ComponentFailedToInitializeException ex) {
                ex.printStackTrace();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            getExceptionHandler().handle(e);
        }
        getInstance().print(MessageLevel.INFO, componentName + " initialization $GREENsucceed$RESET!");

    }

    private void initCommandManager() {
        initComponent("CommandManager", () -> this.commandManager = new CommandManager(getInstance()));
    }

    private void initCommandExecutor() {
        initComponent("CommandExecutor", () -> this.getCommandManager().initExecutor());
    }

    private void initClientLogger() {
        initComponent("ClientLogger", () -> this.clientLogger = new ClientLogger(getInstance()));
    }

    private void initThreadGroup() {
        initComponent("ThreadGroup", () -> this.threadGroup = new ThreadGroup("MeteorrComettClient"));
    }

    private void initTerminalReader() {
        initComponent("TerminalReader", () -> {
            getInstance().print(MessageLevel.INFO, "Setting terminal reader...");
            getClientTerminal().setTerminalReader(new TerminalReader(getInstance(), getClientTerminal().getTerminal()));
            getInstance().print(MessageLevel.INFO, "Initializing terminal reader...");
            TimeUnit.SECONDS.sleep(2);
            getClientTerminal().initReader();
        });
    }

    private void initCommands() {
        initComponent("Commands", () -> {
            Arrays.asList(ReflectionUtils.getClasses("net.meteorr.dev.meteorrcomett.client.commands")).forEach(aClass -> {
                try {
                    getCommandManager().registerCommand((ComettClientCommand) aClass.getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    try {
                        throw new FailedToReflectCommandException(e);
                    } catch (FailedToReflectCommandException ex) {
                        getExceptionHandler().handle(e);
                    }
                }
            });
        });
    }

    public synchronized void stop() throws TerminalNotRunningException, InterruptedException, ThreadGroupNotInitializedException, IOException, GzipIOException {
        getInstance().print(MessageLevel.INFO, "Stopping...");
        getClientTerminal().stopReader();
        getInstance().print(MessageLevel.INFO, "Ending $YELLOWCommandExecutor$RESET...");
        getCommandManager().getCommandExecutor().end();
        getInstance().print(MessageLevel.INFO, "Ended $GREENCommandExecutor$RESET!");
        getInstance().print(MessageLevel.INFO, "Intterupting non-importants threads...");
        List<Thread> threads = ThreadsUtils.getGroupThreads(getInstance().getThreadGroup());
        threads.forEach(thread -> {
            if (!thread.getClass().isAnnotationPresent(MeteorrComettImportantThread.class)) {
                if (thread.getClass().isAnnotationPresent(MeteorrComettWaitableThread.class) || thread instanceof WaitableInlineThread) {
                    long timeout = (thread instanceof WaitableInlineThread ? WaitableInlineThread.class : thread.getClass()).getAnnotation(MeteorrComettWaitableThread.class).timeout();
                    try {
                        getInstance().print(MessageLevel.INFO, "Joinin thread $GREEN" + thread.getClass().getName() + "$RESET for " + timeout + " miliseconds...");
                        long l = 0;
                        while (l < timeout && thread.isAlive()) {
                            wait(1);
                            l++;
                        }
                        if (!thread.isAlive())
                            getInstance().print(MessageLevel.INFO, "Thread $GREEN" + thread.getClass().getName() + "$RESET joined and died!");
                        else {
                            getInstance().print(MessageLevel.INFO, "Intterupting thread $YELLOW" + thread.getClass().getName() + "$RESET...");
                            thread.interrupt();
                            getInstance().print(MessageLevel.INFO, "Intterupted thread $GREEN" + thread.getClass().getName() + "$RESET!");
                        }
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    getInstance().print(MessageLevel.INFO, "Intterupting thread $YELLOW" + thread.getClass().getName() + "$RESET...");
                    thread.interrupt();
                    getInstance().print(MessageLevel.INFO, "Intterupted thread $GREEN" + thread.getClass().getName() + "$RESET!");
                }
                if (thread.isAlive()) {
                    getInstance().print(MessageLevel.CRITICAL, "FORCING THREAD " + thread.getClass().getName() + " TO DIE $RESET!");
                    thread.stop();
                    getInstance().print(MessageLevel.WARNING, "Forced thread $YELLOW" + thread.getClass().getName() + " to die $RESET!");
                }
            }
        });
        getInstance().print(MessageLevel.INFO, "$GREENIntterupted all non important threads!");
        getInstance().print(MessageLevel.INFO, "Ending $YELLOWClientLogger$RESET...");
        getClientLogger().stop();
        getInstance().print(MessageLevel.INFO, "Ended $GREENClientLogger$RESET!");
        getClientTerminal().stop();
        System.out.println("Stopped.");
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public MeteorrComettClient getInstance() {
        return this.instance;
    }

    public ClientTerminal getClientTerminal() {
        return this.clientTerminal;
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public boolean isRunning() {
        return this.running;
    }

    public ThreadGroup getThreadGroup() throws ThreadGroupNotInitializedException {
        if (this.threadGroup == null) throw new ThreadGroupNotInitializedException();
        return this.threadGroup;
    }

    public ClientLogger getClientLogger() {
        return this.clientLogger;
    }

    public boolean isChecked() {
        return this.checked;
    }
}
