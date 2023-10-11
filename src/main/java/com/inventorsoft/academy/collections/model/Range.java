package com.inventorsoft.academy.collections.model;

import java.util.Collection;
import java.util.Iterator;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class Range<T extends Comparable<T>> implements Set<T> {
    private T start;
    private T finish;
    private Function<T, T> functionIncrementation;

    private Range(T start, T finish, Function<T, T> functionIncrementation) {
        this.start = start;
        this.finish = finish;
        this.functionIncrementation = functionIncrementation;
    }

    public static Range<Integer> of(Integer start, Integer finish) {
        return of(start, finish, (Integer number) -> number + 1);
    }

    public static Range<Double> of(Double start, Double finish) {
        return of(start, finish, (Double number) -> number + 0.1);
    }

    public static Range<Float> of(Float start, Float finish) {
        return of(start, finish, (Float number) -> number + 0.1f);
    }

    public static <R extends Comparable<R>> Range<R> of(R start, R finish, Function<R, R> function) {
        if (start == null || finish == null || function == null) {
            throw new RuntimeException("One of parameters is null! Parameters must be not null!");
        }
        return new Range<>(start, finish, function);
    }

    public int size() {
        if (start.compareTo(finish) == 0) {
            return 0;
        }
        int size = 0;
        T current = start;
        while (current.compareTo(finish) <= 0) {
            current = functionIncrementation.apply(current);
            size++;
        }
        return size;
    }
    public boolean isEmpty() {
        return start.compareTo(finish) == 0;
    }

    public boolean contains(Object o) {
        if (!(o instanceof Comparable)) {
            return false;
        }
        T value = (T) o;
        return value.compareTo(start) >= 0 && value.compareTo(finish) <= 0;
    }

    public Iterator<T> iterator() {
        return new RangeIterator();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException("Operation creating array not supported");
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("Operation creating array not supported");
    }

    public boolean add(T t) {
        throw new UnsupportedOperationException("Operation adding not supported");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Operation removing not supported");
    }

    public boolean containsAll(Collection<?> c) {
        return c.stream()
                .allMatch(this::contains);
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Operation adding not supported");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operation retaining not supported");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operation removing not supported");
    }

    public void clear() {
        throw new UnsupportedOperationException("Operation clearing not supported");
    }

    private class RangeIterator implements Iterator<T> {
        private T current = start;
        @Override
        public boolean hasNext() {
            return current.compareTo(finish) <= 0;
        }

        @Override
        public T next() {
            T value = current;
            current = functionIncrementation.apply(current);
            return value;
        }
    }
}
