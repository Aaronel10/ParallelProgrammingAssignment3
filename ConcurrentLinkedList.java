import java.util.concurrent.locks.ReentrantLock;


// LAZY LIST IMPLEMENTATION FROM Maurice Herlihy and Nir Shavit
// Authors of The art of multiprocessor programming
class Node<T extends Comparable<T>> {

    T item;
    int key;
    Node<T> next;
    boolean marked;
    ReentrantLock lock;

    Node(T item) {
        this.item = item;
        this.key = item.hashCode();
        this.next = null;
        this.marked = false;
        this.lock = new ReentrantLock();
    }

    Node(int key) {
        this.item = null;
        this.key = key;
        this.next = null;
        this.marked = false;
        this.lock = new ReentrantLock();
    }

    void lock() {
        lock.lock();
    }
    void unlock() {
        lock.unlock();
    }
}

public class ConcurrentLinkedList<T extends Comparable<T>> {

    public Node<T> head;

    public ConcurrentLinkedList() {
        this.head = new Node<>(Integer.MIN_VALUE);
        this.head.next = new Node<>(Integer.MAX_VALUE);
    }

    public boolean isEmpty() {
        return this.head.next.key == Integer.MAX_VALUE;
    }

    private boolean validate(Node<T> pred, Node<T> curr) {
        return !pred.marked && !curr.marked && pred.next == curr;
    }

    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = this.head;
            Node<T> curr = head.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            try {
                curr.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key == key) { // present
                            return false;
                        } else { // not present
                            Node<T> Node = new Node<>(item);
                            Node.next = curr;
                            pred.next = Node;
                            return true;
                        }
                    }
                } finally { // always unlock
                    curr.unlock();
                }
            } finally { // always unlock
                pred.unlock();
            }
        }
    }
    public boolean remove(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = this.head;
            Node<T> curr = head.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            try {
                curr.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key != key) {
                            return false;
                        } else {
                            curr.marked = true;
                            pred.next = curr.next;
                            return true;
                        }
                    }
                } finally {
                    curr.unlock();
                }
            } finally {
                pred.unlock();
            }
        }
    }
    public boolean contains(T item) {
        int key = item.hashCode();
        Node<T> curr = this.head;
        while (curr.key < key)
            curr = curr.next;
        return curr.key == key && !curr.marked ;
    }

}