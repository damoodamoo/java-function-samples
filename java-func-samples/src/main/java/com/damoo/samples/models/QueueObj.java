package com.damoo.samples.models;

import java.util.UUID;

public class QueueObj {
    public String id;
    public String name;
    public String favouriteFruit;

    public QueueObj(String name, String favouriteFruit) {
        this.name = name;
        this.favouriteFruit = favouriteFruit;
    }
}