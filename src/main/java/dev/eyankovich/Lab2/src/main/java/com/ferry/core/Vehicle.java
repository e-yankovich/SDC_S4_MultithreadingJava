package com.ferry.core;

import java.util.concurrent.Callable;

public abstract class Vehicle implements Callable<Void> {
    protected int weight;
    protected int area;
    protected String id;

    public Vehicle(String id, int weight, int area) {
        this.id = id;
        this.weight = weight;
        this.area = area;
    }

    public int getWeight() {
        return weight;
    }

    public int getArea() {
        return area;
    }

    public String getId() {
        return id;
    }

    public abstract String getType();
}
