package net.meteorr.dev.meteorrcomett.client.commands;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.console.command.ComettServerCommand;
import net.meteorr.dev.meteorrcomett.server.utils.exception.GzipIOException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.TerminalNotRunningException;
import net.meteorr.dev.meteorrcomett.server.utils.exception.ThreadGroupNotInitializedException;

import java.io.IOException;

/**
 * @author RedLux
 */
public class StopCommand extends ComettServerCommand {
    public StopCommand() {
        super("stop");
    }

    @Override
    public void execute(MeteorrComettServer instance, String[] args) throws InterruptedException, ThreadGroupNotInitializedException, TerminalNotRunningException, IOException, GzipIOException {
        instance.stop();
    }
}
