package com.manulaiko.juacamole;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class JuacamoleTest {

    @Test
    public void main() {
        var juacamole = new Juacamole(
                new ModuleTest()
        );

        juacamole.start();

        assertEquals(juacamole.get(ModuleTest.class).getStatus(), ModuleLifeCycle.Status.STARTING);

        while (juacamole.get(ModuleTest.class).getStatus() != ModuleLifeCycle.Status.STOPPED) {
            System.out.println("Waiting...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                // Ignore
            }
        }

        assertEquals(juacamole.get(ModuleTest.class).getStatus(), ModuleLifeCycle.Status.STOPPED);

        juacamole.start(ModuleTest.class);

        assertEquals(juacamole.get(ModuleTest.class).getStatus(), ModuleLifeCycle.Status.STARTING);

        while (juacamole.get(ModuleTest.class).getStatus() != ModuleLifeCycle.Status.STOPPED) {
            System.out.println("Waiting...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                // Ignore
            }
        }

        assertEquals(juacamole.get(ModuleTest.class).getStatus(), ModuleLifeCycle.Status.STOPPED);
    }
}