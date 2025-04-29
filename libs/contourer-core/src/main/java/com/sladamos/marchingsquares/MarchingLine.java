package com.sladamos.marchingsquares;

import com.sladamos.util.Point;

public record MarchingLine(Point first, Point second) {

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)-(%.1f, %.1f)", first.x(), first.y(), second.x(), second.y());
    }
}
