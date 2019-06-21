package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur lors de l'écriture par le Client Logger
 */
public final class ClientLoggerWriteException extends MeteorrComettClientException {

    public ClientLoggerWriteException(Exception e) {
        super(MessageLevel.WARNING, "The Client Logger failed to write: " + e.getClass());
    }
}
