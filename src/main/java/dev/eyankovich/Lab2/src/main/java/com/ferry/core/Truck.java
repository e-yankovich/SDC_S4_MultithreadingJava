package com.ferry.core;

import com.ferry.manager.FerryManager;

public class Truck extends Vehicle {
    public Truck(String id, int weight, int area) {
        super(id, weight, area);
    }

    @Override
    public String getType() {
        return "Truck";
    }

    @Override
    public Void call() throws Exception {
        FerryManager.getInstance().tryBoard(this);
        return null;
    }
}