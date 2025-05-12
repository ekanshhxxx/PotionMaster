package DSA;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private ListType type;

    public enum ListType {
        SINGLY,
        DOUBLY,
        CIRCULAR,
        DOUBLY_CIRCULAR
    }

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
        }
    }

    public LinkedList(ListType type) {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.type = type;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
            if (type == ListType.CIRCULAR || type == ListType.DOUBLY_CIRCULAR) {
                head.next = head;
                if (type == ListType.DOUBLY_CIRCULAR) {
                    head.prev = head;
                }
            }
        } else {
            if (type == ListType.DOUBLY || type == ListType.DOUBLY_CIRCULAR) {
                newNode.prev = tail;
            }
            tail.next = newNode;
            tail = newNode;
            if (type == ListType.CIRCULAR || type == ListType.DOUBLY_CIRCULAR) {
                tail.next = head;
                if (type == ListType.DOUBLY_CIRCULAR) {
                    head.prev = tail;
                }
            }
        }
        size++;
    }

    public void remove(T data) {
        if (head == null) return;

        Node<T> current = head;
        if (current.data.equals(data)) {
            if (size == 1) {
                head = tail = null;
            } else {
                head = head.next;
                if (type == ListType.DOUBLY || type == ListType.DOUBLY_CIRCULAR)
                    head.prev = tail;
                if (type == ListType.CIRCULAR || type == ListType.DOUBLY_CIRCULAR)
                    tail.next = head;
            }
            size--;
            return;
        }

        do {
            if (current.next != null && current.next.data.equals(data)) {
                Node<T> toRemove = current.next;
                current.next = toRemove.next;
                if (toRemove == tail) {
                    tail = current;
                }
                if ((type == ListType.DOUBLY || type == ListType.DOUBLY_CIRCULAR) && toRemove.next != null) {
                    toRemove.next.prev = current;
                }
                if (type == ListType.CIRCULAR || type == ListType.DOUBLY_CIRCULAR) {
                    tail.next = head;
                }
                size--;
                return;
            }
            current = current.next;
        } while (current != null && current != head);
    }

    public boolean contains(T data) {
        if (head == null) return false;
        Node<T> current = head;
        do {
            if (current.data.equals(data)) return true;
            current = current.next;
        } while (current != null && current != head);
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public void printList() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        Node<T> current = head;
        do {
            System.out.print(current.data + " -> ");
            current = current.next;
        } while (current != null && current != head);
        System.out.println(type == ListType.CIRCULAR || type == ListType.DOUBLY_CIRCULAR ? "(circular)" : "null");
    }

    public void removeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) {
            head = head.next;
            if (type == ListType.CIRCULAR && tail != null) {
                tail.next = head;
            }
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            Node<T> toRemove = current.next;
            current.next = toRemove.next;

            if (type == ListType.DOUBLY && toRemove.next != null) {
                toRemove.next.prev = current;
            }

            if (toRemove == tail) {
                tail = current;
            }

            if (type == ListType.CIRCULAR && tail != null) {
                tail.next = head;
            }
        }
        size--;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.data;
    }

    // ✅ Iterator Implementation
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head;
        private boolean started = false;

        @Override
        public boolean hasNext() {
            return current != null && (!started || current != head || type == ListType.SINGLY || type == ListType.DOUBLY);
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T data = current.data;
            current = current.next;
            started = true;
            return data;
        }
    }

    // -----------------------
    // Inner Pair class
    // -----------------------
    public static class Pair<K, V> {
        private final K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }
        public void setValue(V value) { this.value = value; }

        @Override
        public String toString() {
            return "(" + key + " : " + value + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Pair)) return false;
            Pair<?, ?> p = (Pair<?, ?>) obj;
            return key.equals(p.key) && value.equals(p.value);
        }

        @Override
        public int hashCode() {
            return key.hashCode() ^ value.hashCode();
        }
    }
}
