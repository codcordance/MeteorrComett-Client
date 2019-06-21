package net.meteorr.dev.meteorrcomett.client.console.logger;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;
import net.meteorr.dev.meteorrcomett.client.console.ColorCode;
import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.client.utils.ClockTime;
import net.meteorr.dev.meteorrcomett.client.utils.annotations.MeteorrComettWaitableThread;
import net.meteorr.dev.meteorrcomett.client.utils.exception.ClientLoggerWriteException;
import net.meteorr.dev.meteorrcomett.client.utils.exception.ThreadGroupNotInitializedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author RedLux
 */
@MeteorrComettWaitableThread(timeout = 1000)
public class LoggerWritter extends Thread {
    private final MeteorrComettClient instance;
    private FileWriter fileWriter;
    private MessageLevel messageLevel;
    private String[] content;

    public LoggerWritter(MeteorrComettClient instance, File fileWriter) throws IOException, ThreadGroupNotInitializedException {
        super(instance.getThreadGroup(), "MeteorrComettClientLoggerWritter");
        this.instance = instance;
        this.fileWriter = new FileWriter(fileWriter, true);
    }

    public LoggerWritter log(MessageLevel level, String... content) {
        this.messageLevel = level;
        this.content = content;
        return this;
    }

    public void run() {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(ClockTime.getClockTime());
            builder.append(" [");
            builder.append(getMessageLevel().getIdentifier());
            builder.append("] > ");
            for (String line : getContent()) builder.append(line).append("\n");
            String s = builder.toString();
            for (ColorCode c : ColorCode.values()) s = s.replace("$" + c.toString(), "");
            for (ColorCode c : ColorCode.values()) s = s.replace(c.getAsciiCode(), "");
            getFileWriter().write(s);
            getFileWriter().close();
        } catch (IOException e) {
            try {
                throw new ClientLoggerWriteException(e);
            } catch (ClientLoggerWriteException ex) {
                getInstance().getExceptionHandler().handle(ex);
            }
        }
    }

    public MeteorrComettClient getInstance() {
        return instance;
    }

    public FileWriter getFileWriter() {
        return fileWriter;
    }

    public MessageLevel getMessageLevel() {
        return messageLevel;
    }

    public String[] getContent() {
        return content;
    }
}
