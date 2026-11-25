package by.it.group510901.artykov.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.function.Predicate;

public class MyPriorityQueue<E> implements Queue<E> {

    private Object[] heap;
    private int size;

    public MyPriorityQueue() {
        heap = new Object[11];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {
        return ((Comparable<E>) a).compareTo(b);
    }

    private void grow() {
        int oldCapacity = heap.length;
        int newCapacity = oldCapacity < 64 ? oldCapacity + oldCapacity + 2 : oldCapacity + (oldCapacity >> 1);
        Object[] newHeap = new Object[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
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
            sb.append(heap[i]);
        }
        return sb.append(']').toString();
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) heap[i] = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    private int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (o.equals(heap[i])) return i;
            }
        }
        return -1;
    }

    @Override
    public boolean add(E element) {
        return offer(element);
    }

    @Override
    public boolean offer(E element) {
        if (element == null) throw new NullPointerException();
        if (size == heap.length) grow();
        int i = size;
        size++;
        siftUp(i, element);
        return true;
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int k, E x) {
        while (k > 0) {
            int parent = (k - 1) / 2;
            E e = (E) heap[parent];
            if (compare(x, e) >= 0) break;
            heap[k] = e;
            k = parent;
        }
        heap[k] = x;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E poll() {
        if (size == 0) return null;
        E result = (E) heap[0];
        int newSize = --size;
        E moved = (E) heap[newSize];
        heap[newSize] = null;
        if (newSize > 0) {
            siftDown(0, moved);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int k, E x) {
        int half = size / 2;
        while (k < half) {
            int child = 2 * k + 1;
            Object c = heap[child];
            int right = child + 1;
            if (right < size && compare((E) c, (E) heap[right]) > 0) {
                c = heap[child = right];
            }
            if (compare(x, (E) c) <= 0) break;
            heap[k] = c;
            k = child;
        }
        heap[k] = x;
    }

    @Override
    public E remove() {
        E e = poll();
        if (e == null) throw new NoSuchElementException();
        return e;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        return size == 0 ? null : (E) heap[0];
    }

    @Override
    public E element() {
        E e = peek();
        if (e == null) throw new NoSuchElementException();
        return e;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) modified = true;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i == -1) return false;
        removeAt(i);
        return true;
    }

    /** Точечное удаление одного элемента по индексу (как в java.util.PriorityQueue.removeAt). */
    @SuppressWarnings("unchecked")
    private void removeAt(int i) {
        int s = --size;
        if (s == i) {
            heap[i] = null;
        } else {
            E moved = (E) heap[s];
            heap[s] = null;
            siftDown(i, moved);
            if (heap[i] == moved) {
                siftUp(i, moved);
            }
        }
    }

    // ---------- Ключевое исправление: removeAll / retainAll через heapify ----------

    @Override
    public boolean removeAll(Collection<?> c) {
        return bulkRemove(c::contains);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return bulkRemove(e -> !c.contains(e));
    }

    /**
     * Точная копия алгоритма из современного java.util.PriorityQueue:
     * 1) компактируем "выживших" в начало массива, сохраняя их порядок;
     * 2) обнуляем хвост;
     * 3) перестраиваем кучу с нуля (heapify) за O(n).
     */
    @SuppressWarnings("unchecked")
    private boolean bulkRemove(Predicate<? super E> filter) {
        int w = 0;
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            E e = (E) heap[i];
            if (filter.test(e)) {
                modified = true;
            } else {
                heap[w++] = e;
            }
        }
        for (int i = w; i < size; i++) {
            heap[i] = null;
        }
        size = w;
        if (modified) {
            heapify();
        }
        return modified;
    }

    /** Стандартный bottom-up heapify (построение кучи за O(n), см. Heapsort). */
    @SuppressWarnings("unchecked")
    private void heapify() {
        for (int i = (size >>> 1) - 1; i >= 0; i--) {
            siftDown(i, (E) heap[i]);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            int index = 0;
            @Override public boolean hasNext() { return index < size; }
            @Override @SuppressWarnings("unchecked")
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (E) heap[index++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(heap, 0, result, 0, size);
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        return (T[]) toArray();
    }
}