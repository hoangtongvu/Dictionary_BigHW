package Main.SceneControllers.Account;

import Main.Database;
import Main.FxmlFileManager;
import Main.ProjectDirectory;
import Main.SceneControllers.Dictionary.HomeSceneController;
import User.AccountManager;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;

public class ChangePasswordController {
    @FXML
    protected AnchorPane root;
    @FXML
    protected Label passwordLabel;
    @FXML
    protected Label confirmLabel;
    @FXML
    protected TextField passwordField;
    @FXML
    protected TextField confirmField;
    @FXML
    protected Label notification;
    @FXML
    protected TextField currentField;
    @FXML
    protected Label currentLabel;
    @FXML
    protected Button retryButton;


    public void addToParent(StackPane parent) {
        parent.getChildren().addAll(root);
    }

    private static FXMLLoader loader = null;

    public void  initialize() {
        retryButton.setVisible(false);
    }

    public static ChangePasswordController loadInstance() throws IOException {
        if (loader == null) {
            String absolutePath = ProjectDirectory.resourcesPath + "\\fxml\\application\\ChangePassword.fxml";
            URL fxmlURL = Paths.get(absolutePath).toUri().toURL();
            loader = new FXMLLoader(fxmlURL);
            loader.load();
        }
        return loader.getController();
    }

    @FXML
    public void onEnter() {
        if (confirmField.getText().isEmpty() || passwordField.getText().isEmpty() || currentField.getText().isEmpty()) {
            notification.setTextFill(Color.RED);
            notification.setText("Fields must be not left blank!");
            return;
        }
        try {
            AccountManager.Status status = AccountManager.getInstance().login(User.getCurrentUser().getUserName(), currentField.getText());


            switch (status) {
                case LOGGED_IN:
                    if (!confirmField.getText().equals(passwordField.getText())) {
                        notification.setTextFill(Color.RED);
                        notification.setText("Mismatch between confirm password and new password");
                        return;
                    } else {
                        notification.setTextFill(Color.GREEN);
                        notification.setText("Changed password");
                        User.getCurrentUser().setPassWord(AccountManager.getInstance().hashPassword(confirmField.getText()));
                        User.getCurrentUser().updatePassWord();
                        confirmField.clear();
                        currentField.clear();;
                        passwordField.clear();
                    }
                    break;
                case OFFLINE:
                    setOffLineState();
                    break;
                case INVALID_CREDENTIALS:
                    notification.setTextFill(Color.RED);
                    notification.setText("Wrong user password");
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public void setOffLineState() {
        retryButton.setVisible(true);
        notification.setTextFill(Color.RED);
        notification.setText("You are currently offline");
    }

    public void setOnlineState() {
        retryButton.setVisible(false);
        notification.setTextFill(Color.GREEN);
        notification.setText("");
    }

    @FXML
    public void exitPasswordEdit() {
        FxmlFileManager.getInstance().profileSC.passwordPane.setVisible(false);
    }
}
