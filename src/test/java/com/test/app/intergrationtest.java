package com.test.app;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class intergrationtest {
    private intergrationtest app;

    @BeforeAll
    void setup() {
        app = new intergrationtest();
        app.connect("localhost:33060", 10000);
    }

    private void connect(String s, int i) {
    }

    @AfterAll
    void teardown() {

    }

    @Test
    void testGetCountriesByContinent() {
        ArrayList<String> countries = app.getCountriesByContinent("Asia");
        assertNotNull(countries);
        assertTrue(countries.contains("China"));
        assertTrue(countries.contains("India"));
    }
}