package com.ferry;

import com.ferry.core.Vehicle;
import com.ferry.util.Initializer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        List<Vehicle> vehicles = Initializer.loadVehicles("vehicles.txt");
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (Vehicle v : vehicles) {
            executor.submit(v);
        }

        executor.shutdown();
    }
}
