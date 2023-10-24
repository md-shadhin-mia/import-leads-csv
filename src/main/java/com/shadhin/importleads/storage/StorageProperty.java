package com.shadhin.importleads.storage;

public class StorageProperty {
    public StorageProperty(String location) {
        this.location = location;
    }

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
