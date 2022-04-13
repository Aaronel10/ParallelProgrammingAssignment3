import javax.swing.*;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class Sensor implements Runnable {

    // set numHours to the number of hour reports you want
    static final int numHours = 12;
    static int[][][] results = new int[8][60][numHours];
    static ReentrantLock lock = new ReentrantLock();
    static TreeSet<Integer> ts[] = new TreeSet[numHours];

    @Override
    public void run() {
        int id = Integer.parseInt(Thread.currentThread().getName().split("-")[1]);
        Random rand = new Random();

        for (int hour = 0; hour < numHours; hour++) {
            // let every loop iteration be 1 minute for our report
            ts[hour] = new TreeSet<>();
            for (int j = 0; j < 60; j++) {
                AtomicInteger temp = new AtomicInteger(ThreadLocalRandom.current().nextInt(-100, 70 + 1));
                results[id][j][hour] = temp.get();
                lock.lock();
                ts[hour].add(temp.get());
                lock.unlock();
            }

        }

    } // end run

}



public class SpaceSensors {

        public static void main(String[] args) throws InterruptedException {


                Thread one = new Thread(new Sensor());
                Thread two = new Thread(new Sensor());
                Thread third = new Thread(new Sensor());
                Thread fourth = new Thread(new Sensor());
                Thread fifth = new Thread(new Sensor());
                Thread six = new Thread(new Sensor());
                Thread seven = new Thread(new Sensor());
                Thread eight = new Thread(new Sensor());


                one.start();
                two.start();
                third.start();
                fourth.start();
                fifth.start();
                six.start();
                seven.start();
                eight.start();

                one.join();
                two.join();
                third.join();
                fourth.join();
                fifth.join();
                six.join();
                seven.join();
                eight.join();


                // this is just to present the results from the work
                for (int hour = 0; hour < Sensor.numHours; hour++) {
                    int maxDiff = 0;
                    int interval = 0;

                    for (int j = 0; j < 60; j++) {
                        int min = Integer.MAX_VALUE;
                        int max = Integer.MIN_VALUE;
                        for (int k = 0; k < 8; k++) {
                            int cur = Sensor.results[k][j][hour];
                            min = Math.min(cur, min);
                            max = Math.max(max, cur);
                        }
                        int diff = (int)Math.abs(max - min);
                        if (diff > maxDiff) {
                            interval = j;
                            maxDiff = diff;
                        }
                    }

                    System.out.println("The largest difference in reading among all 8 sensors is found at minute " + interval);
                    System.out.printf("Top five: ");
                    for (int j = 0; j < 5; j++) {
                        System.out.printf("%d ", Sensor.ts[hour].pollLast());
                    }
                    System.out.println();


                    System.out.printf("Bottom five: ");
                    for (int j = 0; j < 5; j++) {
                        System.out.printf("%d ", Sensor.ts[hour].pollFirst());
                    }
                    System.out.println();
                }


            } // end of main

    }

