package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

/**
 * @author RedLux
 * <p>
 * Classe servant de modèle pour les exceptions personnalisées de MeteorrComett
 */
public class MeteorrComettClientException extends Exception {
    private MessageLevel level;

    public MeteorrComettClientException(MessageLevel level, String message) {
        super(message);
        this.level = level;
    }

    public MessageLevel getLevel() {
        return level;
    }
}
