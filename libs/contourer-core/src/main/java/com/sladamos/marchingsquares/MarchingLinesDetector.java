package com.sladamos.marchingsquares;

import com.sladamos.util.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarchingLinesDetector {

    Set<MarchingLine> detectLines(List<MarchingRow> marchingRows) {
        Set<MarchingLine> marchingLines = new HashSet<>();
        int rows = marchingRows.size();
        int cols = marchingRows.getFirst().squares().size();
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols - 1; j++) {
                MarchingSquare leftUpper = marchingRows.get(i).getSquare(j);
                MarchingSquare rightUpper = marchingRows.get(i).getSquare(j + 1);
                MarchingSquare leftLower = marchingRows.get(i + 1).getSquare(j);
                MarchingSquare rightLower = marchingRows.get(i + 1).getSquare(j + 1);
                if (isLeftUpperRankDifferent(leftUpper, rightUpper, leftLower, rightLower)) {
                    addTopLineIfNecessary(i, marchingLines, j);
                    addLeftLineIfNecessary(j, marchingLines, i);
                    marchingLines.add(new MarchingLine(new Point(j + 0.5, i + 1), new Point(j + 1, i + 0.5)));
                } else if (isRightUpperRankDifferent(leftUpper, rightUpper, leftLower, rightLower)) {
                    addTopLineIfNecessary(i, marchingLines, j);
                    addRightLineIfNecessary(j, marchingLines, i, cols);
                    marchingLines.add(new MarchingLine(new Point(j + 1.5, i + 1), new Point(j + 1, i + 0.5)));
                } else if (isLeftLowerRankDifferent(leftUpper, rightUpper, leftLower, rightLower)) {
                    addBottomLineIfNecessary(i, marchingLines, j, rows);
                    addLeftLineIfNecessary(j, marchingLines, i);
                    marchingLines.add(new MarchingLine(new Point(j + 0.5, i + 1), new Point(j + 1, i + 1.5)));
                } else if (isRightLowerRankDifferent(leftUpper, rightUpper, leftLower, rightLower)) {
                    addBottomLineIfNecessary(i, marchingLines, j, rows);
                    addRightLineIfNecessary(j, marchingLines, i, cols);
                    marchingLines.add(new MarchingLine(new Point(j + 1.5, i + 1), new Point(j + 1, i + 1.5)));
                } else if (isHorizontalLine(leftUpper, rightUpper, leftLower, rightLower)) {
                    addLeftLineIfNecessary(j, marchingLines, i);
                    addRightLineIfNecessary(j, marchingLines, i, cols);
                    marchingLines.add(new MarchingLine(new Point(j + 0.5, i + 1), new Point(j + 1.5, i + 1)));
                } else if (isVerticalLine(leftUpper, rightUpper, leftLower, rightLower)) {
                    addTopLineIfNecessary(i, marchingLines, j);
                    addBottomLineIfNecessary(i, marchingLines, j, rows);
                    marchingLines.add(new MarchingLine(new Point(j + 1, i + 0.5), new Point(j + 1, i + 1.5)));
                }
            }
        }
        return marchingLines;
    }

    private static void addBottomLineIfNecessary(int i, Set<MarchingLine> marchingLines, int j, int rows) {
        if (i == rows - 2) {
            marchingLines.add(new MarchingLine(new Point(j + 1, rows - 0.5), new Point(j + 1, rows)));
        }
    }

    private static void addTopLineIfNecessary(int i, Set<MarchingLine> marchingLines, int j) {
        if (i == 0) {
            marchingLines.add(new MarchingLine(new Point(j + 1, 0), new Point(j + 1, 0.5)));
        }
    }

    private static void addRightLineIfNecessary(int j, Set<MarchingLine> marchingLines, int i, int cols) {
        if (j == cols - 2) {
            marchingLines.add(new MarchingLine(new Point(cols - 0.5, i + 1), new Point(cols, i + 1)));
        }
    }

    private static void addLeftLineIfNecessary(int j, Set<MarchingLine> marchingLines, int i) {
        if (j == 0) {
            marchingLines.add(new MarchingLine(new Point(0, i + 1), new Point(0.5, i + 1)));
        }
    }

    private boolean isVerticalLine(MarchingSquare leftUpper, MarchingSquare rightUpper, MarchingSquare leftLower, MarchingSquare rightLower) {
        return leftUpper.getRank() == leftLower.getRank()
                && rightUpper.getRank() != leftLower.getRank()
                && rightUpper.getRank() == rightLower.getRank();
    }

    private boolean isHorizontalLine(MarchingSquare leftUpper, MarchingSquare rightUpper, MarchingSquare leftLower, MarchingSquare rightLower) {
        return leftUpper.getRank() == rightUpper.getRank()
                && rightUpper.getRank() != leftLower.getRank()
                && leftLower.getRank() == rightLower.getRank();
    }

    private boolean isLeftUpperRankDifferent(MarchingSquare leftUpper, MarchingSquare rightUpper, MarchingSquare leftLower, MarchingSquare rightLower) {
        return leftUpper.getRank() != rightUpper.getRank()
                && rightUpper.getRank() == leftLower.getRank()
                && leftLower.getRank() == rightLower.getRank();
    }

    private boolean isRightUpperRankDifferent(MarchingSquare leftUpper, MarchingSquare rightUpper, MarchingSquare leftLower, MarchingSquare rightLower) {
        return leftUpper.getRank() != rightUpper.getRank()
                && rightUpper.getRank() != leftLower.getRank()
                && leftLower.getRank() == rightLower.getRank();
    }

    private boolean isLeftLowerRankDifferent(MarchingSquare leftUpper, MarchingSquare rightUpper, MarchingSquare leftLower, MarchingSquare rightLower) {
        return leftUpper.getRank() == rightUpper.getRank()
                && rightUpper.getRank() != leftLower.getRank()
                && leftLower.getRank() != rightLower.getRank();
    }

    private boolean isRightLowerRankDifferent(MarchingSquare leftUpper, MarchingSquare rightUpper, MarchingSquare leftLower, MarchingSquare rightLower) {
        return leftUpper.getRank() == rightUpper.getRank()
                && rightUpper.getRank() == leftLower.getRank()
                && leftLower.getRank() != rightLower.getRank();
    }
}
