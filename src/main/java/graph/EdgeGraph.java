package graph;

import drawing.DrawingApi;

import java.util.ArrayList;
import java.util.Scanner;

public class EdgeGraph extends Graph {

    public EdgeGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph() {
        Scanner in = new Scanner(System.in);

        int v = in.nextInt();
        for (int i = 0; i < v; i++) {
            graph.add(new ArrayList<>());
        }

        int e = in.nextInt();
        for (int i = 0; i < e; i++) {
            int f = in.nextInt(), t = in.nextInt();
            graph.get(--f).add(--t);
            graph.get(t).add(f);
        }
    }
}
