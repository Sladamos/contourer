package com.sladamos.marchingsquares;

import java.util.List;
import java.util.Set;

public interface MarchingLinesDetector {
    Set<MarchingLine> detectLines(List<MarchingRow> marchingRows);
}
