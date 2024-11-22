package com.napier.sem.com.napier;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static com.mycompany.app.Main.main;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static new  = new main();

    @BeforeAll
    static void init()
    {
    }

    @Test
    void printSalariesTestNull()
    {
        try main(null);
    }
}