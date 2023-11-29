package Main.SceneControllers.Account;

import Main.Database;
import Main.FxmlFileManager;
import Main.SceneControllers.BaseSceneController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginSceneController extends BaseSceneController {
    private boolean isRegistering = false;
    private boolean isOnline = false;

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
                offLineState();
                throw new Exception();
            } else {
                isOnline = true;
            }
        } catch (Exception e) {
            //Offline
            System.out.println("Currently offline");
            System.out.println(e.getMessage());
            offLineState();
        }
    }

    @Override
    public void StartShow() {

    }

    @Override
    public void EndShow() {

    }

    public void offLineState() {
        isOnline = false;
        retryButton.setVisible(true);
        message.setTextFill(Color.RED);
        message.setText("You are currently offline");
    }
    @FXML
    public void retryConnection() {
        try {
            Database.connectUserDB();
            if (Database.getUserDB().isClosed()) {
                offLineState();
            } else {
                isOnline = true;
                retryButton.setVisible(false);
                message.setTextFill(Color.GREEN);
                message.setText("Connected!");
            }
        } catch (Exception e) {
            offLineState();
        }

    }

    @FXML
    public void continueWithoutAccount() {
        FxmlFileManager.switchScene(FxmlFileManager.getInstance().homeScene);
    }

    @FXML
    public void onEnter() throws SQLException, ClassNotFoundException {
        String userName = nameField.getText();
        String passWord = passwordField.getText();
        String usrNameRegex = "^[A-Za-z0-9.]+$";
        Pattern usrNamePattern = Pattern.compile(usrNameRegex);

        if (isRegistering) {
            String update = "INSERT INTO user_credentials (user_name, pass_word) VALUES (?, ?)";
            try {
                Matcher matcher = usrNamePattern.matcher(userName);
                if (matcher.matches()) {
                    message.setText("");
                    PreparedStatement statement = Database.getUserDB().prepareStatement(update);
                    statement.setString(1, userName);
                    passWord = hashPassword(passWord);
                    statement.setString(2, passWord);
                    statement.execute();
                    message.setTextFill(Color.GREEN);
                    message.setText("Register successful!");

                } else {
                    message.setTextFill(Color.RED);
                    message.setText("Username can only contains '0-9', 'A-Z', 'a-z', '.'");
                }


            } catch (Exception e) {
                if (!Database.getUserDB().isClosed()) {
                    message.setText("Username already exist");
                } else {
                    offLineState();
                }
            }
        } else {
            String query = "SELECT * FROM user_credentials WHERE user_name = ?";
            try {
                PreparedStatement statement = Database.getUserDB().prepareStatement(query);
                statement.setString(1, userName);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                String dbPassword = resultSet.getString("pass_word");
                passWord = hashPassword(passWord);
                if (!Database.getUserDB().isClosed()) {
                    if (passWord.equals(dbPassword)) {
                        message.setTextFill(Color.GREEN);
                        message.setText("Login successful!");
                        FxmlFileManager.switchScene(FxmlFileManager.getInstance().homeScene);
                    } else {
                        message.setTextFill(Color.RED);
                        message.setText("Incorrect username or password");
                    }
                } else {
                    offLineState();
                }
            } catch (Exception e) {
                System.out.println(e);
                message.setTextFill(Color.RED);
                if (!Database.getUserDB().isClosed()) {
                    message.setText("Incorrect username or password");
                } else {
                    offLineState();
                }
            }
        }
    }

    @FXML
    public void onRegister() {
        if (isRegistering) {
            //Revert back to log in
            loginLabel.setText("Login");
            registerButton.setText("Register a new account");
            messagePane.getChildren().clear();
            message.setText("");
            messagePane.getChildren().add(message);
            isRegistering = false;
        } else {
            //Go to register
            loginLabel.setText("Creating a new account");
            registerButton.setText("Login to an account");
            messagePane.getChildren().clear();
            messagePane.getChildren().add(message);
            message.setText("");
            isRegistering = true;
        }
    }

    public String hashPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        md.update(password.getBytes());

        byte[] hash = md.digest();

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hash) {
            stringBuilder.append(String.format("%02x", b));
        }

        return stringBuilder.toString();
    }
}
