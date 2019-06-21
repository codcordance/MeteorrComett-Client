package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand le lecteur de console est déjà défini
 */
public final class TerminalReaderAlreadySetException extends MeteorrComettClientException {

    public TerminalReaderAlreadySetException() {
        super(MessageLevel.ERROR, "The Client Terminal Reader is already set!");
    }
}
