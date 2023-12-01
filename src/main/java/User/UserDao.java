package User;

import Interfaces.Dao;
import Main.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {
    private String tableName = "user_credentials";

    @Override
    public Optional<User> get(String userName) {
        String query = "SELECT * FROM user_credentials WHERE user_name = '" +  userName + "'";
        try {
            PreparedStatement statement = Database.getUserDB().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            User.getCurrentUser().setImagePath(resultSet.getString("profile_image_path"));
            User.getCurrentUser().setUserName(resultSet.getString("user_name"));
            User.getCurrentUser().setPassWord(resultSet.getString("pass_word"));
            User.getCurrentUser().setStudyGoal(resultSet.getInt("study_goal"));
            User.getCurrentUser().setScore(resultSet.getInt("score"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(User.getCurrentUser());
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void delete(User user) {
        String update = "DELETE * FROM user_credentials WHERE user_name = " +  user.getUserName();
        try {
            PreparedStatement statement = Database.getUserDB().prepareStatement(update);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param user user instance
     * @param params p[1] = attribute, p[2] = value
     *
     */

    @Override
    public void update(User user, String[] params) {
        String update = "UPDATE " + tableName + " SET " + params[0] + " = ? WHERE user_name = ?";
        try {
            PreparedStatement statement = Database.getUserDB().prepareStatement(update);
            statement.setString(1, params[1]);
            statement.setString(2, user.getUserName());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void save(User user) {
        String insert = "INSERT INTO " + tableName +
                " (user_name, pass_word, study_goal, profile_image_path, score) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = Database.getUserDB().prepareStatement(insert);
            statement.setString (1, user.getUserName());
            statement.setString (2, user.getPassWord());
            statement.setInt    (3, user.getStudyGoal());
            statement.setString (4, user.getImagePath());
            statement.setInt    (5, user.getScore());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
