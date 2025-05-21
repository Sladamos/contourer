package com.sladamos.util;

public record Point(double x, double y) {
    public Point add(Point distance) {
        return new Point(x + distance.x, y + distance.y);
    }
}
