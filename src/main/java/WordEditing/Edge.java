package WordEditing;

import javafx.scene.shape.Line;

public class Edge {
    private Line line;
    DicNode nodeA;
    DicNode nodeB;

    public Edge() {
        line = new Line();
    }

    public Edge(DicNode nodeA, DicNode nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        line = new Line();
        line.setStartX(nodeA.getNodePane().getLayoutX() + nodeA.getNodePane().getWidth()/2);
        line.setStartY(nodeA.getNodePane().getLayoutY() + nodeA.getNodePane().getHeight()/2);
        line.setEndX(nodeB.getNodePane().getLayoutX() + nodeB.getNodePane().getWidth()/2);
        line.setEndY(nodeB.getNodePane().getLayoutY() + nodeB.getNodePane().getHeight()/2);
    }

    public void updateEdge() {
        line.setStartX(nodeA.getNodePane().getLayoutX() + nodeA.getNodePane().getWidth()/2);
        line.setStartY(nodeA.getNodePane().getLayoutY() + nodeA.getNodePane().getHeight()/2);
        line.setEndX(nodeB.getNodePane().getLayoutX() + nodeB.getNodePane().getWidth()/2);
        line.setEndY(nodeB.getNodePane().getLayoutY() + nodeB.getNodePane().getHeight()/2);
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public void setNodeA(DicNode nodeA) {
        this.nodeA = nodeA;
    }

    public void setNodeB(DicNode nodeB) {
        this.nodeB = nodeB;
    }

    public DicNode getNodeA() {
        return nodeA;
    }

    public DicNode getNodeB() {
        return nodeB;
    }
}
