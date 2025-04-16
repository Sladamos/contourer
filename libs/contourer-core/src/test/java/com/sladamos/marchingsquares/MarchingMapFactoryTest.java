package com.sladamos.marchingsquares;

import com.sladamos.data.ContourerData;
import com.sladamos.data.ContourerHeights;
import com.sladamos.data.ContourerRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MarchingMapFactoryTest {

    private final MarchingMapFactory uut = new MarchingMapFactory();

    @Test
    void shouldCreateMarchingMap() {
        int numberOfRanks = 2;
        ContourerData data = ContourerData.builder()
                .heights(createContourerHeights())
                .build();
        MarchingMap marchingMap = uut.createMap(data, numberOfRanks);

        assertAll("Should correctly create map",
                () -> assertThat(marchingMap.getRow(0).getSquare(0).getRank()).isEqualTo(0),
                () -> assertThat(marchingMap.getRow(0).getSquare(1).getRank()).isEqualTo(0),
                () -> assertThat(marchingMap.getRow(0).getSquare(2).getRank()).isEqualTo(1),
                () -> assertThat(marchingMap.getRow(1).getSquare(0).getRank()).isEqualTo(0),
                () -> assertThat(marchingMap.getRow(1).getSquare(1).getRank()).isEqualTo(1),
                () -> assertThat(marchingMap.getRow(1).getSquare(2).getRank()).isEqualTo(1)
        );

    }

    private ContourerHeights createContourerHeights() {
        return new ContourerHeights(List.of(
                new ContourerRow(List.of(BigDecimal.valueOf(100), BigDecimal.valueOf(125), BigDecimal.valueOf(150))),
                new ContourerRow(List.of(BigDecimal.valueOf(125), BigDecimal.valueOf(150), BigDecimal.valueOf(200)))
        ));
    }
}