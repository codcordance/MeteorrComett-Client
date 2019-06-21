package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand le console est déjà initialisé
 */
public final class TerminalAlreadyInitializedException extends MeteorrComettClientException {
    public TerminalAlreadyInitializedException() {
        super(MessageLevel.WARNING, "The Client Terminal is already initialized!");
    }
}
