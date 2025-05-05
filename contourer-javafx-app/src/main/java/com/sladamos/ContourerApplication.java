package com.sladamos;

import com.sladamos.data.ContourerDataFileLoader;
import com.sladamos.data.ContourerDataLoader;
import com.sladamos.file.FileInfo;
import com.sladamos.file.FileInfoProvider;
import com.sladamos.file.JavaFXFileInfoProvider;
import com.sladamos.marchingsquares.MarchingLinesDetector;
import com.sladamos.marchingsquares.MarchingLinesDetectorImpl;
import com.sladamos.marchingsquares.MarchingMapFactory;
import com.sladamos.rank.RankCalculator;
import com.sladamos.rank.RankCalculatorImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ContourerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ContourerApplication.class.getResource("contourer-view.fxml"));
        fxmlLoader.setController(createController(stage));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Marching squares contourer");
        stage.setScene(scene);
        stage.show();
    }

    private static ContourerController createController(Stage stage) {
        FileInfoProvider fileInfoProvider = new JavaFXFileInfoProvider(stage);
        RankCalculator rankCalculator = new RankCalculatorImpl();
        MarchingLinesDetector marchingLinesDetector = new MarchingLinesDetectorImpl();
        MarchingMapFactory marchingMapFactory = new MarchingMapFactory(rankCalculator, marchingLinesDetector);
        ContourerDataLoader<FileInfo> contourerDataLoader = new ContourerDataFileLoader();
        return new ContourerController(fileInfoProvider, marchingMapFactory, contourerDataLoader);
    }

    public static void main(String[] args) {
        launch();
    }
}