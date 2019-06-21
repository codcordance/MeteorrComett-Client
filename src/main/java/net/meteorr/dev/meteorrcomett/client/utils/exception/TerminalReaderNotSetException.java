package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand le lecteur de console n'est pas défini
 */
public final class TerminalReaderNotSetException extends MeteorrComettClientException {

    public TerminalReaderNotSetException() {
        super(MessageLevel.ERROR, "The Client Terminal Reader isn't set!");
    }
}
