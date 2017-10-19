package com.sergeyvolkodav.concurrency.synchronizers.countDownLatch;


import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class Race {

    //create countDownLatch for 8 clauses
    private static final CountDownLatch START = new CountDownLatch(8);

    // track distance
    private static final int trackLength = 500000;


    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            new Thread(new Car(i, (int) (Math.random() * 100 + 50))).start();
            Thread.sleep(1000);
        }

        // wait until all car will be ready
        while (START.getCount() > 3) {
            Thread.sleep(100);
        }

        Thread.sleep(1000);
        System.out.println("Prepare!");
        START.countDown();
        Thread.sleep(1000);
        System.out.println("Ready!");
        START.countDown();
        Thread.sleep(1000);
        System.out.println("Go!");
        //All conditions are satisfied, Let's go!
        START.countDown();
    }

    public static class Car implements Runnable {
        private int carNumber;
        // car speed is constant for all ride ;-)
        private int carSpeed;

        public Car(int carNumber, int carSpeed) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Car #%d ready.\n", carNumber);

                START.countDown();
                //wait until all conditions
                START.await();

                Thread.sleep(trackLength / carSpeed);
                System.out.printf("Car #%d finished!\n", carNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
