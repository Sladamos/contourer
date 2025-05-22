package com.sladamos.rank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StepCalculator {
    public BigDecimal calculateStep(BigDecimal maxValue, BigDecimal minValue, int numberOfRanks) {
        return maxValue.subtract(minValue).divide(BigDecimal.valueOf(numberOfRanks), RoundingMode.HALF_UP);
    }
}