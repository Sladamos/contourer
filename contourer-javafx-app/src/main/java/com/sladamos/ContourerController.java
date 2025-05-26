package com.sladamos;

import com.sladamos.data.ContourerData;
import com.sladamos.data.ContourerDataLoader;
import com.sladamos.file.FileInfo;
import com.sladamos.file.FileInfoProvider;
import com.sladamos.gui.Camera;
import com.sladamos.gui.CameraData;
import com.sladamos.marchingsquares.MarchingMap;
import com.sladamos.marchingsquares.MarchingMapFactory;
import com.sladamos.util.Point;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.function.Function;

@RequiredArgsConstructor
public class ContourerController {

    @FXML
    private TextField numberOfRanks;

    @FXML
    private Pane drawingPane;

    private ImageView backgroundImageView = new ImageView();

    private final FileInfoProvider fileInfoProvider;

    private final MarchingMapFactory marchingMapFactory;

    private final ContourerDataLoader<FileInfo> contourerDataLoader;

    private final Function<CameraData, Camera> cameraFactory;

    private ContourerData contourerData;
    private MarchingMap map;
    private double dragStartX = 0;
    private double dragStartY = 0;
    private Camera camera;

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
            updateMapIfNecessary(dx, dy);
            dragStartX = event.getX();
            dragStartY = event.getY();
        });
    }

    private void updateMapIfNecessary(double dx, double dy) {
        if (camera != null) {
            camera.move(new Point(dx, dy));
            drawMap();
        }
    }

    public void onLoadFileClicked(ActionEvent actionEvent) {
        try {
            FileInfo fileInfo = fileInfoProvider.getFileInfo();
            if (fileInfo != null) {
                contourerData = contourerDataLoader.loadData(fileInfo);
                CameraData cameraData = new CameraData(drawingPane.getWidth(), drawingPane.getHeight(), contourerData.getNumberOfRows(), contourerData.getNumberOfColumns());
                camera = cameraFactory.apply(cameraData);
                camera.reset();
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            showAlert("Error loading contourer data");
        }
    }

    public void onLoadImageClicked(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Background Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File selectedFile = fileChooser.showOpenDialog(drawingPane.getScene().getWindow());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                backgroundImageView.setImage(image);
                backgroundImageView.setPreserveRatio(true);
                backgroundImageView.setSmooth(true);
                if (!drawingPane.getChildren().contains(backgroundImageView)) {
                    drawingPane.getChildren().addFirst(backgroundImageView);
                }
                updateBackgroundTransform();
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            showAlert("Error loading contourer data");
        }
    }

    public void onExecuteClicked(ActionEvent actionEvent) {
        if (isValidNumberOfRanks() && contourerData != null) {
            int ranks = Integer.parseInt(numberOfRanks.getText());
            this.map = marchingMapFactory.createMap(contourerData, ranks);
            drawMap();
        }
    }

    public void onZoomIn() {
        camera.zoomIn();
        drawMap();
    }

    public void onZoomOut() {
        camera.zoomOut();
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
        drawingPane.getChildren().addFirst(backgroundImageView);
        updateBackgroundTransform();
        double paneWidth = drawingPane.getWidth();
        double paneHeight = drawingPane.getHeight();

        double squareSize = camera.getSquareSize();
        double offsetX = camera.getOffset().x();
        double offsetY = camera.getOffset().y();

        for (var line : map.lines()) {
            double x1 = line.first().x() * squareSize + offsetX;
            double y1 = line.first().y() * squareSize + offsetY;
            double x2 = line.second().x() * squareSize + offsetX;
            double y2 = line.second().y() * squareSize + offsetY;

            if (x1 <= paneWidth && y1 <= paneHeight && x2 <= paneWidth && y2 <= paneHeight) {
                drawLine(x1, y1, x2, y2);
            }
        }
    }

    private void drawLine(double x1, double y1, double x2, double y2) {
        javafx.scene.shape.Line fxLine = new javafx.scene.shape.Line(x1, y1, x2, y2);
        fxLine.setStrokeWidth(2.0);
        fxLine.setStyle("-fx-stroke: red;");
        drawingPane.getChildren().add(fxLine);
    }

    private void updateBackgroundTransform() {
        if (contourerData != null && camera != null && backgroundImageView.getImage() != null) {
            double squareSize = camera.getSquareSize();
            double offsetX = camera.getOffset().x();
            double offsetY = camera.getOffset().y();

            backgroundImageView.setFitWidth(contourerData.getNumberOfColumns() * squareSize);
            backgroundImageView.setFitHeight(contourerData.getNumberOfRows() * squareSize);
            backgroundImageView.setTranslateX(offsetX);
            backgroundImageView.setTranslateY(offsetY);
        }
    }
}