package net.meteorr.dev.meteorrcomett.client.commands;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;
import net.meteorr.dev.meteorrcomett.client.console.command.ComettClientCommand;
import net.meteorr.dev.meteorrcomett.client.utils.exception.GzipIOException;
import net.meteorr.dev.meteorrcomett.client.utils.exception.TerminalNotRunningException;
import net.meteorr.dev.meteorrcomett.client.utils.exception.ThreadGroupNotInitializedException;

import java.io.IOException;

/**
 * @author RedLux
 */
public class StopCommand extends ComettClientCommand {
    public StopCommand() {
        super("stop");
    }

    @Override
    public void execute(MeteorrComettClient instance, String[] args) throws InterruptedException, ThreadGroupNotInitializedException, TerminalNotRunningException, IOException, GzipIOException {
        instance.stop();
    }
}
