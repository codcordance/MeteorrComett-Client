package net.meteorr.dev.meteorrcomett.client.commands;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;
import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.client.console.command.ComettClientCommand;
import net.meteorr.dev.meteorrcomett.client.utils.ThreadsUtils;
import net.meteorr.dev.meteorrcomett.client.utils.exception.ThreadGroupNotInitializedException;

import java.util.List;

/**
 * @author RedLux
 */
public class ListThreadCommand extends ComettClientCommand {
    public ListThreadCommand() {
        super("listthread");
    }

    @Override
    public void execute(MeteorrComettClient instance, String[] args) throws ThreadGroupNotInitializedException {
        List<Thread> threads = ThreadsUtils.getGroupThreads(instance.getThreadGroup());
        final String[] s = {""};
        threads.forEach(thread -> s[0] += "--> " + thread.getName() + " (" + thread.getState() + "): " + thread.getClass().getName() + "\n");
        instance.print(MessageLevel.DEBUG, "Il y a actuellement " + threads.size() + " sous-threads: ", s[0]);
    }
}
