package com.sladamos;

import com.sladamos.data.ContourerData;
import com.sladamos.data.ContourerDataLoader;
import com.sladamos.file.FileInfo;
import com.sladamos.file.FileInfoProvider;
import com.sladamos.marchingsquares.MarchingMap;
import com.sladamos.marchingsquares.MarchingMapFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContourerController {

    @FXML
    private TextField numberOfRanks;

    @FXML
    private Pane drawingPane;

    private final FileInfoProvider fileInfoProvider;

    private final MarchingMapFactory marchingMapFactory;

    private final ContourerDataLoader<FileInfo> contourerDataLoader;

    private ContourerData contourerData;

    private MarchingMap map;

    private int currentViewportSize = 1000;

    private double offsetX = 0;
    private double offsetY = 0;

    private double dragStartX = 0;
    private double dragStartY = 0;

    @FXML
    public void initialize() {
        drawingPane.widthProperty().addListener((obs, oldVal, newVal) -> drawMap());
        drawingPane.heightProperty().addListener((obs, oldVal, newVal) -> drawMap());

        drawingPane.setOnMousePressed(event -> {
            dragStartX = event.getX();
            dragStartY = event.getY();
        });

        drawingPane.setOnMouseDragged(event -> {
            double dx = event.getX() - dragStartX;
            double dy = event.getY() - dragStartY;
            offsetX += dx;
            offsetY += dy;
            dragStartX = event.getX();
            dragStartY = event.getY();
            drawMap();
        });
    }

    public void onLoadFileClicked(ActionEvent actionEvent) {
        FileInfo fileInfo = fileInfoProvider.getFileInfo();
        if (fileInfo != null) {
            contourerData = contourerDataLoader.loadData(fileInfo);
        }
    }

    public void onExecuteClicked(ActionEvent actionEvent) {
        if (isValidNumberOfRanks() && contourerData != null) {
            int ranks = Integer.parseInt(numberOfRanks.getText());
            this.map = marchingMapFactory.createMap(contourerData, ranks);


            this.currentViewportSize = 1000;
            this.offsetX = 0;
            this.offsetY = 0;
            drawMap();
        }
    }

    public void onZoomIn() {
        if (currentViewportSize > 1) {
            currentViewportSize /= 2;
            drawMap();
        }
    }

    public void onZoomOut() {
        currentViewportSize *= 2;
        drawMap();
    }

    private boolean isValidNumberOfRanks() {
        String text = numberOfRanks.getText();
        try {
            int value = Integer.parseInt(text);
            if (value > 0) {
                return true;
            } else {
                showAlert("Number must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number.");
        }
        return false;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void drawMap() {
        if (map == null) return;
        drawingPane.getChildren().clear();
        double paneWidth = drawingPane.getWidth();
        double paneHeight = drawingPane.getHeight();

        double squareWidth = paneWidth / currentViewportSize;
        double squareHeight = paneHeight / currentViewportSize;
        double squareSize = Math.min(squareWidth, squareHeight);
        double maxY = currentViewportSize * squareSize;
        double maxX = currentViewportSize * squareSize;

        for (var line : map.lines()) {
            double x1 = line.first().x() * squareSize + offsetX;
            double y1 = line.first().y() * squareSize + offsetY;
            double x2 = line.second().x() * squareSize + offsetX;
            double y2 = line.second().y() * squareSize + offsetY;

            if (x1 <= maxX && y1 <= maxY && x2 <= maxX && y2 <= maxY) {

                javafx.scene.shape.Line fxLine = new javafx.scene.shape.Line(x1, y1, x2, y2);
                fxLine.setStrokeWidth(2.0);
                fxLine.setStyle("-fx-stroke: red;");
                drawingPane.getChildren().add(fxLine);
            }
        }
    }
}