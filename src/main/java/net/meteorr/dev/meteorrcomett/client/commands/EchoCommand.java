package net.meteorr.dev.meteorrcomett.client.commands;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;
import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.client.console.command.ComettClientCommand;

import java.util.Arrays;

/**
 * @author RedLux
 */
public class EchoCommand extends ComettClientCommand {
    public EchoCommand() {
        super("echo");
    }

    @Override
    public void execute(MeteorrComettClient instance, String[] args) {
        final String[] s = {""};
        Arrays.asList(args).forEach(arg -> s[0] += arg + " ");
        instance.print(MessageLevel.INFO, s[0]);
    }
}
