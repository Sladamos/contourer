package com.sladamos.rank;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RankCalculatorData(BigDecimal threshold, BigDecimal height, BigDecimal maxValue, BigDecimal minValue,
                                 int numberOfRanks) {
}
