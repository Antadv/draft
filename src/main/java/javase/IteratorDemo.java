package javase;

import java.util.Iterator;

/**
 * 自定义可foreach的类
 *      1. 实现 {@link Iterable} 来获取迭代器
 *      2. 新建内部类实现 {@link Iterator} 做迭代操作
 *
 * @author LBG - 2017/10/12 0012
 */
public class IteratorDemo<T> implements Iterable<T> {

    private Object[] table;
    private int size;

    public IteratorDemo(int capacity) {
        table = new Object[capacity];
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    public boolean add(T t) {
        if (size == table.length) {
            return false;
        }
        table[size++] = t;
        return true;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    class Itr implements Iterator<T> {
        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor + 1 <= size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            return (T) table[cursor++];
        }

        @Override
        public void remove() {
        }
    }

    public static void main(String[] args) {
        IteratorDemo<String> demo = new IteratorDemo<>(2);
        demo.add("hello");
        demo.add("world");
        for (String s : demo) {
            System.out.println(s);
        }
    }
}
