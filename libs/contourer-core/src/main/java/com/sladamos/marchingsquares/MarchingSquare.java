package com.sladamos.marchingsquares;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class MarchingSquare {
    int rank;
    BigDecimal value;
}
