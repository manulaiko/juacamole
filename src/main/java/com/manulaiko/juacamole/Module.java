package com.manulaiko.juacamole;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.greenrobot.eventbus.EventBus;

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
     * Event bus instance.
     */
    private EventBus eventBus = EventBus.getDefault();

    /**
     * Constructor.
     */
    public Module() {
        this.onInstanced();
        this.status = Status.INSTANCED;
    }

    /**
     * Starts the module.
     */
    public final void start() {
        if (this.getStatus().ordinal() > 1 && this.getStatus() != Status.STOPPED) {
            log.warn("Module already started!");

            return;
        }

        this.status = Status.STARTING;
    }

    /**
     * Starts the module.
     */
    public final void run() {
        if (this.getStatus() != Status.STARTING && this.getStatus() != Status.STOPPED) {
            log.warn("Illegal state! Expected STARTING|STOPPING found " + this.getStatus());

            return;
        }

        try {
            this.onStarted();

            if (this.status == Status.STARTING) {
                this.status = Status.STARTED;
            }
        } catch (Exception e) {
            this.onException(e);
        }

        if (this.getStatus().ordinal() < Status.STOPPING.ordinal()) {
            this.stop();
        }
    }

    /**
     * Stops the module.
     */
    public final void stop() {
        if (this.getStatus() != Status.STARTED) {
            log.warn("Illegal state! Expected STARTED, found "+ this.getStatus());

            return;
        }

        this.status = Status.STOPPING;

        this.onStopped();

        this.status = Status.STOPPED;
    }

    /**
     * Registers a new event listener.
     *
     * @param listener Event listener to register.
     */
    protected final void register(EventListener listener) {
        this.eventBus.register(listener);
    }

    /**
     * Posts a new event in the bus.
     *
     * @param event Event to post.
     */
    public final void post(Event event) {
        this.eventBus.post(event);
    }
}
