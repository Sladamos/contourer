package com.sladamos.marchingsquares;

import com.sladamos.data.ContourerData;
import com.sladamos.data.ContourerHeights;
import com.sladamos.data.ContourerRow;
import com.sladamos.rank.RankCalculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MarchingMapFactoryTest {

    private final RankCalculator rankCalculator = new RankCalculator();

    private final MarchingMapFactory uut = new MarchingMapFactory(rankCalculator);

    @ParameterizedTest(name = "Ranks: {0}")
    @MethodSource("testCases")
    void shouldCreateCorrectMarchingMap(int numberOfRanks, List<List<Integer>> expectedRanks) {
        ContourerData data = ContourerData.builder()
                .heights(createContourerHeights())
                .minValue(BigDecimal.valueOf(100))
                .maxValue(BigDecimal.valueOf(200))
                .build();

        MarchingMap marchingMap = uut.createMap(data, numberOfRanks);

        for (int row = 0; row < expectedRanks.size(); row++) {
            for (int col = 0; col < expectedRanks.get(row).size(); col++) {
                int actual = marchingMap.getRow(row).getSquare(col).getRank();
                int expected = expectedRanks.get(row).get(col);
                assertThat(actual)
                        .withFailMessage("Rank at (%d,%d) should be %d, but was %d", row, col, expected, actual)
                        .isEqualTo(expected);
            }
        }
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(1, List.of(
                        List.of(0, 0, 0),
                        List.of(0, 0, 0)
                )),
                org.junit.jupiter.params.provider.Arguments.of(2, List.of(
                        List.of(0, 0, 1),
                        List.of(0, 1, 1)
                ))
        );
    }

    private ContourerHeights createContourerHeights() {
        return new ContourerHeights(List.of(
                new ContourerRow(List.of(BigDecimal.valueOf(100), BigDecimal.valueOf(125), BigDecimal.valueOf(150))),
                new ContourerRow(List.of(BigDecimal.valueOf(125), BigDecimal.valueOf(150), BigDecimal.valueOf(200)))
        ));
    }
}