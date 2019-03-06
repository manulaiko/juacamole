package com.manulaiko.juacamole;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ModuleTest extends Module {

    @Test
    public void main() {
        log.info("Instantiating module...");
        var module = new ModuleTest();

        assertEquals(module.getStatus(), Status.INSTANCED);

        log.info("Starting module...");
        module.start();

        assertEquals(module.getStatus(), Status.STARTING);

        log.info("Waiting...");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            // ignore
        }

        assertEquals(module.getStatus(), Status.STARTING);

        log.info("Waiting more...");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            // ignore
        }

        if (module.getStatus().ordinal() >= Status.STOPPING.ordinal()) {
            log.info("Module stopped!");

            return;
        }

        assertEquals(module.getStatus(), Status.STARTED);

        log.info("Stopping module...");
        module.stop();

        assertEquals(module.getStatus(), Status.STOPPED);
    }

    /**
     * Called when the constructor has finished.
     *
     * It should be used to initialize the module's
     * specific properties.
     */
    @Override
    public void onInstanced() {
        assertEquals(super.getStatus(), Status.NULL);

        log.info("Instanced");
    }

    /**
     * Called when the module is started.
     *
     * It contains the module logic.
     */
    @Override
    public void onStarted() {
        assertEquals(super.getStatus(), Status.STARTING);

        log.info("Starting..., make it busy");
        log.info("Daemon status: "+ Thread.currentThread().isDaemon());

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the module is going to be stopped.
     *
     * It should be used to clean up properties.
     */
    @Override
    public void onStopped() {
        assertEquals(super.getStatus(), Status.STOPPING);

        log.info("Stopped.");
    }

    /**
     * Called when an exception is thrown.
     *
     * It should be handled here.
     *
     * @param e Exception thrown.
     */
    @Override
    public void onException(Exception e) {
        log.info("Exception!");
        e.printStackTrace();
    }
}