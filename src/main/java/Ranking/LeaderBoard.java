package Ranking;

import Main.Database;
import User.User;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {
    private static LeaderBoard instance;

    private LeaderBoard() {
        leaderBoardList = new ArrayList<>();
    }

    private List<UserCard> leaderBoardList;

    public static LeaderBoard getInstance() {
        if (instance == null) {
            instance = new LeaderBoard();
        }
        return instance;
    }

    public void updateLeaderBoard() {
        leaderBoardList.clear();

        String query = "SELECT user_name, score, profile_image_path FROM user_credentials ORDER BY score DESC";
        try {
            PreparedStatement statement = Database.getUserDB().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            int rank = 1;

            while (resultSet.next()) {
//                System.out.println(resultSet.getString("user_name") + " " + resultSet.getInt("score") + " " + resultSet.getString("profile_image_path"));
                leaderBoardList.add(new UserCard(
                        resultSet.getString("user_name"),
                        resultSet.getInt("score"),
                        resultSet.getString("profile_image_path"),
                        rank));
                rank += 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (User.getCurrentUser().isOnline()) {
            User.getCurrentUser().setRank(getUserRank(User.getCurrentUser()));
        }
    }

    public List<UserCard> getLeaderBoardList() {
        return leaderBoardList;
    }

    public void updateGUI(VBox vBox) {
        updateLeaderBoard();

        for (UserCard userCard : leaderBoardList) {
            System.out.println(userCard.getUserName());
            vBox.getChildren().addAll(userCard.getUserCardShape().getCard());
        }
    }

    public int getUserRank(User user) {
        for (UserCard userCard : leaderBoardList) {
            if (userCard.getUserName().equals(user.getUserName())){
                return userCard.getRank();
            }
        }
        return -1;
    }
}
