package com.sergeyvolkodav.concurrency.synchronizers.phaser;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

/**
 *
 */
public class Bus {

    private static final Phaser PHASER = new Phaser(1);


    public static void main(String[] args) throws InterruptedException {
        ArrayList<Passenger> passengers = new ArrayList<>();

        //Generate passengers on bus stop
        for (int i = 1; i < 5; i++) {
            if ((int) (Math.random() * 2) > 0)
                //This passenger leave the bus on next bus station
                passengers.add(new Passenger(i, i + 1));

            if ((int) (Math.random() * 2) > 0)
                //This passenger leave the bus on the last bus station
                passengers.add(new Passenger(i, 5));
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

                    //Check for a passengers on the bus station
                    for (Passenger p : passengers)
                        if (p.departure == currentBusStop) {
                            //Register threads that will participates in phases
                            PHASER.register();
                            // starts
                            p.start();
                        }
                    //Inform that we are ready
                    PHASER.arriveAndAwaitAdvance();
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

                //Wait until bus will not arrive on the bus station
                while (PHASER.getPhase() < destination) {
                    //Inform that we are ready and wait
                    PHASER.arriveAndAwaitAdvance();
                }
                Thread.sleep(1);
                System.out.println(this + " left the bus.");
                //Cancel registry
                PHASER.arriveAndDeregister();
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
