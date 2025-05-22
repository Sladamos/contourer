package com.sladamos.rank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MultipleRanksCalculator implements RankCalculator {

    @Override
    public int calculateRank(RankCalculatorData rankCalculatorData) {
        BigDecimal height = rankCalculatorData.height();
        BigDecimal minValue = rankCalculatorData.minValue();
        int numberOfRanks = rankCalculatorData.numberOfRanks();
        BigDecimal maxValue = rankCalculatorData.maxValue();

        BigDecimal step = maxValue.subtract(minValue)
                .divide(BigDecimal.valueOf(numberOfRanks), RoundingMode.HALF_UP);

        int rawRank = height.subtract(minValue)
                .divide(step, RoundingMode.DOWN)
                .intValue();

        int maxRank = numberOfRanks - 1;

        return Math.min(rawRank, maxRank);
    }
}
