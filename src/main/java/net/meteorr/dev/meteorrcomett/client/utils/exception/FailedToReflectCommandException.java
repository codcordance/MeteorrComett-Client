package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand la commande n'a pas pu être chargé avec reflection
 */
public final class FailedToReflectCommandException extends MeteorrComettClientException {

    public FailedToReflectCommandException(Exception e) {
        super(MessageLevel.ERROR, "Error occured during the command reflection loading :" + e.getClass());
    }
}
