package by.it.group510901.artykov.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayDeque<E> implements Deque<E> {
    private E[] elements;
    private int head;
    private int tail;
    private int size;

    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        this.elements = (E[]) new Object[16];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = elements.length;
        E[] newElements = (E[]) new Object[oldCapacity * 2];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(head + i) % oldCapacity];
        }
        this.elements = newElements;
        this.head = 0;
        this.tail = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(elements[(head + i) % elements.length]);
        }
        return sb.append(']').toString();
    }

    @Override
    public void addFirst(E element) {
        if (size == elements.length) resize();
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        if (size == elements.length) resize();
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        size++;
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public E getFirst() {
        if (size == 0) throw new NoSuchElementException();
        return elements[head];
    }

    @Override
    public E getLast() {
        if (size == 0) throw new NoSuchElementException();
        int lastIndex = (tail - 1 + elements.length) % elements.length;
        return elements[lastIndex];
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0) return null;
        E result = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return result;
    }

    @Override
    public E pollLast() {
        if (size == 0) return null;
        tail = (tail - 1 + elements.length) % elements.length;
        E result = elements[tail];
        elements[tail] = null;
        size--;
        return result;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    // ---------- остальное (для совместимости с интерфейсом Deque) ----------

    @Override public boolean offerFirst(E e) { addFirst(e); return true; }
    @Override public boolean offerLast(E e) { addLast(e); return true; }

    @Override
    public E removeFirst() {
        E e = pollFirst();
        if (e == null) throw new NoSuchElementException();
        return e;
    }

    @Override
    public E removeLast() {
        E e = pollLast();
        if (e == null) throw new NoSuchElementException();
        return e;
    }

    @Override public E peekFirst() { return size == 0 ? null : elements[head]; }
    @Override public E peekLast() {
        return size == 0 ? null : elements[(tail - 1 + elements.length) % elements.length];
    }

    @Override public boolean removeFirstOccurrence(Object o) { return false; }
    @Override public boolean removeLastOccurrence(Object o) { return false; }
    @Override public boolean offer(E e) { return offerLast(e); }
    @Override public E remove() { return removeFirst(); }
    @Override public E peek() { return peekFirst(); }
    @Override public boolean addAll(Collection<? extends E> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public void clear() { size = 0; head = 0; tail = 0; }
    @Override public void push(E e) { addFirst(e); }
    @Override public E pop() { return removeFirst(); }
    @Override public boolean remove(Object o) { return false; }
    @Override public boolean containsAll(Collection<?> c) { return false; }
    @Override public boolean contains(Object o) { return false; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
    @Override public Iterator<E> descendingIterator() { return null; }
}