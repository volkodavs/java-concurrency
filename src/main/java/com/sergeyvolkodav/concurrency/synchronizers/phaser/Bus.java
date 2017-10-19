package com.sergeyvolkodav.concurrency.synchronizers.phaser;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

public class Bus {

    private static final Phaser PHASER = new Phaser(1);


    public static void main(String[] args) throws InterruptedException {
        ArrayList<Passenger> passengers = new ArrayList<>();

        for (int i = 1; i < 5; i++) {           //Generate passengers on bus stop
            if ((int) (Math.random() * 2) > 0)
                passengers.add(new Passenger(i, i + 1));//This passenger leave the bus on next bus station

            if ((int) (Math.random() * 2) > 0)
                passengers.add(new Passenger(i, 5));    //This passenger leave the bus on the last bus station
        }

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    System.out.println("The bus starts");
                    PHASER.arrive();//In phase only 1 participant - bus
                    break;
                case 6:
                    System.out.println("The bus arrived at the last station");
                    PHASER.arriveAndDeregister();//Break the barrier
                    break;
                default:
                    int currentBusStop = PHASER.getPhase();
                    System.out.println("Bus station # " + currentBusStop);

                    for (Passenger p : passengers)          //Check for a passengers on the bus station
                        if (p.departure == currentBusStop) {
                            PHASER.register();//Register threads that will participates in phases
                            p.start();        // starts
                        }

                    PHASER.arriveAndAwaitAdvance();//Inform that we are ready
            }
        }
    }


    public static class Passenger extends Thread {
        private int departure;
        private int destination;

        public Passenger(int departure, int destination) {
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + " wait on the bus # " + this.departure);
        }

        @Override
        public void run() {
            try {
                System.out.println(this + " got on the bus.");

                while (PHASER.getPhase() < destination) //Wait until bus will not arrive on the bus station
                    PHASER.arriveAndAwaitAdvance();     //Inform that we are ready and wait

                Thread.sleep(1);
                System.out.println(this + " left the bus.");
                PHASER.arriveAndDeregister();   //Cancel registry
            } catch (InterruptedException e) {
            }
        }

        @Override
        public String toString() {
            return "Passenger{" +
                    "departure=" + departure +
                    ", destination=" + destination +
                    '}';
        }
    }


}
