package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand la commande est déjà enregistrée
 */
public final class CommandAlreadyExistException extends MeteorrComettClientException {

    public CommandAlreadyExistException(String label) {
        super(MessageLevel.ERROR, "A command with the name/label " + label + "already exist!");
    }
}
