package com.sladamos.data;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ContourerRow {

    private final List<BigDecimal> heights;

    public BigDecimal getHeight(int i) {
        return heights.get(i);
    }
}
