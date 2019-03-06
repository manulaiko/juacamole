package com.manulaiko.juacamole;

import org.junit.Test;

import static org.junit.Assert.*;

public class JuacamoleTest {

    @Test
    public void main() {
        var juacamole = new Juacamole(
                new ModuleTest()
        );

        juacamole.start();

        assertEquals(juacamole.get(ModuleTest.class).getStatus(), ModuleLifeCycle.Status.STARTING);
    }
}