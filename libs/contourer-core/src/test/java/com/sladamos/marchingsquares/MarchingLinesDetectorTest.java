package com.sladamos.marchingsquares;

import com.sladamos.util.Point;
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
    @MethodSource("detectLinesOnSingleSquareArgs")
    void shouldDetectLinesOnSingleSquare(List<MarchingRow> marchingRows, Set<MarchingLine> expectedLines) {

        Set<MarchingLine> lines = uut.detectLines(marchingRows);

        assertThat(lines)
                .withFailMessage("Lines detected does not match expected lines.")
                .isEqualTo(expectedLines);
    }

    public static Stream<Arguments> detectLinesOnSingleSquareArgs() {
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
                        Set.of(new MarchingLine(new Point(0.5, 1), new Point(1, 0.5)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0)))
                        ),
                        Set.of(new MarchingLine(new Point(1.5, 1), new Point(1, 0.5)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0)))
                        ),
                        Set.of(new MarchingLine(new Point(0.5, 1), new Point(1, 1.5)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1)))
                        ),
                        Set.of(new MarchingLine(new Point(1.5, 1), new Point(1, 1.5)))),

                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1)))
                        ),
                        Set.of(new MarchingLine(new Point(0.5, 1), new Point(1, 0.5)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1)))
                        ),
                        Set.of(new MarchingLine(new Point(1.5, 1), new Point(1, 0.5)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1)))
                        ),
                        Set.of(new MarchingLine(new Point(0.5, 1), new Point(1, 1.5)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0)))
                        ),
                        Set.of(new MarchingLine(new Point(1.5, 1), new Point(1, 1.5)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0)))
                        ),
                        Set.of(new MarchingLine(new Point(0, 1), new Point(2, 1)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(0)))
                        ),
                        Set.of(new MarchingLine(new Point(1, 0), new Point(1, 2)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1))),
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(1)))
                        ),
                        Set.of(new MarchingLine(new Point(1, 0), new Point(1, 2)))),
                Arguments.of(List.of(
                                new MarchingRow(List.of(createMarchingSquare(0), createMarchingSquare(0))),
                                new MarchingRow(List.of(createMarchingSquare(1), createMarchingSquare(1)))
                        ),
                        Set.of(new MarchingLine(new Point(0, 1), new Point(2, 1))))
        );
    }

    private static MarchingSquare createMarchingSquare(int rank) {
        return MarchingSquare.builder().rank(rank).build();
    }
    /*

    void shouldAssignIndexesProperly() {


    void shouldDetectLinesOnSquaresWithMultipleRows() { TODO: dodać dla tego 1 square że uzupełnia przy krawędziach
    0 1 0
    0 0 0

    1 1 0
    0 0 0

    1 1 1
    0 0 0

    }*/


}