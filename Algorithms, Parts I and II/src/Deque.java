import java.util.Iterator;

/**
 * Created by pengl on 2015-11-15.
 */
public class Deque<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    static class Node<E> {

        private E item;
        private Node prev;
        private Node next;

        public Node(E item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public E getItem() {
            return item;
        }

    }

    public Deque() {
        head = null;
        tail = null;
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

    static class DequeIterator<E> implements Iterator<E> {

        private Node<E> current;

        public DequeIterator(Node<E> tail) {
            current = tail;
        }

        public E next() {
            E ret = current.getItem();
            current = current.getNext();
            return ret;
        }

        public boolean hasNext() {
            if (current != null) {
                return true;
            }
            return false;
        }

    }

    public void addFirst(T item) {
        Node<T> front_node = new Node<>(item);
        front_node.setPrev(head);
        if (head != null) {
            head.setNext(front_node);
        }
        else {
            tail = front_node;
        }
        head = front_node;
        size ++;
    }

    public void addLast(T item) {
        Node<T> rear_node = new Node<>(item);
        rear_node.setNext(tail);
        if (tail != null) {
            tail.setPrev(rear_node);
        }
        else {
            head = rear_node;
        }
        tail = rear_node;
        size ++;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T ret = head.getItem();
        head = head.getPrev();
        if (head != null) {
            head.setNext(null);
        }
        else {
            tail = null;
        }
        size --;
        return ret;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T ret = tail.getItem();
        tail = tail.getNext();
        if (tail != null) {
            tail.setPrev(null);
        }
        else {
            head = null;
        }
        size --;
        return ret;
    }

    public T peekFirst() {
        if (size == 0) {
            return null;
        }
        return head.getItem();
    }

    public T peekLast() {
        if (size == 0) {
            return null;
        }
        return tail.getItem();
    }

    public Iterator<T> iterator() {
        return new DequeIterator<>(tail);
    }

    public static void main(String[] args) {
        Deque<Integer> test = new Deque<>();
        test.addFirst(1);
        test.addFirst(2);
        System.out.println(test.removeLast());
        test.addLast(3);
        System.out.println(test.removeFirst());
        System.out.println(test.removeFirst());
        test.addLast(1);
        test.addFirst(2);
        System.out.println(test.removeFirst());
        System.out.println(test.removeFirst());
    }

}
