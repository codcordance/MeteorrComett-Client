package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur lors de l'initialisation du Client Logger
 */
public final class ClientLoggerInitException extends MeteorrComettClientException {

    public ClientLoggerInitException(Exception e) {
        super(MessageLevel.ERROR, "The Client Logger failed to init: " + e.getClass());
    }
}
