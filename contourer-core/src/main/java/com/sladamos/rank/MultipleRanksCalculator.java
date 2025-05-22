package com.sladamos.rank;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class MultipleRanksCalculator implements RankCalculator {

    private final StepCalculator stepCalculator;

    @Override
    public int calculateRank(RankCalculatorData rankCalculatorData) {
        BigDecimal height = rankCalculatorData.height();
        BigDecimal minValue = rankCalculatorData.minValue();
        int numberOfRanks = rankCalculatorData.numberOfRanks();
        BigDecimal maxValue = rankCalculatorData.maxValue();

        BigDecimal step = stepCalculator.calculateStep(maxValue, minValue, numberOfRanks);

        int rawRank = height.subtract(minValue)
                .divide(step, RoundingMode.DOWN)
                .intValue();

        int maxRank = numberOfRanks - 1;

        return Math.min(rawRank, maxRank);
    }

}
