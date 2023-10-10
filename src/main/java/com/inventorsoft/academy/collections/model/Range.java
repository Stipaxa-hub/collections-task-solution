package com.inventorsoft.academy.collections.model;

import java.util.Collection;
import java.util.Iterator;

import java.util.Set;
import java.util.function.Function;

public class Range<T extends Comparable<T>> implements Set<T> {
    private T start;
    private T finish;
    private Function<T, T> functionIteration;

    private Range(T start, T finish, Function<T, T> functionIteration) {
        this.start = start;
        this.finish = finish;
        this.functionIteration = functionIteration;
    }

    public static <R extends Comparable<R>> Range<R> of(R start, R finish) {
        return new Range<>(start, finish, null);
    }
    public static <R extends Comparable<R>> Range<R> of(R start, R finish, Function<R, R> function) {
        return new Range<>(start, finish, function);
    }

    public int size() {
        if (start.compareTo(finish) == 0) {
            return 0;
        }
        int size = 0;
        T current = start;
        while (current.compareTo(finish) <= 0) {
            size++;
            if (functionIteration != null) {
                current = functionIteration.apply(current);
            } else {
                current = next(current);
            }
        }
        return size;
    }
    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Object o) {
        T value = (T) o;
        return value.compareTo(start) >= 0 && value.compareTo(finish) <= 0;
    }

    public Iterator<T> iterator() {
        return new RangeIterator();
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    public boolean add(T t) {
        return false;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return c.stream()
                .allMatch(this::contains);
    }

    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public void clear() {
    }

    private T next(T current) {
        if (current instanceof Integer) {
            return (T) (Integer.valueOf(((Integer) (current)) + 1));
        } else if (current instanceof Float) {
            return (T) (Float.valueOf(((Float) current) + 0.1f));
        } else if (current instanceof Double) {
            return (T) (Double.valueOf(((Double) current) + 0.1));
        } else {
            throw new RuntimeException("Illegal type!");
        }
    }

    private class RangeIterator implements Iterator<T> {
        private T current = start;
        @Override
        public boolean hasNext() {
            return current.compareTo(finish) < 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new RuntimeException("No next elements!");
            }
            T value = current;
            current = functionIteration.apply(current);
            return value;
        }
    }
}
