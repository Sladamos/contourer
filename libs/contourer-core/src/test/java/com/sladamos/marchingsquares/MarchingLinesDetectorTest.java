package com.sladamos.marchingsquares;

import com.sladamos.util.Point;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MarchingLinesDetectorTest {

    private final MarchingLinesDetector uut = new MarchingLinesDetector();

    @ParameterizedTest(name = "Lines: {1}")
    @MethodSource("detectLinesOnTwoRanksSingleSquareArgs")
    void shouldDetectLinesOnTwoRanksSingleSquare(List<MarchingRow> marchingRows, Set<MarchingLine> expectedLines) {

        Set<MarchingLine> lines = uut.detectLines(marchingRows);

        assertThat(lines)
                .withFailMessage("Lines %s does not match expected lines %s.", lines, expectedLines)
                .isEqualTo(expectedLines);
    }

    @Test
    void shouldSetXOffsetProperly() {
        List<MarchingRow> marchingRows = List.of(
                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0), createMarchingSquare(1))),
                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0), createMarchingSquare(0)))
        );

        Set<MarchingLine> lines = uut.detectLines(marchingRows);

        assertThat(lines).isEqualTo(Set.of(
                new MarchingLine(new Point(2, 0.5), new Point(2.5, 1)),
                new MarchingLine(new Point(2, 0.5), new Point(2, 0)),
                new MarchingLine(new Point(3, 1), new Point(2.5, 1))
        ));
    }

    @Test
    void shouldSetYOffsetProperly() {
        List<MarchingRow> marchingRows = List.of(
                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0))),
                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0))),
                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1)))
        );

        Set<MarchingLine> lines = uut.detectLines(marchingRows);

        assertThat(lines).isEqualTo(Set.of(
                new MarchingLine(new Point(1, 2.5), new Point(1, 3)),
                new MarchingLine(new Point(1, 2.5), new Point(1.5, 2)),
                new MarchingLine(new Point(1.5, 2), new Point(2, 2))
        ));
    }

    @ParameterizedTest(name = "Lines: {1}")
    @MethodSource("detectLinesOnTwoRanksMultipleSquareArgs")
    void shouldDetectLinesOnTwoRanksMultipleSquares(List<MarchingRow> marchingRows, Set<MarchingLine> expectedLines) {

        Set<MarchingLine> lines = uut.detectLines(marchingRows);

        assertThat(lines)
                .withFailMessage("Lines %s does not match expected lines %s.", lines, expectedLines)
                .isEqualTo(expectedLines);
    }

    static Stream<Arguments> detectLinesOnTwoRanksSingleSquareArgs() {
        MarchingLine rightLine = new MarchingLine(new Point(1.5, 1), new Point(2, 1));
        MarchingLine leftLine = new MarchingLine(new Point(0, 1), new Point(0.5, 1));
        MarchingLine bottomLine = new MarchingLine(new Point(1, 1.5), new Point(1, 2));
        MarchingLine topLine = new MarchingLine(new Point(1, 0), new Point(1, 0.5));
        return Stream.of(
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0)))
                        ),
                        Set.of()),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1)))
                        ),
                        Set.of()),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0.5, 1), new Point(1, 0.5)),
                                topLine,
                                leftLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(1.5, 1), new Point(1, 0.5)),
                                topLine,
                                rightLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0.5, 1), new Point(1, 1.5)),
                                bottomLine,
                                leftLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(1.5, 1), new Point(1, 1.5)),
                                bottomLine,
                                rightLine
                        )),

                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0.5, 1), new Point(1, 0.5)),
                                topLine,
                                leftLine)),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(1.5, 1), new Point(1, 0.5)),
                                topLine,
                                rightLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0.5, 1), new Point(1, 1.5)),
                                bottomLine,
                                leftLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(1.5, 1), new Point(1, 1.5)),
                                bottomLine,
                                rightLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0.5, 1), new Point(1.5, 1)),
                                rightLine,
                                leftLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(1, 0.5), new Point(1, 1.5)),
                                topLine,
                                bottomLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(1, 0.5), new Point(1, 1.5)),
                                topLine,
                                bottomLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0.5, 1), new Point(1.5, 1)),
                                rightLine,
                                leftLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(1.5, 1), new Point(1, 0.5)),
                                new MarchingLine(new Point(0.5, 1), new Point(1, 1.5)),
                                rightLine,
                                leftLine,
                                topLine,
                                bottomLine
                        )),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0.5, 1), new Point(1, 0.5)),
                                new MarchingLine(new Point(1, 1.5), new Point(1.5, 1)),
                                rightLine,
                                topLine,
                                bottomLine,
                                leftLine
                        ))
        );
    }


    static Stream<Arguments> detectLinesOnTwoRanksMultipleSquareArgs() {
        return Stream.of(
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1), createMarchingSquare(0)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(1, 0), new Point(1, 0.5)),
                                new MarchingLine(new Point(1, 0.5), new Point(1.5, 1)),
                                new MarchingLine(new Point(1.5, 1), new Point(2, 0.5)),
                                new MarchingLine(new Point(2, 0.5), new Point(2, 0)),

                                new MarchingLine(new Point(1, 3), new Point(1, 2.5)),
                                new MarchingLine(new Point(1, 2.5), new Point(1.5, 2)),
                                new MarchingLine(new Point(1.5, 2), new Point(2, 2.5)),
                                new MarchingLine(new Point(2, 2.5), new Point(2, 3))
                        )
                ),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1), createMarchingSquare(0)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0, 1), new Point(0.5, 1)),
                                new MarchingLine(new Point(0.5, 1), new Point(1.5, 1)),
                                new MarchingLine(new Point(1.5, 1), new Point(2, 0.5)),
                                new MarchingLine(new Point(2, 0.5), new Point(2, 0)),

                                new MarchingLine(new Point(0, 2), new Point(0.5, 2)),
                                new MarchingLine(new Point(0.5, 2), new Point(1.5, 2)),
                                new MarchingLine(new Point(1.5, 2), new Point(2, 2.5)),
                                new MarchingLine(new Point(2, 2.5), new Point(2, 3))
                        )
                ),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1), createMarchingSquare(1)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0, 1), new Point(0.5, 1)),
                                new MarchingLine(new Point(0.5, 1), new Point(1.5, 1)),
                                new MarchingLine(new Point(1.5, 1), new Point(2.5, 1)),
                                new MarchingLine(new Point(2.5, 1), new Point(3, 1)),

                                new MarchingLine(new Point(0, 2), new Point(0.5, 2)),
                                new MarchingLine(new Point(0.5, 2), new Point(1.5, 2)),
                                new MarchingLine(new Point(1.5, 2), new Point(2.5, 2)),
                                new MarchingLine(new Point(2.5, 2), new Point(3, 2))
                        )
                ),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0), createMarchingSquare(1)))
                        ),
                        Set.of(
                                new MarchingLine(new Point(0, 1), new Point(0.5, 1)),
                                new MarchingLine(new Point(0.5, 1), new Point(1, 0.5)),
                                new MarchingLine(new Point(1, 0.5), new Point(1, 0)),

                                new MarchingLine(new Point(2, 0), new Point(2, 0.5)),
                                new MarchingLine(new Point(2, 0.5), new Point(2.5, 1)),
                                new MarchingLine(new Point(2.5, 1), new Point(3, 1)),

                                new MarchingLine(new Point(0, 2), new Point(0.5, 2)),
                                new MarchingLine(new Point(0.5, 2), new Point(1, 2.5)),
                                new MarchingLine(new Point(1, 2.5), new Point(1, 3)),

                                new MarchingLine(new Point(2, 3), new Point(2, 2.5)),
                                new MarchingLine(new Point(2, 2.5), new Point(2.5, 2)),
                                new MarchingLine(new Point(2.5, 2), new Point(3, 2)),

                                new MarchingLine(new Point(1, 1.5), new Point(1.5, 1)),
                                new MarchingLine(new Point(1.5, 1), new Point(2, 1.5)),
                                new MarchingLine(new Point(2, 1.5), new Point(1.5, 2)),
                                new MarchingLine(new Point(1.5, 2), new Point(1, 1.5))
                        )
                )
        );
    }


    private static MarchingSquare createMarchingSquare(int rank) {
        return MarchingSquare.builder().rank(rank).build();
    }
}