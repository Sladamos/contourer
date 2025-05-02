package com.sladamos.marchingsquares;

import com.sladamos.data.ContourerData;
import com.sladamos.rank.RankCalculator;
import com.sladamos.rank.RankCalculatorData;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@RequiredArgsConstructor
public class MarchingMapFactory {

    private final RankCalculator rankCalculator;

    private final MarchingLinesDetector linesDetector;

    public MarchingMap createMap(ContourerData data, int numberOfRanks) {
        Function<BigDecimal, RankCalculatorData> calculatorDataFunction = createCalculatorDataFunction(data, numberOfRanks);
        List<MarchingRow> rows = data.getHeights().rows().stream()
                .map(row -> new MarchingRow(row.heights().stream()
                        .map(this.createSquare(calculatorDataFunction))
                        .toList()))
                .toList();
        Set<MarchingLine> lines = linesDetector.detectLines(rows);
        return new MarchingMap(rows, lines);
    }

    private Function<BigDecimal, MarchingSquare> createSquare(Function<BigDecimal, RankCalculatorData> calculatorDataFunction) {
        return height -> {
            RankCalculatorData calculatorData = calculatorDataFunction.apply(height);
            int rank = rankCalculator.calculateRank(calculatorData);
            return new MarchingSquare(rank, height);
        };
    }

    private Function<BigDecimal, RankCalculatorData> createCalculatorDataFunction(ContourerData data, int numberOfRanks) {
        BigDecimal minValue = data.getMinValue();
        BigDecimal maxValue = data.getMaxValue();
        return height -> new RankCalculatorData(height, maxValue, minValue, numberOfRanks);
    }
}
