package com.manulaiko.juacamole;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * Event class.
 * ============
 *
 * Represents the event that can be fired.
 *
 * Each event should define its properties and
 * the callback that should be called once it's handled.
 *
 * Example:
 *
 * ```java
 * public class GetUserByIdEvent extends Event<User> {
 *     private int id;
 *
 *     public GetUserByIdEvent(int id, Consumer<User> callback) {
 *         super(callback);
 *
 *         this.id = id;
 *     }
 *
 *     public int getId() {
 *         return this.id;
 *     }
 * }
 *
 * var event = new GetUserByIdEvent(1, this::parseUser);
 * ```
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@AllArgsConstructor
@NoArgsConstructor
public class Event<T> {
    /**
     * The callback consumer.
     */
    private Consumer<T> callback;

    /**
     * Calls the callback with the given argument.
     *
     * @param t Callback argument.
     */
    public void callback(T t) {
        if (this.callback == null) {
            return;
        }

        this.callback.accept(t);
    }
}
