package com.sladamos.data;

import com.sladamos.file.FileExtension;
import com.sladamos.file.FileInfo;
import com.sladamos.file.FileProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContourerDataFileLoaderTest {

    @Mock
    private FileProvider fileProvider;

    @InjectMocks
    private ContourerDataFileLoader uut;

    @Test
    void shouldThrowContourerLoaderExceptionWhenFileExtensionIsNotTiff() {
        FileInfo fileInfo = FileInfo.builder().fileExtension(FileExtension.EMPTY).build();
        when(fileProvider.getFileInfo()).thenReturn(fileInfo);

        assertThatThrownBy(uut::loadData).isInstanceOf(ContourerLoaderException.class);
    }

    @Test
    void shouldThrowContourerLoaderExceptionWhenFileReturnedByFileProviderDoesNotExist() {
        FileInfo fileInfo = FileInfo.builder()
                .fileExtension(FileExtension.ASC)
                .fileName("testName")
                .absolutePath("testPath")
                .build();
        when(fileProvider.getFileInfo()).thenReturn(fileInfo);

        assertThatThrownBy(uut::loadData).isInstanceOf(ContourerLoaderException.class);
    }

    @Test
    void shouldParseDataProperlyFromExistingFile() {
        String fileName = "data_karkonoski.asc";
        String path = Path.of("src/test/resources/" + fileName).toAbsolutePath().toString();
        FileInfo fileInfo = FileInfo.builder()
                .absolutePath(path)
                .fileName(fileName)
                .fileExtension(FileExtension.ASC).build();
        when(fileProvider.getFileInfo()).thenReturn(fileInfo);

        ContourerData contourerData = uut.loadData();

        assertAll("Should correctly parse data",
                () -> assertThat(contourerData.getNumberOfColumns()).isEqualTo(2209),
                () -> assertThat(contourerData.getNumberOfRows()).isEqualTo(2064),
                () -> assertThat(contourerData.getXllCorner()).isEqualTo("253719.317869942635"),
                () -> assertThat(contourerData.getYllCorner()).isEqualTo("331136.677727281640"),
                () -> assertThat(contourerData.getDx()).isEqualTo("0.500062330590"),
                () -> assertThat(contourerData.getDy()).isEqualTo("0.499940408994"),
                () -> assertThat(contourerData.getHeights().getRow(0).getHeight(0)).isEqualTo("1031.969970703125"),
                () -> assertThat(contourerData.getHeights().getRow(0).getHeight(2208)).isEqualTo("936.15997314453125"),
                () -> assertThat(contourerData.getHeights().getRow(2063).getHeight(0)).isEqualTo("1205.9300537109375"),
                () -> assertThat(contourerData.getHeights().getRow(2063).getHeight(2208)).isEqualTo("1171")
        );
    }

}