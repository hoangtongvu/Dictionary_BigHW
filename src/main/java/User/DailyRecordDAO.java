package User;

import Interfaces.Dao;
import Main.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DailyRecordDAO implements Dao<DailyRecord> {
    String tableName = "daily_record";

    @Override
    public Optional<DailyRecord> get(String id) {
        return Optional.<DailyRecord>empty();
    }

    @Override
    public List<DailyRecord> getAll() {
        String query = "SELECT * FROM daily_record WHERE user_name = ?";
        try {
            PreparedStatement statement = Database.getUserDB().prepareStatement(query);
            statement.setString(1, User.getCurrentUser().getUserName());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User.getCurrentUser().getDailyRecordList().add(new DailyRecord(
                        resultSet.getString("user_name"),
                        resultSet.getString("access_date"),
                        resultSet.getInt("study_time"),
                        resultSet.getInt("session_time")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return User.getCurrentUser().getDailyRecordList();
    }

    @Override
    public void delete(DailyRecord dailyRecord) {

    }

    @Override
    public void update(DailyRecord dailyRecord, String[] params) {

    }

    @Override
    public void save(DailyRecord dailyRecord) {

        String insert = "INSERT INTO " + tableName +
                " (user_name, access_date, study_time, session_time) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = Database.getUserDB().prepareStatement(insert);
            statement.setString (1, dailyRecord.getUserName());
            statement.setString (2, dailyRecord.getAccessDate());
            statement.setInt    (3, dailyRecord.getSession_time());
            statement.setInt (4, dailyRecord.getSession_time());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
