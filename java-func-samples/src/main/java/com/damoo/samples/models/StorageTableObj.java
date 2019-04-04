package com.damoo.samples.models;

import java.util.UUID;

public class StorageTableObj {
    public String rowKey;
    public String partitionKey;
    public String id;
    public String name;
    public String favouriteFruit;

    public StorageTableObj(String rowKey, String partitionKey, String name, String favouriteFruit) {
        this.name = name;
        this.favouriteFruit = favouriteFruit;
        this.rowKey = rowKey;
        this.partitionKey = partitionKey;
    }
}