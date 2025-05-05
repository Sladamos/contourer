package com.sladamos.file;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class JavaFXFileInfoProvider implements FileInfoProvider {

    private final Stage stage;

    @Override
    public FileInfo getFileInfo() {
        FileExtension fileExtension = FileExtension.ASC;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file with data");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(fileExtension.strValue(), String.format("*.%s", fileExtension.strValue()))
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return new FileInfo(
                    fileExtension,
                    file.getName(),
                    file.getAbsolutePath());
        }
        return null;
    }
}
