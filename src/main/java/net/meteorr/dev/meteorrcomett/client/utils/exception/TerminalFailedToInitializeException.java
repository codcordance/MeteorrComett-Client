package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand le console n'a pas réussi à s'initialiser
 */
public final class TerminalFailedToInitializeException extends MeteorrComettClientException {

    public TerminalFailedToInitializeException() {
        super(MessageLevel.CRITICAL, "The Client Terminal failed to initialize!");
    }
}
