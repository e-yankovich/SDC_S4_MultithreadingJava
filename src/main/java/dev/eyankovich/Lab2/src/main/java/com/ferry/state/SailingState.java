package com.ferry.state;

import com.ferry.core.Ferry;

import java.util.concurrent.TimeUnit;

public class SailingState implements FerryState {
    private final Ferry ferry;

    public SailingState(Ferry ferry) {
        this.ferry = ferry;
    }

    @Override
    public void handle() {
        try {
            logger.info("Ferry is sailing...");
            TimeUnit.SECONDS.sleep(2); // вместо Thread.sleep
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        ferry.setState(new UnloadingState(ferry));
        ferry.sailAndReset();
    }
}

