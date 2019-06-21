package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand un composant du seveur n'a pas réussi à s'initialiser
 */
public final class ComponentFailedToInitializeException extends MeteorrComettClientException {

    public ComponentFailedToInitializeException(Exception e) {
        super(MessageLevel.CRITICAL, "A component of the client failed to initialize: " + e.getClass());
    }
}
