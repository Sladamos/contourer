package com.sladamos.rank;

import java.math.BigDecimal;

public record RankCalculatorData(BigDecimal height, BigDecimal maxValue, BigDecimal minValue, int numberOfRanks) {
}
