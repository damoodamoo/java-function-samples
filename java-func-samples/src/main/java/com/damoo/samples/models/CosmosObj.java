package com.damoo.samples.models;

import java.util.UUID;

public class CosmosObj {
    public String id;
    public String name;
    public String favouriteFruit;

    public CosmosObj(String name, String favouriteFruit) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.favouriteFruit = favouriteFruit;
    }
}