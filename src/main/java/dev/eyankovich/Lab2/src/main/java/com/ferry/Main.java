package com.ferry;

import com.ferry.core.Vehicle;
import com.ferry.manager.FerryManager;
import com.ferry.util.Initializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        List<Vehicle> vehicles = Initializer.loadVehicles("vehicles.txt");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        FerryManager ferryManager = FerryManager.getInstance();

        for (Vehicle v : vehicles) {
            executor.submit(v);
        }

        executor.shutdown();
        
        // Wait for all vehicles to be processed
        try {
            // Wait for all vehicle threads to complete
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                logger.warn("Not all vehicles were processed within the timeout period");
            }
            
            // Wait for the queue to be empty and ferry to be ready
            while (true) {
                if (ferryManager.isQueueEmpty() && ferryManager.isFerryReady()) {
                    logger.info("All vehicles have been processed and ferry is ready");
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Main thread was interrupted while waiting for vehicles to be processed");
        }
    }
}
