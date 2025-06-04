package com.ferry.manager;

import com.ferry.core.Ferry;
import com.ferry.core.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FerryManager {

    private static FerryManager instance;
    private static final ReentrantLock instanceLock = new ReentrantLock();

    private final Queue<Vehicle> waitingQueue = new LinkedList<>();
    private final Ferry ferry;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition ferryNotFull = lock.newCondition();
    private static final Logger logger = LogManager.getLogger(FerryManager.class);

    private FerryManager() {
        this.ferry = new Ferry(10000, 100); // параметры могут быть загружены из файла
        startDispatcherThread();
    }

    public static FerryManager getInstance() {
        instanceLock.lock();
        try {
            if (instance == null) {
                instance = new FerryManager();
            }
            return instance;
        } finally {
            instanceLock.unlock();
        }
    }

    public void tryBoard(Vehicle vehicle) throws InterruptedException {
        lock.lock();
        try {
            waitingQueue.offer(vehicle);
            logger.info("{} {} added to queue.", vehicle.getType(), vehicle.getId());
            ferryNotFull.signalAll(); // пробуждаем диспетчер
        } finally {
            lock.unlock();
        }
    }

    private void startDispatcherThread() {
        Thread dispatcher = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    // Wait until the ferry is ready for loading and there are vehicles in the queue
                    while (!ferry.isReadyForLoading() || waitingQueue.isEmpty()) {
                        logger.debug("Dispatcher waiting for signal. Ferry ready: {}, Queue size: {}", 
                            ferry.isReadyForLoading(), waitingQueue.size());
                        ferryNotFull.await();
                        logger.debug("Dispatcher received signal. Ferry ready: {}, Queue size: {}", 
                            ferry.isReadyForLoading(), waitingQueue.size());
                    }

                    boolean loadedAny = false;
                    // Load vehicles onto the ferry until it's full or queue is empty
                    while (!waitingQueue.isEmpty() && ferry.isReadyForLoading()) {
                        Vehicle v = waitingQueue.peek();
                        logger.debug("Attempting to load {} {}. Ferry onboard count: {}", 
                            v.getType(), v.getId(), ferry.getOnboardSnapshot().size());
                        if (ferry.tryAddVehicle(v)) {
                            waitingQueue.poll();
                            loadedAny = true;
                            logger.info("{} {} boarded the ferry.", v.getType(), v.getId());
                        } else {
                            if (ferry.getOnboardSnapshot().isEmpty()) {
                                waitingQueue.poll();
                                logger.warn("{} {} cannot fit on the empty ferry and will be skipped.", 
                                    v.getType(), v.getId());
                                continue;
                            }
                            break;
                        }
                    }

                    logger.debug("After loading: Ferry onboard count: {}. Queue size: {}", 
                        ferry.getOnboardSnapshot().size(), waitingQueue.size());

                    if (loadedAny) {
                        logger.debug("Starting ferry trip with {} vehicles onboard.", 
                            ferry.getOnboardSnapshot().size());
                        new Thread(ferry::sailAndReset).start();
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } finally {
                    lock.unlock();
                }

                // Wait for the next signal before checking conditions again
                try {
                    logger.debug("Dispatcher waiting for next signal...");
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        dispatcher.setDaemon(true);
        dispatcher.start();
    }

    public void signalDispatcher() {
        lock.lock();
        try {
            logger.debug("Signaling dispatcher to check the queue.");
            ferryNotFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean isQueueEmpty() {
        lock.lock();
        try {
            return waitingQueue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public boolean isFerryReady() {
        lock.lock();
        try {
            return ferry.isReadyForLoading();
        } finally {
            lock.unlock();
        }
    }

}
