package com.manulaiko.juacamole;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Module class.
 * =============
 *
 * Base class for all modules.
 *
 * Each module should define it's life-cycle with the different methods.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Slf4j
public class Module implements Runnable, ModuleLifeCycle {
    /**
     * Current lifecycle state.
     */
    @Getter
    private Status status = Status.NULL;

    /**
     * Thread in which the module is running.
     */
    private Thread thread;

    /**
     * Constructor.
     */
    public Module() {
        this.thread = new Thread(
                this,
                this.getClass()
                    .getSimpleName()
        );

        this.onInstanced();
        this.status = Status.INSTANCED;
    }

    /**
     * Starts the module.
     */
    public final void start() {
        if (this.getStatus().ordinal() > 1) {
            log.warn("Module already started!");

            return;
        }

        this.status = Status.STARTING;

        this.thread.start();
    }

    /**
     * Starts the module.
     */
    public final void run() {
        if (this.getStatus() != Status.STARTING || this.getStatus() != Status.STOPPED) {
            log.warn("Illegal state!");

            return;
        }

        try {
            this.onStarted();
        } catch (Exception e) {
            this.onException(e);
        }

        if (this.getStatus().ordinal() < Status.STOPPING.ordinal()
        ) {
            this.stop();
        }
    }

    /**
     * Stops the module.
     */
    public final void stop() {
        if (this.getStatus() != Status.STARTED) {
            log.warn("Illegal state!");

            return;
        }

        this.status = Status.STOPPING;

        this.onStopped();

        this.status = Status.STOPPED;
    }
}
