package Main.SceneControllers.Account;

import Main.Database;
import Main.FxmlFileManager;
import Main.ProjectDirectory;
import Main.SceneControllers.BaseSceneController;
import Main.SceneControllers.Widget.StudyTimerController;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import User.AccountManager;

public class LoginSceneController extends BaseSceneController {
    private boolean isRegistering = false;

    @FXML
    protected TextField nameField;
    @FXML
    protected PasswordField passwordField;
    @FXML
    protected Button continueButton;
    @FXML
    protected Button registerButton;
    @FXML
    protected Button retryButton;
    @FXML
    protected AnchorPane messagePane;
    @FXML
    protected Label loginLabel;
    @FXML
    protected Label suggestLabel;
    @FXML
    protected AnchorPane root;

    protected Label message;
    public void initialize() {
        retryButton.setVisible(false);

        message = new Label();
        message.setTextAlignment(TextAlignment.CENTER);
        message.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(message, 0d);
        AnchorPane.setBottomAnchor(message, 0d);
        AnchorPane.setRightAnchor(message, 0d);
        AnchorPane.setLeftAnchor(message, 0d);
        messagePane.getChildren().add(message);

        try {
            if (Database.getUserDB().isClosed()) {
                setOffLineState();
                throw new Exception();
            } else {

            }
        } catch (Exception e) {
            //Offline
            System.out.println("Currently offline");
            System.out.println(e.getMessage());
            setOffLineState();
        }
    }

    @FXML
    protected Button continueWithoutAccountButton;

    @Override
    public void StartShow() {

    }

    @Override
    public void EndShow() {

    }

    @Override
    public void update() {

    }

    public void setOffLineState() {
        retryButton.setVisible(true);
        message.setTextFill(Color.RED);
        message.setText("You are currently offline");
    }

    public void setOnlineState() {
        retryButton.setVisible(false);
        message.setTextFill(Color.GREEN);
        message.setText("Connected!");
    }
    @FXML
    public void retryConnection() {
        try {
            Database.connectUserDB();
            if (Database.getUserDB().isClosed()) {
                setOffLineState();
            } else {
                setOnlineState();
            }
        } catch (Exception e) {
            setOffLineState();
        }
    }

    @FXML
    public void continueWithoutAccount() {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().homeSC);
    }

    @FXML
    public void onEnter() throws SQLException, ClassNotFoundException {
        if (isRegistering) {
            AccountManager.Status status = AccountManager.getInstance().register(nameField.getText(), passwordField.getText());
            switch (status) {
                case REGISTERED:
                    message.setText("");
                    message.setTextFill(Color.GREEN);
                    message.setText("Register successful!");

                    break;
                case OFFLINE:
                    setOffLineState();
                    break;
                case INVALID_CHARACTERS:
                    message.setTextFill(Color.RED);
                    message.setText("Username can only contains '0-9', 'A-Z', 'a-z', '.'");
                    break;
                case USERNAME_TAKEN:
                    message.setTextFill(Color.RED);
                    message.setText("Username already exist");
                    break;
                case NULL_FIELD:
                    message.setTextFill(Color.RED);
                    message.setText("PassWord and Username must not be left blank!");
                    break;
            }
        } else {
            AccountManager.Status status = AccountManager.getInstance().login(nameField.getText(), passwordField.getText());
            switch (status) {
                case LOGGED_IN:
                    message.setTextFill(Color.GREEN);
                    message.setText("Login successful!");
                    nameField.clear();
                    passwordField.clear();
                    User.getCurrentUser().loginHandler();
                    FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().homeSC);
                    break;
                case INVALID_CREDENTIALS:
                    message.setTextFill(Color.RED);
                    message.setText("Incorrect username or password");
                    break;
                case OFFLINE:
                    setOffLineState();
                    break;
                case NULL_FIELD:
                    message.setTextFill(Color.RED);
                    message.setText("Password and Username must not be left blank!");
                    break;
            }
        }
    }

    @FXML
    public void onRegister() {
        if (isRegistering) {
            //Revert back to log in
            goToLogin();
        } else {
            //Go to register
            goToRegister();
        }
    }

    public void goToRegister() {
        passwordField.clear();
        nameField.clear();
        loginLabel.setText("Creating a new account");
        registerButton.setText("Login to an account");
        suggestLabel.setText("Don't have an account yet?");
        messagePane.getChildren().clear();
        messagePane.getChildren().add(message);
        message.setText("");
        isRegistering = true;
    }

    public void goToLogin() {
        passwordField.clear();
        nameField.clear();
        loginLabel.setText("Login");
        registerButton.setText("Register a new account");
        suggestLabel.setText("Already have an account?");
        messagePane.getChildren().clear();
        message.setText("");
        messagePane.getChildren().add(message);
        isRegistering = false;
    }

    public void addToParent(AnchorPane parent, boolean continueButtonStatus) {
        AnchorPane.setRightAnchor(root, 0d);
        AnchorPane.setTopAnchor(root, 0d);
        AnchorPane.setLeftAnchor(root, 0d);
        AnchorPane.setBottomAnchor(root, 0d);

        parent.getChildren().addAll(root);
        continueWithoutAccountButton.setVisible(continueButtonStatus);

    }

    public static LoginSceneController loadInstance() throws IOException {
        FXMLLoader loader;
        String absolutePath = ProjectDirectory.resourcesPath + "\\fxml\\application\\LoginScreen.fxml";
        URL fxmlURL = Paths.get(absolutePath).toUri().toURL();
        loader = new FXMLLoader(fxmlURL);
        loader.load();

        return loader.getController();
    }
}
