package by.it.group510901.artykov.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class       ListA<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    private E[] elements;
    private int size = 0;

    public ListA() {
        this.elements = (E[]) new Object[10];
    }

    @Override
    public boolean add(E e) {
        // Если массив полностью заполнен, увеличиваем его размер в 2 раза
        if (size == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];
            // Копируем все элементы из старого массива в новый
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            // Переключаем ссылку на новый увеличенный массив
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
    public E get(int index) {
        // Проверяем, входит ли индекс в допустимый диапазон
        if (index < 0 || index >= size) {
            return null;
        }
        // Возвращает элемент по указанному индексу
        return elements[index];
    }


    /////////////////////////////////////////////////////////////////////////
    //////        Galan metodlar hökmany däl (Şonuň üçin boş galdy)    //////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {}

    @Override
    public boolean remove(Object o) { return false; }

    @Override
    public E set(int index, E element) { return null; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public void clear() {}

    @Override
    public int indexOf(Object o) { return 0; }

    @Override
    public boolean contains(Object o) { return false; }

    @Override
    public int lastIndexOf(Object o) { return 0; }

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
