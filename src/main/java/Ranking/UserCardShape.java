package Ranking;

import User.AccountManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

public class UserCardShape {
    private AnchorPane card;
    private Circle profilePic;
    private Label scoreLabel;
    private Label nameLabel;
    private Label rankLabel;
    private String styleSheet;

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }

    public UserCardShape(String name, String path, int score, int rank) {
//        card.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        card = new AnchorPane();
        card.setMaxHeight(50.0);
        card.setPrefHeight(128.0);
        card.setPrefWidth(287.0);
        card.setStyle("-fx-background-color: pink;");
        VBox.setVgrow(card, Priority.ALWAYS);

        profilePic = new Circle();
        profilePic.setFill(Color.DODGERBLUE);
        profilePic.setLayoutX(38.0);
        profilePic.setLayoutY(31.0);
        profilePic.setRadius(24.0);
        profilePic.setStroke(Color.BLACK);
        profilePic.setStrokeType(StrokeType.INSIDE);

        //Load profile picture
        loadProfilePic(profilePic, path);

        AnchorPane.setLeftAnchor(profilePic, 14.0);
        AnchorPane.setTopAnchor(profilePic, 7.0);

        //Set username
        nameLabel = new Label();
        nameLabel.setText(name);

        nameLabel.setLayoutX(70.0);
        nameLabel.setLayoutY(7.0);
//        nameLabel.setStyle("-fx-font-weight: bold;");
//        nameLabel.setFont(new Font(22.0));
        AnchorPane.setLeftAnchor(nameLabel, 70.0);
        AnchorPane.setTopAnchor(nameLabel, 7.0);

        //Set rank
        rankLabel = new Label();
        rankLabel.setText(String.valueOf(rank));

        rankLabel.setLayoutX(244.0);
        rankLabel.setLayoutY(13.0);
//        rankLabel.setStyle("-fx-font-weight: bold;");
//        rankLabel.setFont(new Font(24.0));
        AnchorPane.setRightAnchor(rankLabel, 15.0);
        AnchorPane.setTopAnchor(rankLabel, 14.0);

        //Set score
        scoreLabel = new Label();
        scoreLabel = new Label(String.valueOf(score));

        scoreLabel.setLayoutX(70.0);
        scoreLabel.setLayoutY(30.0);
//        scoreLabel.setStyle("-fx-font-weight: bold;");
//        scoreLabel.setFont(new Font(18.0));
        AnchorPane.setLeftAnchor(scoreLabel, 70.0);
        AnchorPane.setTopAnchor(scoreLabel, 30.0);

        card.getChildren().addAll(profilePic, nameLabel, rankLabel, scoreLabel);
    }

    public void loadProfilePic(Circle profilePic, String path) {
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

    public AnchorPane getCard() {
        return card;
    }
}
