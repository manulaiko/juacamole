package com.manulaiko.juacamole;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Juacamole class.
 * ================
 *
 * This is the entry point of the framework.
 *
 * It takes care of handling the module life cycle and publishing
 * the events to their corresponding listeners.
 *
 * To start the modules you need to add them to the instance:
 *
 * ```java
 * var juacamole = new Juacamole();
 *
 * juacamole.add(new APIServer());
 * juacamole.add(new Frontend());
 * ```
 *
 * You can also pass the modules to the constructor:
 *
 * ```java
 * var juacamole = new Juacamole(new APIServer(), new Frontend());
 * ```
 *
 * Then call the method `start` to start all or any of the modules:
 *
 * ```java
 * juacamole.start(APIServer.class);
 * juacamole.start();
 * ```
 *
 * To stop, call the method `stop` the same way as `start` (obvs.):
 *
 * ```java
 * juacamole.stop(APIServer.class);
 * juacamole.stop();
 * ```
 *
 * You can also retrieve and delete modules with the `get` and `remove` methods:
 *
 * ```java
 * var apiServer = juacamole.get(APIServer.class);
 * var modules = juacamole.get();
 *
 * juacamole.remove(APIServer.class);
 * juacamole.remove(modules);
 * ```
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Slf4j
@NoArgsConstructor
public class Juacamole {
    /**
     * Registered modules.
     */
    private Map<Class<? extends Module>, Module> modules = new HashMap<>();

    /**
     * Constructor.
     *
     * @param modules Modules to register.
     */
    public Juacamole(Module... modules) {
        this.add(Arrays.asList(modules));
    }

    /**
     * Starts the registered modules.
     */
    public void start() {
        this.modules.values().forEach(this::start);
    }

    /**
     * Starts a module.
     *
     * @param type Module to start.
     */
    public void start(Class<? extends Module> type) {
        this.modules.computeIfPresent(type, (k, m) -> {
            this.start(m);

            return m;
        });
    }

    /**
     * Starts a module.
     *
     * It will wait for the module to be ready to be started,
     * this means that either, the module hasn't been instantiated yet
     * (its current status is NULL) or the module is stopping
     * (its current status is STOPPING).
     *
     * If the module isn't instantiated (INSTANCED) or stopped (STOPPED)
     * it will log an error and do nothing.
     *
     * @param module Module to start.
     */
    private void start(Module module) {
        log.debug("Starting " + module.getClass().getName() + "...");

        while (
                // Wait if the module is being instantiated or stopped.
                module.getStatus().ordinal() < ModuleLifeCycle.Status.INSTANCED.ordinal() &&
                module.getStatus() != ModuleLifeCycle.Status.STOPPING
        ) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                log.warn("Error while waiting for module to ready before starting!");
            }
        }

        if (
                // If the module hasn't been instantiated or stopped
                module.getStatus() != ModuleLifeCycle.Status.INSTANCED ||
                module.getStatus() != ModuleLifeCycle.Status.STOPPED
        ) {
            log.warn("Couldn't start module, illegal state! " + module.getStatus());

            return;
        }

        module.start();
    }

    /**
     * Adds multiple modules.
     *
     * @param modules Modules to add.
     */
    public void add(Iterable<Module> modules) {
        modules.forEach(this::add);
    }

    /**
     * Adds a module.
     *
     * @param module Module to add.
     */
    public void add(Module module) {
        this.modules.put(module.getClass(), module);
    }

    /**
     * Removes multiple modules.
     *
     * @param modules Modules to remove.
     */
    public void remove(Iterable<Module> modules) {
        modules.forEach(this::remove);
    }

    /**
     * Removes a module.
     *
     * @param module Module to remove.
     */
    public void remove(Module module) {
        this.remove(module.getClass());
    }

    /**
     * Removes a module.
     *
     * @param type Module type to remove.
     */
    public void remove(Class<? extends Module> type) {
        this.modules.remove(type);
    }

    /**
     * Returns a module.
     *
     * @param type Module type to return.
     *
     * @return Module instance of `type`.
     */
    public Module get(Class<? extends Module> type) {
        return this.get().get(type);
    }

    /**
     * Returns all the registered modules.
     *
     * @return Registered modules.
     */
    public Map<Class<? extends Module>, Module> get() {
        return this.modules;
    }
}
