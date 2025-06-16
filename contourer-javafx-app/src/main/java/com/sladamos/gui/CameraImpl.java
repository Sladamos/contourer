package com.sladamos.gui;

import com.sladamos.util.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CameraImpl implements Camera {

    private final CameraData cameraData;
    private double viewportSize;
    private Point offset;
    private double squareSize;

    @Override
    public void zoomIn() {
        if (viewportSize > 1) {
            updateZoom(viewportSize / 1.5);
        }
    }

    @Override
    public void zoomOut() {
        updateZoom(viewportSize * 1.5);
    }

    @Override
    public void move(Point distance) {
        offset = offset.add(distance);
    }

    @Override
    public void reset() {
        viewportSize = Math.max(cameraData.cols(), cameraData.rows()) * 2;
        squareSize = Math.min(cameraData.screenWidth(), cameraData.screenHeight()) / viewportSize;
        double mapWidth = cameraData.cols() * squareSize;
        double mapHeight = cameraData.rows() * squareSize;
        offset = new Point((cameraData.screenWidth() - mapWidth) / 2, (cameraData.screenHeight() - mapHeight) / 2);
    }

    private void updateZoom(double newViewportSize) {
        double centerX = cameraData.screenWidth() / 2;
        double centerY = cameraData.screenHeight() / 2;
        double oldSquare = squareSize;

        viewportSize = newViewportSize;
        squareSize = Math.min(cameraData.screenWidth(), cameraData.screenHeight()) / viewportSize;

        double offsetX = centerX - ((centerX - offset.x()) / oldSquare) * squareSize;
        double offsetY = centerY - ((centerY - offset.y()) / oldSquare) * squareSize;
        offset = new Point(offsetX, offsetY);
    }
}
