package com.ferry.state;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface FerryState {
    void handle();
    static final Logger logger = LogManager.getLogger(FerryState.class);
}
