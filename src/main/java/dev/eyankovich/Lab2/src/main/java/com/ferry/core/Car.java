package com.ferry.core;

import com.ferry.manager.FerryManager;

public class Car extends Vehicle {
    public Car(String id, int weight, int area) {
        super(id, weight, area);
    }

    @Override
    public String getType() {
        return "Car";
    }

    @Override
    public Void call() throws Exception {
        FerryManager.getInstance().tryBoard(this);
        return null;
    }
}
