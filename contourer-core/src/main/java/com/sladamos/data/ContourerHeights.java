package com.sladamos.data;

import java.util.List;

public record ContourerHeights(List<ContourerRow> rows) {

    public ContourerRow getRow(int row) {
        return rows.get(row);
    }
}
