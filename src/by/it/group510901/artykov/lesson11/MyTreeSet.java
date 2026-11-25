package by.it.group510901.artykov.lesson11;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {

    private Object[] data = new Object[16];
    private int size;

    @SuppressWarnings("unchecked")
    private int search(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        Comparable<Object> key = (Comparable<Object>) o;
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int cmp = key.compareTo(data[mid]);
            if (cmp > 0) {
                lo = mid + 1;
            } else if (cmp < 0) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return -(lo + 1);
    }

    private void removeAt(int index) {
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--size] = null;
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
        return search(o) >= 0;
    }

    @Override
    public boolean add(E e) {
        int idx = search(e);
        if (idx >= 0) {
            return false;
        }
        int pos = -idx - 1;
        if (size == data.length) {
            Object[] fresh = new Object[data.length * 2];
            for (int i = 0; i < size; i++) {
                fresh[i] = data[i];
            }
            data = fresh;
        }
        for (int i = size; i > pos; i--) {
            data[i] = data[i - 1];
        }
        data[pos] = e;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int idx = search(o);
        if (idx < 0) {
            return false;
        }
        removeAt(idx);
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
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

    private boolean filter(Collection<?> c, boolean keepIfContained) {
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(data[i]) == keepIfContained) {
                data[j++] = data[i];
            }
        }
        boolean changed = j != size;
        for (int i = j; i < size; i++) {
            data[i] = null;
        }
        size = j;
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return filter(c, false);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return filter(c, true);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int cursor = 0;
            private int last = -1;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                if (cursor >= size) {
                    throw new NoSuchElementException();
                }
                last = cursor++;
                return (E) data[last];
            }

            @Override
            public void remove() {
                if (last < 0) {
                    throw new IllegalStateException();
                }
                removeAt(last);
                cursor = last;
                last = -1;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = data[i];
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }
        for (int i = 0; i < size; i++) {
            a[i] = (T) data[i];
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(data[i]);
        }
        return sb.append(']').toString();
    }
}