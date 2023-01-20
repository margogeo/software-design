package drawing;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class AwtDrawingApi extends Frame implements DrawingApi {

    private final int width = 500;
    private final int height = 500;
    private static final ArrayList<Integer> circleX = new ArrayList<>();
    private static final ArrayList<Integer> circleY = new ArrayList<>();
    private static final ArrayList<Integer> circleD = new ArrayList<>();
    private static final ArrayList<Integer> lineX = new ArrayList<>();
    private static final ArrayList<Integer> lineY = new ArrayList<>();
    private static final ArrayList<Integer> lineX1 = new ArrayList<>();
    private static final ArrayList<Integer> lineY1 = new ArrayList<>();

    public AwtDrawingApi() {
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
    public void drawCircle(double x, double y, double d) {
        circleX.add((int) (x - d / 2));
        circleY.add((int) (y - d / 2));
        circleD.add((int) d);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        lineX.add((int) x1);
        lineY.add((int) y1);
        lineX1.add((int) x2);
        lineY1.add((int) y2);
    }

    @Override
    public void showResult() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(width, height);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < lineX.size(); i++) {
            g.drawLine(lineX.get(i), lineY.get(i), lineX1.get(i), lineY1.get(i));
        }
        for (int i = 0; i < circleX.size(); i++) {
            g.fillOval(circleX.get(i), circleY.get(i), circleD.get(i), circleD.get(i));
        }
    }
}
