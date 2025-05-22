package com.sladamos.rank;

import java.math.BigDecimal;

public class DualRanksCalculator implements RankCalculator {
    @Override
    public int calculateRank(RankCalculatorData rankCalculatorData) {
        BigDecimal threshold = rankCalculatorData.threshold();
        return rankCalculatorData.height().compareTo(threshold) > -1 ? 1 : 0;
    }
}
