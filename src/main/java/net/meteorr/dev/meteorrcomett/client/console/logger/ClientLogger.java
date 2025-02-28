package net.meteorr.dev.meteorrcomett.client.console.logger;

import net.meteorr.dev.meteorrcomett.client.MeteorrComettClient;
import net.meteorr.dev.meteorrcomett.client.console.MessageLevel;
import net.meteorr.dev.meteorrcomett.client.utils.ClockTime;
import net.meteorr.dev.meteorrcomett.client.utils.GzipUtils;
import net.meteorr.dev.meteorrcomett.client.utils.exception.ClientLoggerInitException;
import net.meteorr.dev.meteorrcomett.client.utils.exception.GzipIOException;
import net.meteorr.dev.meteorrcomett.client.utils.exception.ThreadGroupNotInitializedException;

import java.io.File;
import java.io.IOException;

/**
 * @author RedLux
 */
public class ClientLogger {
    private final MeteorrComettClient instance;
    private final File logFile;
    private String startDate;
    private boolean running;

    public ClientLogger(MeteorrComettClient instance) throws ClientLoggerInitException {
        this.running = false;
        File logFile;
        this.instance = instance;
        try {
            logFile = init();
        } catch (Exception e) {
            throw new ClientLoggerInitException(e);
        }
        this.logFile = logFile;
        this.running = true;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File init() throws IOException {
        getInstance().print(MessageLevel.INFO, "Initializing Client Logger file...");
        this.startDate = ClockTime.getClockTimeLogger();
        getInstance().print(MessageLevel.INFO, "Getting directory...");
        File dir = new File("logs/");
        if (!dir.exists()) {
            getInstance().print(MessageLevel.INFO, "Creating it...");
            dir.mkdir();
            getInstance().print(MessageLevel.INFO, "$GREENCreated!");
        } else getInstance().print(MessageLevel.INFO, "$GREENDirectory exists!");
        getInstance().print(MessageLevel.INFO, "Getting log file...");
        File log = new File("logs/" + getStartDate() + ".log");
        if (!log.exists()) {
            getInstance().print(MessageLevel.INFO, "Creating it...");
            log.createNewFile();
            getInstance().print(MessageLevel.INFO, "$GREENCreated!");
        } else {
            getInstance().print(MessageLevel.INFO, "$YELLOWFile exists! Deleting...");
            log.delete();
            getInstance().print(MessageLevel.INFO, "$GREENDeleted!$RESET Creationg it...");
            log.createNewFile();
            getInstance().print(MessageLevel.INFO, "$GREENCreated!");
        }
        getInstance().print(MessageLevel.DEBUG, "Logging on " + log.getAbsolutePath());
        return log;
    }

    public void write(MessageLevel level, String... content) throws IOException, ThreadGroupNotInitializedException {
        if (!this.isRunning()) return;
        new LoggerWritter(getInstance(), getLogFile()).log(level, content).start();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void stop() throws IOException, GzipIOException {
        if (!logFile.exists()) return;
        getInstance().print(MessageLevel.INFO, "Getting log archive...");
        File logarchive = new File(getLogFile().getAbsolutePath() + ".gz");
        if (!logarchive.exists()) {
            getInstance().print(MessageLevel.INFO, "Creating it...");
            logarchive.createNewFile();
            getInstance().print(MessageLevel.INFO, "$GREENCreated!");
        } else {
            getInstance().print(MessageLevel.INFO, "$YELLOWFile exists! Deleting...");
            logarchive.delete();
            getInstance().print(MessageLevel.INFO, "$GREENDeleted!$RESET Creationg it...");
            logarchive.createNewFile();
            getInstance().print(MessageLevel.INFO, "$GREENCreated!");
        }
        getInstance().print(MessageLevel.DEBUG, "Log archive on " + logarchive.getAbsolutePath());
        this.running = false;
        getInstance().print(MessageLevel.INFO, "Compressing current log into archive...");
        GzipUtils.compressGZIP(getLogFile(), logarchive);
        getInstance().print(MessageLevel.INFO, "$GREENCompressed!");
        getInstance().print(MessageLevel.INFO, "Deleting log file...");
        getLogFile().delete();
        getInstance().print(MessageLevel.INFO, "$GREENDeleted!");
    }

    public MeteorrComettClient getInstance() {
        return instance;
    }

    public File getLogFile() {
        return logFile;
    }

    public String getStartDate() {
        return startDate;
    }

    public boolean isRunning() {
        return running;
    }
}
