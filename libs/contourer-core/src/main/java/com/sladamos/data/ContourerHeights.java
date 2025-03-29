package com.sladamos.data;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ContourerHeights {

    private final List<ContourerRow> rows;

    public ContourerRow getRow(int row) {
        return rows.get(row);
    }
}
