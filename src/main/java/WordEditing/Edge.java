package WordEditing;

import javafx.scene.shape.Line;

public class Edge {
    private Line line;
    WordSceneNode nodeA;
    WordSceneNode nodeB;

    public Edge() {
        line = new Line();
    }

    public Edge(WordSceneNode nodeA, WordSceneNode nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        line = new Line();
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public void setNodeA(WordSceneNode nodeA) {
        this.nodeA = nodeA;
    }

    public void setNodeB(WordSceneNode nodeB) {
        this.nodeB = nodeB;
    }

    public WordSceneNode getNodeA() {
        return nodeA;
    }

    public WordSceneNode getNodeB() {
        return nodeB;
    }
}
