package graph;

import drawing.DrawingApi;

import java.util.ArrayList;
import java.util.Scanner;

public class MatrixGraph extends Graph {

    public MatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph() {
        Scanner in = new Scanner(System.in);

        int v = in.nextInt();
        for (int i = 0; i < v; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (in.nextInt() == 1) {
                    graph.get(i).add(j);
                }
            }
        }
    }
}
