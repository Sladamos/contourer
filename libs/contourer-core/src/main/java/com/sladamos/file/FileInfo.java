package com.sladamos.file;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FileInfo {
    FileExtension fileExtension;
    String fileName;
    String absolutePath;
}
