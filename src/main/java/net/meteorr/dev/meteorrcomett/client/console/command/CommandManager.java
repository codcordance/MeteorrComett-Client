package net.meteorr.dev.meteorrcomett.client.console.command;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;
import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.client.utils.exception.CommandAlreadyExistException;
import net.meteorr.dev.meteorrcomett.client.utils.exception.CommandAlreadyRegisteredException;
import net.meteorr.dev.meteorrcomett.client.utils.exception.ThreadGroupNotInitializedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author RedLux
 */
public class CommandManager {
    private final MeteorrComettClient instance;
    private List<ComettClientCommand> commands;
    private CommandExecutor commandExecutor;

    public CommandManager(MeteorrComettClient instance) {
        this.instance = instance;
        this.commands = new ArrayList<>();
        this.commandExecutor = null;
    }

    public void initExecutor() throws ThreadGroupNotInitializedException {
        this.commandExecutor = new CommandExecutor(getInstance());
        getInstance().print(MessageLevel.INFO, "Starting executor...");
        this.commandExecutor.start();
        getInstance().print(MessageLevel.INFO, "$GREENExecutor successfully started!");
    }

    public void registerCommand(ComettClientCommand command) {
        getInstance().print(MessageLevel.INFO, "Registering command " + command.getLabel() + " with class " + command.getClass() + "...");
        getCommands().forEach(cmd -> {
            if (cmd.getLabel().equals(command.getLabel())) try {
                throw new CommandAlreadyExistException(command.getLabel());
            } catch (CommandAlreadyExistException e) {
                getInstance().getExceptionHandler().handle(e);
                getInstance().print(MessageLevel.INFO, "$REDFailed to register command " + command.getLabel() + " with class " + command.getClass() + "!");
            }
        });
        if (!commands.contains(command)) {
            commands.add(command);
            getInstance().print(MessageLevel.INFO, "$GREENSuccessfully registered command " + command.getLabel() + " with class " + command.getClass() + "!");
        } else {
            try {
                throw new CommandAlreadyRegisteredException();
            } catch (CommandAlreadyRegisteredException e) {
                getInstance().getExceptionHandler().handle(e);
                getInstance().print(MessageLevel.INFO, "$REDFailed to register command " + command.getLabel() + " with class " + command.getClass() + "!");
            }
        }
    }

    public synchronized void proceed(String input) throws ThreadGroupNotInitializedException, IOException {
        if (input.equals("")) return;
        getInstance().getClientLogger().write(MessageLevel.DEBUG, "Command input: " + input);
        String label = input.contains(" ") ? input.split(" ")[0] : input;
        String[] args = input.contains(" ") ? input.split(label + " ")[1].split(" ") : new String[0];
        AtomicBoolean success = new AtomicBoolean(false);
        getCommands().forEach(command -> {
            if (command.getLabel().equalsIgnoreCase(label)) {
                getCommandExecutor().execute(command, args);
                success.set(true);
            }
        });
        if (!success.get()) getInstance().print(MessageLevel.WARNING, "No command found for : " + label);
    }

    public MeteorrComettClient getInstance() {
        return this.instance;
    }

    public List<ComettClientCommand> getCommands() {
        return commands;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }
}
