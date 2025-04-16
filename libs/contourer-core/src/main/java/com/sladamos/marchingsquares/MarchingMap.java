package com.sladamos.marchingsquares;

import java.util.List;

public record MarchingMap(List<MarchingRow> rows) {

    public MarchingRow getRow(int row) {
        return rows.get(row);
    }
}
