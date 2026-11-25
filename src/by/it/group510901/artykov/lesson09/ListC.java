package by.it.group510901.artykov.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    private E[] elements;
    private int size = 0;


    public ListC() {
        this.elements = (E[]) new Object[10];
    }
    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        // Формирует строковое представление списка в формате [элемент1, ... ]
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        // Если массив заполнен, увеличиваем его размер в 2 раза
        if (size == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
        // Добавляем элемент в конец и увеличиваем размер
        elements[size] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        // Проверка корректности индекса
        if (index < 0 || index >= size) {
            return null;
        }
        // Сохраняем удаляемый элемент
        E deletedElement = elements[index];
        // Сдвигаем все элементы справа от удаленного влево
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        // Уменьшаем размер и зануляем последний элемент
        size--;
        elements[size] = null;
        return deletedElement;
    }

    @Override
    public int size() {
        // Возвращает текущее количество элементов в списке
        return size;
    }

    @Override
    public void add(int index, E element) {
        // Проверка границ индекса
        if (index < 0 || index > size) {
            return;
        }
        // Если массив полон, увеличиваем его в 2 раза
        if (size == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
        // Освобождаем место, сдвигая элементы вправо
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        // Вставляем новый элемент
        elements[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        // Находим индекс объекта и удаляем его, если он существует
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        // Проверка индекса
        if (index < 0 || index >= size) {
            return null;
        }
        // Заменяем элемент и возвращаем старое значение
        E oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }

    @Override
    public boolean isEmpty() {
        // Проверка списка на пустоту
        return size == 0;
    }

    @Override
    public void clear() {
        // Полная очистка массива
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        // Поиск индекса элемента с начала списка
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        // Получение элемента по индексу
        if (index < 0 || index >= size) {
            return null;
        }
        return elements[index];
    }

    @Override
    public boolean contains(Object o) {
        // Проверка наличия элемента в списке
        return indexOf(o) != -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        // Поиск индекса элемента с конца списка
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elements[i])) return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // Проверяем, содержит ли наш список каждый элемент из переданной коллекции
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // Если переданная коллекция пустая, ничего не добавляем
        if (c.isEmpty()) {
            return false;
        }
        // Последовательно добавляем каждый элемент в конец нашего списка
        for (E item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        // Проверка корректности индекса для вставки
        if (index < 0 || index > size) {
            return false;
        }
        if (c.isEmpty()) {
            return false;
        }
        // Вставляем элементы поочередно, увеличивая индекс вставки для сохранения порядка
        int currentIndex = index;
        for (E item : c) {
            add(currentIndex, item);
            currentIndex++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }
        boolean modified = false;
        // Проходим по нашему списку с конца к началу, чтобы корректно удалять элементы по индексам
        for (int i = size - 1; i >= 0; i--) {
            if (c.contains(elements[i])) {
                remove(i);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        // Оставляем только те элементы, которые есть в коллекции c, остальные удаляем
        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(elements[i])) {
                remove(i);
                modified = true;
            }
        }
        return modified;
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public List<E> subList(int fromIndex, int toIndex) { return null; }

    @Override
    public ListIterator<E> listIterator(int index) { return null; }

    @Override
    public ListIterator<E> listIterator() { return null; }

    @Override
    public <T> T[] toArray(T[] a) { return null; }

    @Override
    public Object[] toArray() { return new Object[0]; }

    @Override
    public Iterator<E> iterator() { return null; }
}
