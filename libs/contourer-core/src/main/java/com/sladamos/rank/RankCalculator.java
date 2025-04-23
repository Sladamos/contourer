package com.sladamos.rank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RankCalculator {

    public int calculateRank(RankCalculatorData rankCalculatorData) {
        BigDecimal height = rankCalculatorData.height();
        BigDecimal minValue = rankCalculatorData.minValue();
        int maxRank = rankCalculatorData.maxRank();
        BigDecimal step = rankCalculatorData.step();

        int rawRank = height.subtract(minValue)
                .divide(step, RoundingMode.DOWN)
                .intValue();

        return Math.min(rawRank, maxRank);
    }
}
