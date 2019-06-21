package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand le console n'est pas initialisé
 */
public final class TerminalNotInitializedException extends MeteorrComettClientException {

    public TerminalNotInitializedException() {
        super(MessageLevel.ERROR, "The Client Terminal isn't initialized!");
    }
}
