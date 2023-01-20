package graph;

import drawing.DrawingApi;

import java.util.ArrayList;
import java.util.List;

public abstract class Graph {

    private final DrawingApi drawingApi;
    protected final ArrayList<ArrayList<Integer>> graph;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
        this.graph = new ArrayList<>();
    }

    public void renderGraph() {
        int v = graph.size();
        Coords center = new Coords(drawingApi.getDrawingAreaWidth() * 0.5, drawingApi.getDrawingAreaHeight() * 0.5);
        Coords[] coords = new Coords[v];
        double rad = Math.min(center.x, center.y) * 0.75;

        for (int i = 0; i < v; i++) {
            double alpha = 2 * Math.PI * i / v;
            coords[i] = new Coords(center.x + rad * Math.cos(alpha),center.y + rad * Math.sin(alpha));
            drawingApi.drawCircle(coords[i].x, coords[i].y, 10);
            for (int j : graph.get(i)) {
                if (j < i)
                    drawingApi.drawLine(coords[i].x, coords[i].y, coords[j].x, coords[j].y);
            }
        }
        drawingApi.showResult();
    }

    public abstract void readGraph();

    static class Coords {
        double x, y;

        Coords(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
