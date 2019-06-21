package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand une commande avec un nom déjà existant tente d'être enregistré
 */
public final class CommandAlreadyRegisteredException extends MeteorrComettClientException {

    public CommandAlreadyRegisteredException() {
        super(MessageLevel.ERROR, "The command is already registered!");
    }
}
