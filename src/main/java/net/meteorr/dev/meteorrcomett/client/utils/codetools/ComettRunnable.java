package net.meteorr.dev.meteorrcomett.client.utils.codetools;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;

/**
 * @author RedLux
 * <p>
 * Abstraction permettant de crée un runnable avec exceptions gérées
 */
public interface ComettRunnable {

    void lambda() throws Exception;

    default void run(MeteorrComettClient instance) {
        try {
            lambda();
        } catch (Exception e) {
            instance.getExceptionHandler().handle(e);
        }
    }
}
