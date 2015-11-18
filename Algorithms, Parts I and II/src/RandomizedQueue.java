import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by pengl on 2015-11-15.
 */
public class RandomizedQueue<E> implements Iterable<E> {

    private E[] items;
    private int capacity = 10;
    private int size;

    static class RandomizedQueueIterator<T> implements Iterator<T> {

        private T[] items;
        private int size;

        public RandomizedQueueIterator(T[] items, int size) {
            this.items = (T[]) new Object[size];
            System.arraycopy(items, 0, this.items, 0, size);
            this.size = size;
        }

        public T next() {
            int index = StdRandom.uniform(size);
            T ret = items[index];
            items[index] = items[size - 1];
            items[size - 1] = null;
            size --;
            return ret;
        }

        public boolean hasNext() {
            if (size != 0) {
                return true;
            }
            return false;
        }

    }

    public RandomizedQueue() {
        items = (E[]) new Object[capacity];
        size = 0;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void enqueue(E item) {
        if (size == capacity) {
            capacity *= 2;
            E[] new_items = (E[]) new Object[capacity];
            System.arraycopy(items, 0, new_items, 0, size);
            items = new_items;
        }
        items[size] = item;
        size ++;
    }

    public E dequeue() {
        int index = StdRandom.uniform(size);
        E ret = items[index];
        items[index] = items[size - 1];
        items[size - 1] = null;
        size --;
        return ret;
    }

    public E sample() {
        int index = StdRandom.uniform(size);
        return items[index];
    }

    public Iterator<E> iterator() {
        return new RandomizedQueueIterator<>(items, size);
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        for(int i = 0; i < 10; i ++) {
            System.out.print(test.sample() + " ");
        }
        System.out.println();
        System.out.println(test.dequeue());
        System.out.println(test.dequeue());
        for(int i = 0; i < 10; i ++) {
            System.out.print(test.sample() + " ");
        }
        System.out.println();
        System.out.println(test.size());
    }

}
