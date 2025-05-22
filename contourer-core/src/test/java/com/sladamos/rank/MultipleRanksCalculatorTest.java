package com.sladamos.rank;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleRanksCalculatorTest {

    private final RankCalculator uut = new MultipleRanksCalculator(new StepCalculator()); //TODO switch to mock

    @ParameterizedTest(name = "Height: {0}, Expected Rank: {4}")
    @MethodSource("rankCases")
    void shouldCorrectlyCalculateRank(BigDecimal height, BigDecimal minValue, BigDecimal maxValue, int numberOfRanks, int expectedRank) {
        RankCalculatorData data = RankCalculatorData.builder()
                .height(height)
                .minValue(minValue)
                .maxValue(maxValue)
                .numberOfRanks(numberOfRanks)
                .build();

        int rank = uut.calculateRank(data);

        assertThat(rank)
                .withFailMessage("Expected rank %d for height %s but got %d", expectedRank, height, rank)
                .isEqualTo(expectedRank);
    }

    private static Stream<Arguments> rankCases() {
        return Stream.of(
                Arguments.of(new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("200"), 2, 0),
                Arguments.of(new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("200"), 2, 1),
                Arguments.of(new BigDecimal("150"), new BigDecimal("100"), new BigDecimal("200"), 2, 1),
                Arguments.of(new BigDecimal("149"), new BigDecimal("100"), new BigDecimal("200"), 2, 0),
                Arguments.of(new BigDecimal("199"), new BigDecimal("100"), new BigDecimal("400"), 3, 0),
                Arguments.of(new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("400"), 3, 1),
                Arguments.of(new BigDecimal("299"), new BigDecimal("100"), new BigDecimal("400"), 3, 1),
                Arguments.of(new BigDecimal("300"), new BigDecimal("100"), new BigDecimal("400"), 3, 2)
        );
    }

}