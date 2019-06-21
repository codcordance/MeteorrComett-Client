package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand le console est déjà en cours
 */
public final class TerminalAlreadyRunningException extends MeteorrComettClientException {

    public TerminalAlreadyRunningException() {
        super(MessageLevel.ERROR, "The Client Terminal is already running and started!");
    }
}
