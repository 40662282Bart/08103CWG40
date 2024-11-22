package com.test.app;

import com.mycompany.app.Main;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testConnectToDatabaseDebug() {
        Main app = new Main();
        Connection con = app.connectToDatabase();

        assertNotNull(con, "Connection should not be null in DEBUG mode");

        try {
            con.close();
        } catch (Exception e) {
            fail("Connection should close without errors");
        }
    }

    @Test
    public void testConnectToDatabaseNormal() {
        Main app = new Main();
        Connection con = app.connectToDatabase();

        assertNotNull(con, "Connection should not be null in normal mode");

        try {
            con.close();
        } catch (Exception e) {
            fail("Connection should close without errors");
        }
    }
}