package by.it.group510901.artykov.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {

    private E[] elements;
    private int size = 0;

    public ListB() {
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
        // Формирует строковое представление списка в формате [элемент1, элемент2]
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
        // Если массив полностью заполнен, увеличиваем его размер в 2 раза
        if (size == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
        // Добавляем новый элемент в конец списка и увеличиваем счетчик размера
        elements[size] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        // Проверка на корректность индекса (выход за границы списка)
        if (index < 0 || index >= size) {
            return null;
        }
        // Сохраняем удаляемый элемент, чтобы вернуть его в конце метода
        E deletedElement = elements[index];
        // Сдвигаем все элементы справа от удаленного на одну позицию влево
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        // Уменьшаем размер списка и зануляем последний элемент для очистки памяти
        size--;
        elements[size] = null;
        return deletedElement;
    }

    @Override
    public int size() {
        // Возвращает текущее количество реальных элементов в списке
        return size;
    }

    @Override
    public void add(int index, E element) {
        // Проверка индекса (вставку можно делать и в самый конец, поэтому index > size)
        if (index < 0 || index > size) {
            return;
        }
        // Если массив заполнен, увеличиваем его размер в 2 раза
        if (size == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
        // Сдвигаем элементы вправо, начиная с конца, чтобы освободить место под нужным индексом
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        // Вставляем новый элемент на освободившееся место и увеличиваем размер
        elements[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        // Находим индекс искомого объекта в списке
        int index = indexOf(o);
        // Если объект найден (индекс не равен -1), удаляем его по индексу
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        // Проверка на корректность индекса
        if (index < 0 || index >= size) {
            return null;
        }
        // Сохраняем старое значение элемента
        E oldElement = elements[index];
        // Записываем на его место новый элемент
        elements[index] = element;
        // Возвращаем старое значение
        return oldElement;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {

        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        // Сбрасываем счетчик размера в 0
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        // Ищем объект, начиная с начала списка (с 0-го индекса)
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) return i;
            }
        }
        // Если элемент не найден, возвращаем -1
        return -1;
    }

    @Override
    public E get(int index) {
        // Возвращает элемент по указанному индексу с проверкой границ
        if (index < 0 || index >= size) {
            return null;
        }
        return elements[index];
    }

    @Override
    public boolean contains(Object o) {
        // Если метод indexOf возвращает не -1, значит элемент присутствует в списке
        return indexOf(o) != -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        // Ищем объект с конца списка (в обратном порядке)
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elements[i])) return i;
            }
        }
        // Если элемент не найден, возвращаем -1
        return -1;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public boolean containsAll(Collection<?> c) { return false; }

    @Override
    public boolean addAll(Collection<? extends E> c) { return false; }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) { return false; }

    @Override
    public boolean removeAll(Collection<?> c) { return false; }

    @Override
    public boolean retainAll(Collection<?> c) { return false; }

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
