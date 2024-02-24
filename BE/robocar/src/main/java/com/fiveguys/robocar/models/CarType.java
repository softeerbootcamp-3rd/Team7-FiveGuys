package com.fiveguys.robocar.models;

public enum CarType {

    SMALL(4),
    MEDIUM(6);

    int capacity;

    CarType(int capacity){
        this.capacity=capacity;
    }
    public int getCapacity() {
        return capacity;
    }
}
