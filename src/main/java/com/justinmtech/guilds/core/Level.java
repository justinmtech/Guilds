package com.justinmtech.guilds.core;

public class Level {
    private int value;

    public Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMaxWarps() {
        return value;
    }

    public int getMaxPlayers() {
        return value * 3;
    }
}
