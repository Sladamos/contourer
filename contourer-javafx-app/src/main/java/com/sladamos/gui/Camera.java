package com.sladamos.gui;

import com.sladamos.util.Point;

public interface Camera {
    void zoomIn();

    void zoomOut();

    void move(Point distance);

    void reset();

    Point getOffset();

    double getSquareSize();
}