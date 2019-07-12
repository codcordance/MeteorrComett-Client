package net.meteorr.dev.meteorrcomett.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * @author RedLux
 * <p>
 * Boostrap du client MeteorrComett
 */
public final class MeteorrComettClientBoostrap {

    public static void main(String[] args) throws Exception {
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int arg0) {
            }
        }));
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int arg0) {
            }
        }));
        System.setIn(new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        });
        new MeteorrComettClient().start(Arrays.asList(args));
    }
}
