package by.it.group510901.artykov.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<E> implements Deque<E> {

    private static class Node<E> {
        E value;
        Node<E> prev;
        Node<E> next;
        Node(E value) { this.value = value; }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> cur = head;
        while (cur != null) {
            sb.append(cur.value);
            if (cur.next != null) sb.append(", ");
            cur = cur.next;
        }
        return sb.append(']').toString();
    }

    @Override
    public void addFirst(E element) {
        Node<E> node = new Node<>(element);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> node = new Node<>(element);
        if (tail == null) {
            head = tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public E getFirst() {
        if (head == null) throw new NoSuchElementException();
        return head.value;
    }

    @Override
    public E getLast() {
        if (tail == null) throw new NoSuchElementException();
        return tail.value;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E pollFirst() {
        if (head == null) return null;
        E value = head.value;
        head = head.next;
        if (head == null) tail = null;
        else head.prev = null;
        size--;
        return value;
    }

    @Override
    public E pollLast() {
        if (tail == null) return null;
        E value = tail.value;
        tail = tail.prev;
        if (tail == null) head = null;
        else tail.next = null;
        size--;
        return value;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    // ---------- Дополнительные методы для задания B ----------

    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(String.valueOf(index));
        Node<E> cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        unlink(cur);
        return cur.value;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> cur = head;
        while (cur != null) {
            if (Objects.equals(cur.value, o)) {
                unlink(cur);
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    private void unlink(Node<E> node) {
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if (prev == null) head = next; else prev.next = next;
        if (next == null) tail = prev; else next.prev = prev;
        size--;
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

    @Override public E peekFirst() { return head == null ? null : head.value; }
    @Override public E peekLast() { return tail == null ? null : tail.value; }
    @Override public boolean removeFirstOccurrence(Object o) { return remove(o); }
    @Override public boolean removeLastOccurrence(Object o) { return false; }
    @Override public boolean offer(E e) { return offerLast(e); }
    @Override public E remove() { return removeFirst(); }
    @Override public E peek() { return peekFirst(); }
    @Override public boolean addAll(Collection<? extends E> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public void clear() { head = tail = null; size = 0; }
    @Override public void push(E e) { addFirst(e);  }
    @Override public E pop() { return removeFirst(); }
    @Override public boolean containsAll(Collection<?> c) { return false; }
    @Override public boolean contains(Object o) { return false; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
    @Override public Iterator<E> descendingIterator() { return null; }
}