package net.meteorr.dev.meteorrcomett.client.commands;

import net.meteorr.dev.meteorrcomett.server.MeteorrComettServer;
import net.meteorr.dev.meteorrcomett.server.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.console.command.ComettServerCommand;

import java.util.Arrays;

/**
 * @author RedLux
 */
public class EchoCommand extends ComettServerCommand {
    public EchoCommand() {
        super("echo");
    }

    @Override
    public void execute(MeteorrComettServer instance, String[] args) {
        final String[] s = {""};
        Arrays.asList(args).forEach(arg -> s[0] += arg + " ");
        instance.print(MessageLevel.INFO, s[0]);
    }
}
