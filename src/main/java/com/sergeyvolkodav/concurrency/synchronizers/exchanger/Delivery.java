package com.sergeyvolkodav.concurrency.synchronizers.exchanger;

import java.util.concurrent.Exchanger;

public class Delivery {

    private static final Exchanger<String> EXCHANGER = new Exchanger<>();


    public static void main(String[] args) throws InterruptedException {
        String[] p1 = new String[]{"{envelope A->D}", "{envelope A->C}"};//Prepare envelope for a 1st track
        String[] p2 = new String[]{"{envelope B->C}", "{envelope B->D}"};//Prepare enveloper for a 2nd track
        new Thread(new Truck(1, "A", "D", p1)).start();//Dispatch 1st track from А to D
        Thread.sleep(100);
        new Thread(new Truck(2, "B", "C", p2)).start();//Dispatch 2nd track from В to С
    }


    public static class Truck implements Runnable {
        private int number;
        private String dep;
        private String dest;
        private String[] parcels;


        public Truck(int number, String departure, String destination, String[] parcels) {
            this.number = number;
            this.dep = departure;
            this.dest = destination;
            this.parcels = parcels;
        }

        @Override
        public void run() {
            try {
                System.out.printf("В track #%d loaded: %s and %s.\n", number, parcels[0], parcels[1]);
                System.out.printf("Track #%d start driving from %s to %s.\n", number, dep, dest);
                Thread.sleep(1000 + (long) Math.random() * 5000);
                System.out.printf("Track #%d come to Е.\n", number);
                parcels[1] = EXCHANGER.exchange(parcels[1]);//Block and wait until other thread execute exchange()
                System.out.printf("Into track #%d put envelope for destination %s.\n", number, dest);
                Thread.sleep(1000 + (long) Math.random() * 5000);
                System.out.printf("Track #%d arrived to %s and dispatched: %s and %s.\n",
                                  number,
                                  dest,
                                  parcels[0],
                                  parcels[1]);
            } catch (InterruptedException e) {
            }
        }
    }
}
