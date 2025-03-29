package com.sladamos.data;

import com.sladamos.file.FileExtension;
import com.sladamos.file.FileInfo;
import com.sladamos.file.FileProvider;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class ContourerDataFileLoader implements ContourerDataLoader {

    private final FileProvider fileProvider;

    @Override
    public ContourerData loadData() {
        FileInfo fileInfo = fileProvider.getFileInfo();
        if (!fileInfo.getFileExtension().equals(FileExtension.ASC)) {
            throw new ContourerLoaderException();
        }

        return getDataFromFileInfo(fileInfo);
    }

    private ContourerData getDataFromFileInfo(FileInfo fileInfo) {
        try (Scanner scanner = new Scanner(new File(fileInfo.getAbsolutePath()))) {
            int numberOfColumns = parseIntLine(scanner);
            int numberOfRows = parseIntLine(scanner);
            BigDecimal xllCorner = parseBigDecimalLine(scanner);
            BigDecimal yllCorner = parseBigDecimalLine(scanner);
            BigDecimal dx = parseBigDecimalLine(scanner);
            BigDecimal dy = parseBigDecimalLine(scanner);
            ContourerHeights heights = parseContourerHeights(scanner);
            return ContourerData.builder()
                    .numberOfColumns(numberOfColumns)
                    .numberOfRows(numberOfRows)
                    .xllCorner(xllCorner)
                    .yllCorner(yllCorner)
                    .dx(dx)
                    .dy(dy)
                    .heights(heights)
                    .build();
        } catch (FileNotFoundException e) {
            throw new ContourerLoaderException();
        }
    }

    private ContourerHeights parseContourerHeights(Scanner scanner) {
        scanner.useDelimiter("\\n");
        List<ContourerRow> heights = scanner.tokens()
                .map(this::parseContourerRow)
                .toList();
        return new ContourerHeights(heights);
    }

    private ContourerRow parseContourerRow(String line) {
        String[] lineArr = tokenizeLine(line);
        List<BigDecimal> heights = Arrays.stream(lineArr).map(BigDecimal::new).toList();
        return new ContourerRow(heights);
    }

    private int parseIntLine(Scanner scanner) {
        var tokenizedLine = tokenizeLine(scanner.nextLine());
        return Integer.parseInt(tokenizedLine[1]);
    }

    private BigDecimal parseBigDecimalLine(Scanner scanner) {
        var tokenizedLine = tokenizeLine(scanner.nextLine());
        return new BigDecimal(tokenizedLine[1]);
    }

    private String[] tokenizeLine(String line) {
        return line.trim().split("\\s+");
    }
}
