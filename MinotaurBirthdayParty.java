// Aaronel Haro COP 4520

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

class Servant extends Thread {

//     static ConcurrentSkipListSet<Integer> list = new ConcurrentSkipListSet<>();

    static ConcurrentLinkedList<Integer> list = new ConcurrentLinkedList<>();

    static RandomSet<Integer> bag;
    static AtomicInteger thankYouCards = new AtomicInteger(0);
    static final int size = 500000;
    static TreeSet<Integer> ts = new TreeSet<>();

    int operation;

    @Override
    public void run() {
        // servant will do a random operation
        while (!bag.isEmpty() || !list.isEmpty()) {

            if (bag.isEmpty()) {
                // we have added everything from bag so only remove or contains
                operation = ThreadLocalRandom.current().nextInt(2,4);
            } else {
                // can be any of the 3
                operation = ThreadLocalRandom.current().nextInt(1,4);
            }

            switch(operation) {
                case 1: {
                    // add item to list from bag
                    Integer item = bag.pollRandom(new Random());
                    list.add(item);
                    // Im using a treeset in order to now the values at the front of the list
                    ts.add(item);
                    break;
                }
                case 2: {
                    // take from list and add to bag
                    if (ts.size() > 0) {
                        int item = ts.pollFirst();
                        list.remove(item);
                        thankYouCards.incrementAndGet();
                    }
                    break;
                }
                case 3: {
                    // arbitrary contains that checks if a random number is in the list
                    int item = ThreadLocalRandom.current().nextInt(1, 500001);
                    boolean x = list.contains(item);
                    break;
                }
            }
        }
    }

    static void setArray() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arr.add((i+1));
        }
        bag = new RandomSet<>(arr);
    }
}

public class MinotaurBirthdayParty {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Servant one = new Servant();
        one.setArray();
        Servant two = new Servant();
        Servant three = new Servant();
        Servant four = new Servant();

        one.run();
        two.run();
        three.run();
        four.run();

        one.join();
        two.join();
        three.join();
        four.join();

        long end = System.currentTimeMillis();
        System.out.println("Number of thank you cards created " + Servant.thankYouCards.get());


        long res = end-start;
        System.out.println("Time:" + res);
    }

}
