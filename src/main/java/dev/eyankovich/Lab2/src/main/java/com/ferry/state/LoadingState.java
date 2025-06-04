package com.ferry.state;

import com.ferry.core.Ferry;

public class LoadingState implements FerryState {
    private final Ferry ferry;

    public LoadingState(Ferry ferry) {
        this.ferry = ferry;
    }

    @Override
    public void handle() {
        // Переход к плаванию после загрузки
        ferry.setState(new SailingState(ferry));
        ferry.sailAndReset();
    }
}
