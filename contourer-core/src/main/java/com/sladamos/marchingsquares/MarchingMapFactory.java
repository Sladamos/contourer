package com.sladamos.marchingsquares;

import com.sladamos.data.ContourerData;
import com.sladamos.rank.RankCalculator;
import com.sladamos.rank.RankCalculatorData;
import com.sladamos.rank.StepCalculator;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@RequiredArgsConstructor
public class MarchingMapFactory {

    public static final int MINIMAL_NUMBER_OF_RANKS = 2;

    private final RankCalculator rankCalculator;

    private final StepCalculator stepCalculator;

    private final MarchingLinesDetector linesDetector;

    public MarchingMap createMap(ContourerData data, int numberOfRanks) {
        BigDecimal step = stepCalculator.calculateStep(data.getMaxValue(), data.getMinValue(), numberOfRanks);
        BigDecimal threshold = data.getMinValue();
        Set<MarchingLine> lines = new HashSet<>();
        for (int i = MINIMAL_NUMBER_OF_RANKS; i <= numberOfRanks; i++) {
            threshold = threshold.add(step);
            Function<BigDecimal, RankCalculatorData> calculatorDataFunction = createCalculatorDataFunction(threshold);
            List<MarchingRow> rows = data.getHeights().rows().parallelStream()
                    .map(row -> new MarchingRow(row.heights().parallelStream()
                            .map(this.createSquare(calculatorDataFunction))
                            .toList()))
                    .toList();
            lines.addAll(linesDetector.detectLines(rows));
        }
        return new MarchingMap(lines);
    }

    private Function<BigDecimal, MarchingSquare> createSquare(Function<BigDecimal, RankCalculatorData> calculatorDataFunction) {
        return height -> {
            RankCalculatorData calculatorData = calculatorDataFunction.apply(height);
            int rank = rankCalculator.calculateRank(calculatorData);
            return new MarchingSquare(rank, height);
        };
    }

    private Function<BigDecimal, RankCalculatorData> createCalculatorDataFunction(BigDecimal threshold) {
        return height -> RankCalculatorData.builder()
                .height(height)
                .threshold(threshold)
                .build();
    }
}