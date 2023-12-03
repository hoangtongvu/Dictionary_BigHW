package Main.SceneControllers.Account;

import Main.FxmlFileManager;
import Main.ProjectDirectory;
import Main.SceneControllers.BaseSceneController;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditProfilePic extends BaseSceneController implements Initializable {
    List<PicNode> profilePics;
    class PicNode {
        Circle circle;
        String path;

        public void setCircle(Circle circle) {
            this.circle = circle;

            circle.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
                circle.setStroke(Color.rgb(76,142,220));
            });

            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getClickCount() > 1) {
                    User.getCurrentUser().setImagePath(path);
                    User.getCurrentUser().updateImagePath();
                    User.getCurrentUser().updateAllGUI();
                    exitEditPic();
                }
            });

            circle.addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
                circle.setStroke(Color.TRANSPARENT);
            });
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    @FXML
    protected AnchorPane root;
    @FXML
    protected FlowPane flowPane;
    @FXML
    protected Button backButton;
    @FXML
    public void exitEditPic() {
        FxmlFileManager.getInstance().profileSC.editPicPane.setVisible(false);
    }

    @Override
    public void StartShow() {

    }

    @Override
    public void EndShow() {

    }

    @Override
    public void update() {

    }



    public void addChoice(String path) {
        Circle circle = new Circle();
        circle.setRadius(45);
        FlowPane.setMargin(circle, new Insets(10, 10, 10, 10));
        PicNode node = new PicNode();
        circle.setStrokeWidth(5);
        circle.setStroke(Color.TRANSPARENT);
        node.setCircle(circle);
        node.setPath(path);
        loadPicture(circle, path);
        flowPane.getChildren().add(circle);
    }

    public void loadPicture(Circle profilePic, String path) {
        Image image = new Image(String.valueOf(getClass().getResource(path)));
        double sideLength = Math.min(image.getWidth(), image.getHeight());

        ImageView imageView = new ImageView(image);

        imageView.setViewport(new Rectangle2D(
                (image.getWidth() - sideLength) / 2,
                (image.getHeight() - sideLength) / 2,
                sideLength,
                sideLength
        ));

        Circle circleClip = new Circle(sideLength / 2);

        imageView.setClip(circleClip);

        profilePic.setFill(new ImagePattern(imageView.getImage()));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profilePics = new ArrayList<>();
        try {
            List<String> fileNames = Files.list(Paths.get(ProjectDirectory.resourcesPath + "/png/profilePictures"))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());

            for (String file : fileNames) {
                addChoice("/png/profilePictures/" + file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToParent(StackPane parent) {
        parent.getChildren().addAll(root);
    }

    public static EditProfilePic loadInstance() throws IOException {
        FXMLLoader loader;
        String absolutePath = ProjectDirectory.resourcesPath + "\\fxml\\application\\ProfilePicScene.fxml";
        URL fxmlURL = Paths.get(absolutePath).toUri().toURL();
        loader = new FXMLLoader(fxmlURL);
        loader.load();

        return loader.getController();
    }
}
