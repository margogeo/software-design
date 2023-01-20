package drawing;

public interface DrawingApi {

    int getDrawingAreaWidth();

    int getDrawingAreaHeight();

    void drawCircle(double x, double y, double d);

    void drawLine(double x1, double y1, double x2, double y2);

    void showResult();
}
