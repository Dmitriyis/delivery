package com.delivery.ddd;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public abstract class ValueObject<T extends ValueObject<T>> implements Comparable<T> {

    protected abstract List<Object> equalityComponents();

    @SuppressWarnings("unchecked")
    protected static <V> int safeCompare(V a, V b) {
        if (a == b) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }

        if (a instanceof BigDecimal && b instanceof BigDecimal) {
            return ((BigDecimal) a).compareTo((BigDecimal) b);
        }

        if (!(a instanceof Comparable) || !(b instanceof Comparable)) {
            throw new IllegalArgumentException("Fields must be Comparable");
        }

        return ((Comparable<V>) a).compareTo(b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValueObject<?> that = (ValueObject<?>) o;

        List<Object> thisComponents = this.equalityComponents();
        List<Object> thatComponents = that.equalityComponents();

        if (thisComponents.size() != thatComponents.size()) {
            return false;
        }

        for (int i = 0; i < thisComponents.size(); i++) {
            if (!Objects.equals(thisComponents.get(i), thatComponents.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(equalityComponents().toArray());
    }

    @Override
    public int compareTo(T other) {
        List<Object> thisComponents  = this.equalityComponents();
        List<Object> otherComponents  = other.equalityComponents();

        for (int i = 0; i < thisComponents.size() && i < otherComponents.size(); i++) {
            int result = safeCompare(thisComponents.get(i), otherComponents.get(i));

            if (result != 0) {
                return result;
            }
        }

        return Integer.compare(thisComponents.size(), otherComponents.size());
    }

    @Override
    public String toString() {
        List<Object> components = equalityComponents();
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("[");
        for (int i = 0; i < components.size(); i++) {
            sb.append(components.get(i));
            if (i < components.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
