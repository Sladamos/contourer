package com.sladamos.data;

import com.sladamos.file.FileExtension;
import com.sladamos.file.FileInfo;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ContourerDataFileLoaderTest {

    private final ContourerDataFileLoader uut = new ContourerDataFileLoader();

    @Test
    void shouldThrowContourerLoaderExceptionWhenFileExtensionIsNotTiff() {
        FileInfo fileInfo = FileInfo.builder().fileExtension(FileExtension.UNKNOWN).build();

        assertThatThrownBy(() -> uut.loadData(fileInfo)).isInstanceOf(ContourerLoaderException.class);
    }

    @Test
    void shouldThrowContourerLoaderExceptionWhenFileReturnedByFileProviderDoesNotExist() {
        FileInfo fileInfo = FileInfo.builder()
                .fileExtension(FileExtension.ASC)
                .fileName("testName")
                .absolutePath("testPath")
                .build();

        assertThatThrownBy(() -> uut.loadData(fileInfo)).isInstanceOf(ContourerLoaderException.class);
    }

    @Test
    void shouldParseDataProperlyFromExistingFile() {
        String fileName = "data_karkonoski.asc";
        String path = Path.of("src/test/resources/" + fileName).toAbsolutePath().toString();
        FileInfo fileInfo = FileInfo.builder()
                .absolutePath(path)
                .fileName(fileName)
                .fileExtension(FileExtension.ASC).build();

        ContourerData contourerData = uut.loadData(fileInfo);

        assertAll("Should correctly parse data",
                () -> assertThat(contourerData.getNumberOfColumns()).isEqualTo(2),
                () -> assertThat(contourerData.getNumberOfRows()).isEqualTo(6),
                () -> assertThat(contourerData.getXllCorner()).isEqualTo("253719.317869942635"),
                () -> assertThat(contourerData.getYllCorner()).isEqualTo("331136.677727281640"),
                () -> assertThat(contourerData.getDx()).isEqualTo("0.500062330590"),
                () -> assertThat(contourerData.getDy()).isEqualTo("0.499940408994"),
                () -> assertThat(contourerData.getMinValue()).isEqualTo("1"),
                () -> assertThat(contourerData.getMaxValue()).isEqualTo("6.6"),
                () -> assertThat(contourerData.getHeights().getRow(0).getHeight(0)).isEqualTo("1"),
                () -> assertThat(contourerData.getHeights().getRow(0).getHeight(1)).isEqualTo("1.1"),
                () -> assertThat(contourerData.getHeights().getRow(5).getHeight(0)).isEqualTo("6"),
                () -> assertThat(contourerData.getHeights().getRow(5).getHeight(1)).isEqualTo("6.6")
        );
    }

}