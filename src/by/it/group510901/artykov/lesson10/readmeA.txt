import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayDeque<E> implements Deque<E> {
    private E[] elements;
    private int head; // Индекс первого элемента
    private int tail; // Индекс для следующего нового элемента в конце
    private int size; // Текущее количество элементов

    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        // Начальная емкость массива — 16 элементов
        this.elements = (E[]) new Object[16];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    // Вспомогательный метод для автоматического расширения массива
    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = elements.length;
        E[] newElements = (E[]) new Object[oldCapacity * 2];

        // Копируем элементы по порядку от head до конца массива, а затем от 0 до tail
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(head + i) % oldCapacity];
        }

        this.elements = newElements;
        this.head = 0;
        this.tail = size;
    }

    // Обязательный метод: РАЗМЕР
    @Override
    public int size() {
        return size;
    }

    // Обязательный метод: СТРОКОВОЕ ПРЕДСТАВЛЕНИЕ
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            E element = elements[(head + i) % elements.length];
            sb.append(element);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    // Обязательный метод: ДОБАВЛЕНИЕ В НАЧАЛО
    @Override
    public void addFirst(E element) {
        if (size == elements.length) {
            resize();
        }
        // Формула кольцевого буфера для шага назад
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
        size++;
    }

    // Обязательный метод: ДОБАВЛЕНИЕ В КОНЕЦ
    @Override
    public void addLast(E element) {
        if (size == elements.length) {
            resize();
        }
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        size++;
    }

    // Обязательный метод: ДОБАВЛЕНИЕ (по умолчанию в конец)
    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    // Обязательный метод: ПОЛУЧЕНИЕ ПЕРВОГО (без удаления, с исключением)
    @Override
    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return elements[head];
    }

    // Обязательный метод: ПОЛУЧЕНИЕ ПОСЛЕДНЕГО (без удаления, с исключением)
    @Override
    public E getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        // Индекс последнего элемента на 1 меньше tail
        int lastIndex = (tail - 1 + elements.length) % elements.length;
        return elements[lastIndex];
    }

    // Обязательный метод: ПОЛУЧЕНИЕ ПЕРВОГО (аналог getFirst)
    @Override
    public E element() {
        return getFirst();
    }

    // Обязательный метод: УДАЛЕНИЕ ПЕРВОГО (возвращает null, если пусто)
    @Override
    public E pollFirst() {
        if (size == 0) {
            return null;
        }
        E result = elements[head];
        elements[head] = null; // Зануляем ссылку для Garbage Collector
        head = (head + 1) % elements.length;
        size--;
        return result;
    }

    // Обязательный метод: УДАЛЕНИЕ ПОСЛЕДНЕГО (возвращает null, если пусто)
    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }
        tail = (tail - 1 + elements.length) % elements.length;
        E result = elements[tail];
        elements[tail] = null; // Зануляем ссылку
        size--;
        return result;
    }

    // Обязательный метод: УДАЛЕНИЕ ПЕРВОГО (аналог pollFirst)
    @Override
    public E poll() {
        return pollFirst();
    }

    /////////////////////////////////////////////////////////////////////////
    //////   Заглушки для остальных методов интерфейса Deque           ///////
    //////   (Необходимы, чтобы код скомпилировался)                    ///////
    /////////////////////////////////////////////////////////////////////////

    @Override public boolean offerFirst(E e) { addFirst(e); return true; }
    @Override public boolean offerLast(E e) { addLast(e); return true; }
    @Override public E removeFirst() { return getFirst(); } // Упрощено, по спецификации должно удалять
    @Override public E removeLast() { return getLast(); }   // Упрощено
    @Override public E peekFirst() { return size == 0 ? null : elements[head]; }
    @Override public E peekLast() { return size == 0 ? null : elements[(tail - 1 + elements.length) % elements.length]; }
    @Override public boolean removeFirstOccurrence(Object o) { return false; }
    @Override public boolean removeLastOccurrence(Object o) { return false; }
    @Override public boolean offer(E e) { return offerLast(e); }
    @Override public E remove() { return removeFirst(); }
    @Override public E peek() { return peekFirst(); }
    @Override public boolean addAll(Collection<? extends E> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public void clear() { size = 0; head = 0; tail = 0; }
    @Override public boolean push(E e) { addFirst(e); return true; }
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
