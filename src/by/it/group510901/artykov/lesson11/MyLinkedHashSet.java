package by.it.group510901.artykov.lesson11;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    private static class Node<E> {
        final E value;
        final int hash;
        Node<E> next;
        Node<E> before;
        Node<E> after;

        Node(E value, int hash) {
            this.value = value;
            this.hash = hash;
        }
    }

    private Node<E>[] table;
    private Node<E> head;
    private Node<E> tail;
    private int size;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        table = (Node<E>[]) new Node[16];
    }

    private static int hash(Object o) {
        int h = o == null ? 0 : o.hashCode();
        return h ^ (h >>> 16);
    }

    private static boolean eq(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<E>[] fresh = (Node<E>[]) new Node[table.length * 2];
        for (Node<E> n = head; n != null; n = n.after) {
            int idx = n.hash & (fresh.length - 1);
            n.next = fresh[idx];
            fresh[idx] = n;
        }
        table = fresh;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int h = hash(o);
        for (Node<E> n = table[h & (table.length - 1)]; n != null; n = n.next) {
            if (n.hash == h && eq(n.value, o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        int h = hash(e);
        int idx = h & (table.length - 1);
        for (Node<E> n = table[idx]; n != null; n = n.next) {
            if (n.hash == h && eq(n.value, e)) {
                return false;
            }
        }
        Node<E> node = new Node<>(e, h);
        node.next = table[idx];
        table[idx] = node;
        node.before = tail;
        if (tail == null) {
            head = node;
        } else {
            tail.after = node;
        }
        tail = node;
        size++;
        if (size > table.length * 3 / 4) {
            resize();
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int h = hash(o);
        int idx = h & (table.length - 1);
        Node<E> prev = null;
        for (Node<E> n = table[idx]; n != null; prev = n, n = n.next) {
            if (n.hash == h && eq(n.value, o)) {
                if (prev == null) {
                    table[idx] = n.next;
                } else {
                    prev.next = n.next;
                }
                if (n.before == null) {
                    head = n.after;
                } else {
                    n.before.after = n.after;
                }
                if (n.after == null) {
                    tail = n.before;
                } else {
                    n.after.before = n.before;
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E e : c) {
            if (add(e)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> next = head;
            private Node<E> last = null;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public E next() {
                if (next == null) {
                    throw new NoSuchElementException();
                }
                last = next;
                next = next.after;
                return last.value;
            }

            @Override
            public void remove() {
                if (last == null) {
                    throw new IllegalStateException();
                }
                MyLinkedHashSet.this.remove(last.value);
                last = null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> n = head; n != null; n = n.after) {
            result[i++] = n.value;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        for (Node<E> n = head; n != null; n = n.after) {
            a[i++] = (T) n.value;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node<E> n = head; n != null; n = n.after) {
            if (n != head) {
                sb.append(", ");
            }
            sb.append(n.value);
        }
        return sb.append(']').toString();
    }
}