package com.sladamos.data;

import java.math.BigDecimal;
import java.util.List;

public record ContourerRow(List<BigDecimal> heights) {

    public BigDecimal getHeight(int i) {
        return heights.get(i);
    }
}
