package com.sladamos.marchingsquares;

import com.sladamos.data.ContourerData;
import com.sladamos.data.ContourerHeights;
import com.sladamos.data.ContourerRow;
import com.sladamos.rank.RankCalculator;
import com.sladamos.rank.RankCalculatorData;
import com.sladamos.rank.StepCalculator;
import com.sladamos.util.Point;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarchingMapFactoryTest {

    @Mock
    private RankCalculator rankCalculator;

    @Mock
    private MarchingLinesDetector linesDetector;

    @Spy
    private StepCalculator stepCalculator;

    @InjectMocks
    private MarchingMapFactory uut;

    @ParameterizedTest(name = "Ranks: {0}")
    @MethodSource("shouldCreateCorrectMarchingMapCases")
    void shouldCreateCorrectMarchingMap(int numberOfRanks, List<Integer> expectedRanks, Set<MarchingLine> expectedLines) {
        ContourerData data = ContourerData.builder()
                .heights(createContourerHeights())
                .minValue(BigDecimal.valueOf(100))
                .maxValue(BigDecimal.valueOf(200))
                .build();
        if (numberOfRanks >= MarchingMapFactory.MINIMAL_NUMBER_OF_RANKS) {
            mockRankCalculator(data, expectedRanks);
            when(linesDetector.detectLines(any())).thenReturn(expectedLines);
        }

        MarchingMap marchingMap = uut.createMap(data, numberOfRanks);

        assertThat(marchingMap.lines()).isEqualTo(expectedLines);
    }

    private static Stream<Arguments> shouldCreateCorrectMarchingMapCases() {
        return Stream.of(
                Arguments.of(1, List.of(0, 0, 0, 0, 0, 0), Set.of()),
                Arguments.of(MarchingMapFactory.MINIMAL_NUMBER_OF_RANKS, List.of(0, 0, 1, 0, 1, 1), Set.of(new MarchingLine(new Point(0, 1), new Point(3, 1))))
        );
    }

    private ContourerHeights createContourerHeights() {
        return new ContourerHeights(List.of(
                new ContourerRow(List.of(BigDecimal.valueOf(100), BigDecimal.valueOf(125), BigDecimal.valueOf(150))),
                new ContourerRow(List.of(BigDecimal.valueOf(125), BigDecimal.valueOf(150), BigDecimal.valueOf(200)))
        ));
    }

    private void mockRankCalculator(ContourerData data, List<Integer> expectedRanks) {
        List<BigDecimal> heights = data.getHeights().rows().stream().map(ContourerRow::heights).flatMap(List::stream).toList();
        IntStream.range(0, heights.size())
                .forEach(i -> when(rankCalculator.calculateRank(
                        RankCalculatorData.builder()
                                .height(heights.get(i))
                                .threshold(data.getMaxValue().add(data.getMinValue()).divide(BigDecimal.valueOf(MarchingMapFactory.MINIMAL_NUMBER_OF_RANKS), RoundingMode.UNNECESSARY))
                                .build()))
                        .thenReturn(expectedRanks.get(i)));
    }
}