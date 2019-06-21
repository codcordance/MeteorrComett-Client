package net.meteorr.dev.meteorrcomett.client.utils.exception;

import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;

import java.io.IOException;

/**
 * @author RedLux
 * <p>
 * Erreur IO lors d'une opération lié aux fichiers GZIP
 */
public final class GzipIOException extends MeteorrComettClientException {

    public GzipIOException(IOException io) {
        super(MessageLevel.ERROR, "An IO exception occured during the gzip operatrion:" + io.getClass());
    }
}
