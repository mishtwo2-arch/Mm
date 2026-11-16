package com.example.mm;

public class OneLevel extends Level {
    public Boolean button = true;
    public OneLevel() {

        platforms = new Platform[]{
                new Platform(0, 700, 1000, 500),
                new Platform(1000, 900, 700, 1000),
                new Platform(1700, 800, 1000, 500),
                new Platform(0, 300, 2000, 200)
        };
    }
}

