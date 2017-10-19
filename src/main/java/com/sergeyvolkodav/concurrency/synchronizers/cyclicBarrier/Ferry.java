package com.sergeyvolkodav.concurrency.synchronizers.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

public class Ferry {


    private static final CyclicBarrier BARRIER = new CyclicBarrier(3, new FerryBoat());


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 9; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
        }
    }

    public static class FerryBoat implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(500);
                System.out.printf("Ferry transfer cars! \n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static class Car implements Runnable {

        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Car #%d drive to ferry.\n", carNumber);
                //Для указания потоку о том что он достиг барьера, нужно вызвать метод await()
                //После этого данный поток блокируется, и ждет пока остальные стороны достигнут барьера
                BARRIER.await();
                System.out.printf("Car #%d continue drive.\n", carNumber);
            } catch (Exception e) {
            }
        }
    }
}
