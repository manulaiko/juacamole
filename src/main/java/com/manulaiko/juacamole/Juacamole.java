package com.manulaiko.juacamole;

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
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Juacamole {

}
