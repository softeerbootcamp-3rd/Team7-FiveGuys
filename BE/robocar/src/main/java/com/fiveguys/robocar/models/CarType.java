package com.fiveguys.robocar.models;

public enum CarType {

    SMALL(5),
    MEIDUM(8);

    int capacity;

    CarType(int capacity){
        this.capacity=capacity;
    }
    public int getCapacity() {
        return capacity;
    }
}
