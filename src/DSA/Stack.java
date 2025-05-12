package DSA;

import java.util.Arrays;
import java.util.Iterator;

public class Stack<T> implements Iterable<T> {
    private T[] data;
    private int top;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    // Constructor
    @SuppressWarnings("unchecked")
    public Stack() {
        this.capacity = DEFAULT_CAPACITY;
        this.data = (T[]) new Object[capacity];  // Type-safe cast
        this.top = -1;
    }

    // Push an element onto the stack
    public void push(T value) {
        if (top == capacity - 1) {
            resize();
        }
        data[++top] = value;
    }

    // Pop the top element
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack Underflow: Cannot pop from empty stack.");
        }
        T value = data[top];
        data[top--] = null; // Avoid memory leaks
        return value;
    }

    // Peek the top element
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty. Nothing to peek.");
        }
        return data[top];
    }

    // Clear the stack
    public void clear() {
        for (int i = 0; i <= top; i++) {
            data[i] = null; // Help garbage collection
        }
        top = -1;
    }

    // Check if stack is empty
    public boolean isEmpty() {
        return top == -1;
    }

    // Get size of the stack
    public int size() {
        return top + 1;
    }

    public ArrayList<T> toArrayList() {
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i <= top; i++) {
            list.add(data[i]);
        }
        return list;
    }

    // Resize internal array
    @SuppressWarnings("unchecked")
    private void resize() {
        capacity = capacity * 2;
        data = Arrays.copyOf(data, capacity);
    }

    // Print stack (top to bottom)
    public void printStack() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }
        System.out.print("Top -> ");
        for (int i = top; i >= 0; i--) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    // Allow enhanced for-loop by implementing Iterable
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current <= top;
            }

            @Override
            public T next() {
                return data[current++];
            }
        };
    }
}
