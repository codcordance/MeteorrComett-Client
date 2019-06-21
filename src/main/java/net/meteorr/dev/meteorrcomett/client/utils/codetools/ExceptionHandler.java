package net.meteorr.dev.meteorrcomett.client.utils.codetools;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;
import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.client.utils.exception.MeteorrComettClientException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author RedLux
 */
public class ExceptionHandler {
    private final MeteorrComettClient instance;

    public ExceptionHandler(MeteorrComettClient instance) {
        this.instance = instance;
    }

    public void handle(Exception e) {
        try {
            if (getInstance().getClientTerminal().isInitialized()) {
                if (e instanceof MeteorrComettClientException) {
                    getInstance().getClientTerminal().print(((MeteorrComettClientException) e).getLevel(), "Exception Handled: " + e.getMessage() + " {" + e.getClass().getSimpleName() + "}", getStackTrace(e));
                } else
                    getInstance().getClientTerminal().print(MessageLevel.ERROR, "Exception Occured: " + e.getMessage() + " {" + e.getClass().getSimpleName() + "}", getStackTrace(e));
            } else e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getStackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    public MeteorrComettClient getInstance() {
        return this.instance;
    }
}
