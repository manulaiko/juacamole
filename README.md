Juacamole
=========

Opinionated framework for building modularized java applications. (J(*ava*)uacamo(*du*)le).

Getting started
---------------

The main concept is to separate the application into independent modules.

Each module should not depend on any other module and instead provide an event-based service API.

In order to start Juacamole, you need to instantiate a `Juacamole` object and add the application modules:

```java
import com.manulaiko.juacamole.Juacamole;

class Launcher {
    public static void main(String[] args) {
        var juacamole = new Juacamole();
        
        juacamole.add(new APIServer());
        juacamole.add(new Frontend());
        
        juacamole.start();
    }
}
```

Each module is started in a new thread and can be easily stopped by calling the `Juacamole.stop` method:

```java
juacamole.stop(APIServer.class);
```

Modules
-------

A module is a part of your application that is independent of the rest of modules. For example,
a REST api server, an HTTP server, a connection to a database...

Like a service in a microservice architecture, just, that in the same jvm, because none actually needs
a different server for each service ¯\\\_(:D(*insert katakana Tsu*))_/¯

The `Module` class provides the life-cycle methods:

```java
import com.manulaiko.juacamole.Module;

class Database extends Module {
    public void onInstance() {
        //this.connection = new JDBCConenction(...);
    }
    
    public void onStart() {
        //this.connection.connect();
    }
    
    public void onStop() {
        //this.connection.close();
    }
}
```

Event API
---------

In order to make a module usable, an event-bus API is provided where each module registers
the events that it can handle, and the modules that need to consume them, calls them:

```java
super.addEventListener(new GetUserByIdEventListener());
super.addEventListener(new DeleteUserByIdEventListener());

// ...

super.post(new GetUserByIdEvent(1, this::parseUser));
super.post(new DeleteUserByIdEvent(1));
```

The `Event` class provides the base for each event that can be fired and the `EventListener` class
provides the base for each event handler:

```java
class GetUserByIdEvent extends Event {
    private int id;
    
    public GetUserByIdEvent(int id, Consumer<User> callback) {
        super(callback);
        
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}

class GetUserByIdEventListener extends EventListener<GetUserByIdEvent> {
    public void handle(GetUserByIdEvent event) {
        // var user = getUserById(event.getId());
        //
        // event.finish(user);
    }
}
```

And that's pretty much it.