package net.meteorr.dev.meteorrcomett.client.console.command;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;
import net.meteorr.dev.meteorrcomett.client.utils.annotations.MeteorrComettImportantThread;
import net.meteorr.dev.meteorrcomett.client.utils.exception.ThreadGroupNotInitializedException;

/**
 * @author RedLux
 */
@MeteorrComettImportantThread(name = "MeteorrComettClientCommandExecutor")
public class CommandExecutor extends Thread {
    private final MeteorrComettClient instance;
    private ComettClientCommand command;
    private String[] args;
    private boolean running;

    public CommandExecutor(MeteorrComettClient instance) throws ThreadGroupNotInitializedException {
        super(instance.getThreadGroup(), "MeteorrComettClientCommandExecutor");
        this.instance = instance;
        this.command = null;
        this.args = null;
        this.running = false;
    }

    @Override
    public synchronized void start() {
        super.start();
        this.running = true;
    }

    public synchronized ComettClientCommand getCommand() {
        return command;
    }

    public synchronized String[] getArgs() {
        return args;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized MeteorrComettClient getInstance() {
        return instance;
    }

    @Override
    public synchronized void run() {
        super.run();
        try {
            wait();
        } catch (InterruptedException e) {
            getInstance().getExceptionHandler().handle(e);
        }
        do {
            try {
                getCommand().execute(getInstance(), getArgs());
            } catch (Exception e) {
                if (getInstance().isChecked()) getInstance().getExceptionHandler().handle(e);
            }
            this.args = null;
            this.command = null;
            try {
                if (isRunning()) wait();
            } catch (InterruptedException e) {
                getInstance().getExceptionHandler().handle(e);
            }
        } while (isRunning());
    }

    public synchronized void end() {
        this.running = false;
        this.notify();
    }


    public synchronized void execute(ComettClientCommand command, String[] args) {
        this.command = command;
        this.args = args;
        this.notify();
    }
}
