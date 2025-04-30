package com.sladamos.marchingsquares;

import com.sladamos.util.Point;

public record MarchingLine(Point first, Point second) {

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)-(%.1f, %.1f)", first.x(), first.y(), second.x(), second.y());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MarchingLine(Point first1, Point second1)) {
            return first.equals(first1) && second.equals(second1) || first.equals(second1) && second.equals(first1);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }
}
