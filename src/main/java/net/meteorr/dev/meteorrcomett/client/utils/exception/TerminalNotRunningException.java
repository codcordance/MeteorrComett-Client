package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand le console n'est pas en cours
 */
public final class TerminalNotRunningException extends MeteorrComettClientException {

    public TerminalNotRunningException() {
        super(MessageLevel.ERROR, "The Client Terminal isn't running / started (yet)!");
    }
}
