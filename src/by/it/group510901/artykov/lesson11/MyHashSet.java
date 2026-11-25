package by.it.group510901.artykov.lesson11;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {

    private static class Node<E> {
        final E value;
        final int hash;
        Node<E> next;

        Node(E value, int hash, Node<E> next) {
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
    }

    private Node<E>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
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
        Node<E>[] old = table;
        Node<E>[] fresh = (Node<E>[]) new Node[old.length * 2];
        for (int i = 0; i < old.length; i++) {
            Node<E> n = old[i];
            while (n != null) {
                Node<E> next = n.next;
                int idx = n.hash & (fresh.length - 1);
                n.next = fresh[idx];
                fresh[idx] = n;
                n = next;
            }
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
        table[idx] = new Node<>(e, h, table[idx]);
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
            private int bucket = 0;
            private Node<E> next = nextNode(null);
            private Node<E> last = null;

            private Node<E> nextNode(Node<E> cur) {
                if (cur != null && cur.next != null) {
                    return cur.next;
                }
                while (bucket < table.length) {
                    Node<E> n = table[bucket++];
                    if (n != null) {
                        return n;
                    }
                }
                return null;
            }

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
                next = nextNode(next);
                return last.value;
            }

            @Override
            public void remove() {
                if (last == null) {
                    throw new IllegalStateException();
                }
                MyHashSet.this.remove(last.value);
                last = null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (E e : this) {
            result[i++] = e;
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
        for (E e : this) {
            a[i++] = (T) e;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (E e : this) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(e);
        }
        return sb.append(']').toString();
    }
}