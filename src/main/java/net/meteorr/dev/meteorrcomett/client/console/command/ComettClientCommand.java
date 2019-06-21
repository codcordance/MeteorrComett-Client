package net.meteorr.dev.meteorrcomett.client.console.command;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;

/**
 * @author RedLux
 */
public abstract class ComettClientCommand {
    private final String label;

    public ComettClientCommand(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public abstract void execute(MeteorrComettClient instance, String[] args) throws Exception;
}
