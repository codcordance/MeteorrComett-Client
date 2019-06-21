package net.meteorr.dev.meteorrcomett.client;

import java.util.Arrays;

/**
 * @author RedLux
 * <p>
 * Boostrap du client MeteorrComett
 */
public final class MeteorrComettClientBoostrap {

    public static void main(String[] args) throws Exception {

        new MeteorrComettClient().start(Arrays.asList(args));
    }
}
