package drawing;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javafx.scene.shape.Shape;

import java.util.*;

public class JavaFxDrawingApi extends Application implements DrawingApi {

    private final int width = 500;
    private final int height = 500;
    private static final List<Shape> shapes = new ArrayList<>();

    public JavaFxDrawingApi() {
    }

    @Override
    public int getDrawingAreaWidth() {
        return width;
    }

    @Override
    public int getDrawingAreaHeight() {
        return height;
    }

    @Override
    public void showResult() {
        launch();
    }

    @Override
    public void drawCircle(double x, double y, double d) {
        shapes.add(new Circle(x, y, d));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        shapes.add(new Line(x1, y1, x2, y2));
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        shapes.forEach(e -> root.getChildren().add(e));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
