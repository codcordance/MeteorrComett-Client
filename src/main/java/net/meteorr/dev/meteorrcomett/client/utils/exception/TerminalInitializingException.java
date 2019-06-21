package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand le console s'initialise
 */
public final class TerminalInitializingException extends MeteorrComettClientException {

    public TerminalInitializingException(Exception e) {
        super(MessageLevel.ERROR, "An error occured during the console initializing : " + e.getClass());
    }
}
