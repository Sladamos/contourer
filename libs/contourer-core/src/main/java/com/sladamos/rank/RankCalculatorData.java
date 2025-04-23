package com.sladamos.rank;

import java.math.BigDecimal;

public record RankCalculatorData(BigDecimal height, BigDecimal minValue, BigDecimal step, int maxRank) {
}
