package com.sladamos.data;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ContourerData {
    int numberOfColumns;
    int numberOfRows;
    BigDecimal xllCorner;
    BigDecimal yllCorner;
    BigDecimal dx;
    BigDecimal dy;
    ContourerHeights heights;
}
