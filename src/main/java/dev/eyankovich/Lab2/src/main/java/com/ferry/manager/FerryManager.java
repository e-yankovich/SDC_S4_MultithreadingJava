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
                    // если очередь пуста — ждём сигнал
                    if (waitingQueue.isEmpty()) {
                        ferryNotFull.await(1, TimeUnit.SECONDS); // ждём не вечно
                    }

                    // снова проверяем очередь
                    while (!waitingQueue.isEmpty()) {
                        Vehicle v = waitingQueue.peek();
                        if (ferry.tryAddVehicle(v)) {
                            waitingQueue.poll();
                            logger.info("{} {} boarded the ferry.", v.getType(), v.getId());
                        } else {
                            break;
                        }
                    }

                    // если есть кто-то на борту — плывём
                    if (!ferry.getOnboardSnapshot().isEmpty()) {
                        ferry.sailAndReset();
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } finally {
                    lock.unlock();
                }

                // Мини-пауза перед новой итерацией
                try {
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




//    public void signalDispatcher() {
//        lock.lock();
//        try {
//            ferryNotFull.signalAll();
//            //tryStartFerryCycle();
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    public void tryStartFerryCycle() {
//        lock.lock();
//        try {
//            while (!waitingQueue.isEmpty()) {
//                Vehicle v = waitingQueue.peek();
//                if (ferry.tryAddVehicle(v)) {
//                    waitingQueue.poll();
//                    logger.info("{} {} boarded the ferry.", v.getType(), v.getId());
//                } else {
//                    break;
//                }
//            }
//
//            if (!ferry.getOnboardSnapshot().isEmpty()) {
//                ferry.sailAndReset();
//            }
//
//        } finally {
//            lock.unlock();
//        }
//    }

}
