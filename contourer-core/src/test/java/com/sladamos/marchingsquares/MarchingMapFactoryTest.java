package com.sladamos.marchingsquares;

import com.sladamos.data.ContourerData;
import com.sladamos.data.ContourerHeights;
import com.sladamos.data.ContourerRow;
import com.sladamos.rank.RankCalculator;
import com.sladamos.rank.RankCalculatorData;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarchingMapFactoryTest {

    @Mock
    private RankCalculator rankCalculator;

    @InjectMocks
    private MarchingMapFactory uut;

    @ParameterizedTest(name = "Ranks: {0}")
    @MethodSource("shouldCreateCorrectMarchingMapCases")
    void shouldCreateCorrectMarchingMap(int numberOfRanks, List<Integer> expectedRanks) {
        ContourerData data = ContourerData.builder()
                .heights(createContourerHeights())
                .minValue(BigDecimal.valueOf(100))
                .maxValue(BigDecimal.valueOf(200))
                .build();
        mockRankCalculator(data, expectedRanks, numberOfRanks);

        MarchingMap marchingMap = uut.createMap(data, numberOfRanks);

        List<Integer> actualRanks = marchingMap.rows().stream()
                .map(MarchingRow::squares)
                .flatMap(List::stream)
                .map(MarchingSquare::getRank)
                .toList();

        assertThat(actualRanks)
                .withFailMessage("Ranks in the generated marching map do not match the expected ranks.")
                .isEqualTo(expectedRanks);
    }

    private static Stream<Arguments> shouldCreateCorrectMarchingMapCases() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(1, List.of(0, 0, 0, 0, 0, 0)),
                org.junit.jupiter.params.provider.Arguments.of(2, List.of(0, 0, 1, 0, 1, 1))
        );
    }

    private ContourerHeights createContourerHeights() {
        return new ContourerHeights(List.of(
                new ContourerRow(List.of(BigDecimal.valueOf(100), BigDecimal.valueOf(125), BigDecimal.valueOf(150))),
                new ContourerRow(List.of(BigDecimal.valueOf(125), BigDecimal.valueOf(150), BigDecimal.valueOf(200)))
        ));
    }

    private void mockRankCalculator(ContourerData data, List<Integer> expectedRanks, int numberOfRanks) {
        List<BigDecimal> heights = data.getHeights().rows().stream().map(ContourerRow::heights).flatMap(List::stream).toList();
        IntStream.range(0, heights.size())
                .forEach(i -> when(rankCalculator.calculateRank(
                        new RankCalculatorData(heights.get(i), data.getMaxValue(), data.getMinValue(), numberOfRanks)))
                        .thenReturn(expectedRanks.get(i)));
    }
}