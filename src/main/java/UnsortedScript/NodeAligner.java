package UnsortedScript;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Pair;

public final class NodeAligner
{

    public static void AlignCenter(Parent root, Node node)
    {
        AlignCenterWidth(root, node);
        AlignCenterHeight(root, node);
    }

    public static void AlignCenterWidth(Parent root, Node node)
    {
        double rootWidth = GetWidthOfNode(root);
        double nodeWidth = GetWidthOfNode(node);

        double newX = (rootWidth - nodeWidth) / 2;
        node.setLayoutX(newX);

    }

    public static void AlignCenterHeight(Parent root, Node node)
    {
        double rootHeight = GetHeightOfNode(root);
        double nodeHeight = GetHeightOfNode(node);

        double newY = (rootHeight - nodeHeight) / 2;
        node.setLayoutX(newY);

    }

    public static Pair<Double, Double> GetSizeOfNode(Node node)
    {
        double width = node.getBoundsInLocal().getWidth();
        double height = node.getBoundsInLocal().getHeight();
        return new Pair<>(width, height);
    }

    public static double GetWidthOfNode(Node node)
    {
        return node.getBoundsInLocal().getWidth();
    }

    public static double GetHeightOfNode(Node node)
    {
        return node.getBoundsInLocal().getHeight();
    }


}
