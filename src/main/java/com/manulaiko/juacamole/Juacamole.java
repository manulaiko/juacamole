package com.manulaiko.juacamole;

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
