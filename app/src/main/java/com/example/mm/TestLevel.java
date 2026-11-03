package com.example.mm;

public class TestLevel {
    public final Platform[] platforms;
    public TestLevel() {

        platforms = new Platform[]{
                new Platform(0, 700, 1000, 500),
                new Platform(1000, 900, 700, 1000),
                new Platform(1700, 800, 1000, 500),
                new Platform(0, 300, 200, 200)
        };
    }
}
