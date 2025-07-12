package dev.atomic;

import java.util.concurrent.atomic.AtomicReference;

class Stack<T>{

    static class Node<T> {
        T value;
        Node<T> next;


        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private final AtomicReference<Node<T>> top = new AtomicReference<>(null);

    public void push(T val) {
        Node<T> currentTop = top.get();
        Node<T> newNode = new Node<>(val, currentTop);
        while (!top.compareAndSet(currentTop, newNode)) {
            currentTop = top.get();
            newNode = new Node<>(val, currentTop);
        }
    }

    public T pop() {
        Node<T> currentTop = top.get();

        while (currentTop != null) {
            Node<T> next = currentTop.next;

            if (top.compareAndSet(currentTop, next)) {
                return currentTop.value;
            }

            currentTop = top.get(); // retry with latest top
        }

        return null; // stack was empty
    }
}


public class AtomicStackMain {
    public static void main(String[] args) {

        Stack<String> stack = new Stack<>();


    }
}
