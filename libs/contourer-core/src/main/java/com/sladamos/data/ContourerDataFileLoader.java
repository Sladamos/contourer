package com.sladamos.data;

import com.sladamos.file.FileExtension;
import com.sladamos.file.FileInfo;
import com.sladamos.file.FileProvider;
import lombok.RequiredArgsConstructor;

import java.io.File;

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
        File file = new File(fileInfo.getAbsolutePath());
        if (!file.exists()) {
            throw new ContourerLoaderException();
        }
        return new ContourerData();
    }
}
