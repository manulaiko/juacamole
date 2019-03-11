package com.manulaiko.juacamole;

import org.greenrobot.eventbus.Subscribe;

/**
 * Event listener class.
 * =====================
 *
 * Listens for the designated events and handles them.
 *
 * Each listener listens to a type of event and when fired
 * the `onEvent` method is called with the fired method.
 *
 * The `onEvent` method should handle the event and call
 * the event callback if necessary:
 *
 * ```java
 * public class GetUserByIdEventListener extends EventListener<GetUserByIdEvent> {
 *     public void onEvent(GetUserByIdEvent event) {
 *         var user = Database.findUserById(event.getId());
 *
 *         event.callback(user);
 *     }
 * }
 * ```
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class EventListener<T extends Event> {
    /**
     * Handles the incoming events.
     *
     * @param event Received event.
     */
    @Subscribe
    public final void handle(T event) {
        this.onEvent(event);
    }

    /**
     * Executed when an event is fired.
     *
     * This method should handle the event and,
     * if necessary, call the event's callback.
     *
     * @param event Fired event.
     */
    public abstract void onEvent(T event);
}
