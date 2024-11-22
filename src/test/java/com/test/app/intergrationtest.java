package com.test.app;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class intergrationtest {
    private App app;

    @BeforeAll
    void setup() {
        app = new App();
        app.connect("localhost:33060", 10000);
    }

    @AfterAll
    void teardown() {
        app.disconnect();
    }

    @Test
    void testGetCountriesByContinent() {
        ArrayList<String> countries = app.getCountriesByContinent("Asia");
        assertNotNull(countries);
        assertTrue(countries.contains("China"));
        assertTrue(countries.contains("India"));
    }
}