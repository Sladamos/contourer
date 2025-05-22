package com.sladamos.rank;

import java.math.BigDecimal;

public class DualRanksCalculator implements RankCalculator {
    @Override
    public int calculateRank(RankCalculatorData rankCalculatorData) {
        BigDecimal threshold = rankCalculatorData.maxValue().add(rankCalculatorData.minValue()).divide(new BigDecimal(2));
        return rankCalculatorData.height().compareTo(threshold) > -1 ? 1 : 0;
    }
}
