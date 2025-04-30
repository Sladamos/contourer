package com.sladamos.marchingsquares;

import java.util.List;

public record MarchingRow(List<MarchingSquare> squares) {

    public MarchingSquare getSquare(int i) {
        return squares.get(i);
    }
}
