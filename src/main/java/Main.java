import drawing.AwtDrawingApi;
import drawing.DrawingApi;
import drawing.JavaFxDrawingApi;
import graph.MatrixGraph;
import graph.EdgeGraph;
import graph.Graph;

public class Main {

    public static void main(String[] args) {
      if (args.length != 2) {
        throw new AssertionError("wrong number of args");
      }
      String api = args[0];
      String implementation = args[1];

      DrawingApi drawingApi = null;
      if (api.equals("AWT")) {
         drawingApi = new AwtDrawingApi();
      } else if (api.equals("JavaFX")) {
         drawingApi = new JavaFxDrawingApi();
      }
      else {
         System.err.println("Invalid drawing API name");
      }
      Graph graph;
      if (implementation.equals("Matrix")) {
          graph = new MatrixGraph(drawingApi);
      } else if (implementation.equals("EdgeList")) {
          graph = new EdgeGraph(drawingApi);
      } else {
          System.err.println("Invalid graph format name");
          throw new AssertionError();
      }
      graph.readGraph();
      graph.renderGraph();
    }
}
