package com.sladamos.marchingsquares;

import com.sladamos.util.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarchingLinesDetector {

    Set<MarchingLine> detectLines(List<MarchingRow> marchingRows) {
        Set<MarchingLine> marchingLines = new HashSet<MarchingLine>();
        int rows = marchingRows.size();
        int cols = marchingRows.getFirst().squares().size();
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols - 1; j++) {
                MarchingSquare a = marchingRows.get(i).getSquare(j);
                MarchingSquare b = marchingRows.get(i).getSquare(j + 1);
                MarchingSquare c = marchingRows.get(i + 1).getSquare(j);
                MarchingSquare d = marchingRows.get(i + 1).getSquare(j + 1);
                if (areAllRanksEqual(a, b, c, d)) {
                    continue;
                } else if (isARankDifferent(a, b, c, d)) {
                    marchingLines.add(new MarchingLine(new Point(j + 0.5, i + 1), new Point(j + 1, i + 0.5)));
                } else if (isBRankDifferent(a, b, c, d)) {
                    marchingLines.add(new MarchingLine(new Point(j + 1.5, i + 1), new Point(j + 1, i + 0.5)));
                } else if (isCRankDifferent(a, b, c, d)) {
                    marchingLines.add(new MarchingLine(new Point(j + 0.5, i + 1), new Point(j + 1, i + 1.5)));
                } else if (isDRankDifferent(a, b, c, d)) {
                    marchingLines.add(new MarchingLine(new Point(j + 1.5, i + 1), new Point(j + 1, i + 1.5)));
                }
            }
        }
        return marchingLines;
    }

    private boolean areAllRanksEqual(MarchingSquare a, MarchingSquare b, MarchingSquare c, MarchingSquare d) {
        return a.getRank() == b.getRank() && b.getRank() == c.getRank() && c.getRank() == d.getRank();
    }

    private boolean isARankDifferent(MarchingSquare a, MarchingSquare b, MarchingSquare c, MarchingSquare d) {
        return a.getRank() != b.getRank() && b.getRank() == c.getRank() && c.getRank() == d.getRank();
    }

    private boolean isBRankDifferent(MarchingSquare a, MarchingSquare b, MarchingSquare c, MarchingSquare d) {
        return a.getRank() != b.getRank() && b.getRank() != c.getRank() && c.getRank() == d.getRank();
    }

    private boolean isCRankDifferent(MarchingSquare a, MarchingSquare b, MarchingSquare c, MarchingSquare d) {
        return a.getRank() == b.getRank() && b.getRank() != c.getRank() && c.getRank() != d.getRank();
    }

    private boolean isDRankDifferent(MarchingSquare a, MarchingSquare b, MarchingSquare c, MarchingSquare d) {
        return a.getRank() == b.getRank() && b.getRank() == c.getRank() && c.getRank() != d.getRank();
    }
}
