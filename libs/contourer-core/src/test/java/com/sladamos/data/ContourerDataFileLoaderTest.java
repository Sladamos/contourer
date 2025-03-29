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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        String fileName = "data_test.asc";
        String path = Path.of("src/test/resources/" + fileName).toAbsolutePath().toString();
        FileInfo fileInfo = FileInfo.builder()
                .absolutePath(path)
                .fileName(fileName)
                .fileExtension(FileExtension.ASC).build();
        when(fileProvider.getFileInfo()).thenReturn(fileInfo);

        ContourerData contourerData = uut.loadData();

        assertThat(contourerData).isNotNull();
    }

}