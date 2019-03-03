package com.manulaiko.juacamole;

/**
 * Module lifecycle.
 * =================
 *
 * Defines the lifecycle methods of a module.
 *
 * These are:
 *
 *  * `onInstanced`: Called when the module has been instantiated.
 *  * `onStarted`: Called when the module has been started.
 *  * `onStopped`: Called when the module has been stopped.
 *  * `onException`: Called when the module fires an uncaught exception.
 *
 * The current state of the lifecycle can be obtained with the `getStatus` method.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public interface ModuleLifeCycle {
    /**
     * Status type.
     *
     * Defines the different state of the lifecycle.
     */
    enum Status {
        /**
         * Default state.
         *
         * `onInstanced` hasn't been called yet.
         */
        NULL,

        /**
         * Instanced state.
         *
         * `onInstanced` has finished.
         */
        INSTANCED,

        /**
         * Starting state.
         *
         * `start` has been called, but `onStarted` no.
         */
        STARTING,

        /**
         * STARTED state.
         *
         * `onStarted` has finished.
         */
        STARTED,

        /**
         * Stopping state.
         *
         * `stop` has been called, but `onStop` no.
         */
        STOPPING,

        /**
         * Stopped state.
         *
         * `onStop` has finished.
         */
        STOPPED
    }

    /**
     * Called when the constructor has finished.
     *
     * It should be used to initialize the module's
     * specific properties.
     */
    default void onInstanced() {
    }

    /**
     * Called when the module is started.
     *
     * It contains the module logic.
     */
    default void onStarted() {
    }

    /**
     * Called when the module is going to be stopped.
     *
     * It should be used to clean up properties.
     */
    default void onStopped() {
    }

    /**
     * Called when an exception is thrown.
     *
     * It should be handled here.
     *
     * @param e Exception thrown.
     */
    default void onException(Exception e) {
    }

    /**
     * Returns the current lifecycle state.
     *
     * @return Current lifecycle state.
     */
    default Status getStatus() {
        return Status.NULL;
    }
}
