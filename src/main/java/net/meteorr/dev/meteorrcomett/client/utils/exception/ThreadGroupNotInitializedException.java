package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Erreur quand le groupe de thread n'est pas initialisé
 */
public final class ThreadGroupNotInitializedException extends MeteorrComettClientException {

    public ThreadGroupNotInitializedException() {
        super(MessageLevel.ERROR, "The Client Thread Group isn't initialized!");
    }
}
