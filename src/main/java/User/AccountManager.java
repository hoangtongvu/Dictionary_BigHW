package User;

import Main.Database;
import javafx.geometry.Rectangle2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//MEDIATOR FOR MEDIATOR PATTERN
public class AccountManager {
    public enum Status {
        LOGGED_IN,
        REGISTERED,
        INVALID_CREDENTIALS,
        INVALID_CHARACTERS,
        OFFLINE,
        USERNAME_TAKEN,
        NULL_FIELD
    }

    public enum DataCategory {
        SESSION_TIME,
        STUDY_TIME,
        DAILY_GOAL,
        SCORE,
        RANKING,
        COMPLETION_RATIO,
        TOTAL_STUDY_TIME,
        TOTAL_SESSION_TIME,
        USER_NAME
    }

    private static AccountManager instance;
    private AccountManager() {

    }

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public void getPieChart(PieChart dailyGoalChart) {
        int dailyGoal = User.getCurrentUser().getStudyGoal();
        int studyTime = User.getCurrentUser().getCurrentRecord().getStudy_time();
        double completionRate = ((double)studyTime / dailyGoal);
        if (dailyGoal == 0) {
            PieChart.Data incomplete = new PieChart.Data("Incomplete", 100);
            dailyGoalChart.getData().add(incomplete);
            incomplete.getNode().setStyle("-fx-background-color: grey");
        }

        if (completionRate > 1) {
            PieChart.Data progress = new PieChart.Data("Completed", 100);
            dailyGoalChart.getData().add(progress);
            progress.getNode().setStyle("-fx-background-color: green");
        } else {
            PieChart.Data progress = new PieChart.Data("Completed", completionRate * 100);
            PieChart.Data incomplete = new PieChart.Data("Incomplete", (1 - completionRate) * 100);
            dailyGoalChart.getData().add(progress);
            dailyGoalChart.getData().add(incomplete);

            progress.getNode().setStyle("-fx-background-color: green");
            incomplete.getNode().setStyle("-fx-background-color: grey");

            dailyGoalChart.setLabelsVisible(false);

        }
    }

    public void getLabel(Label label, DataCategory dataCategory) {
        switch (dataCategory) {
            case DAILY_GOAL:
                label.setText(formatTime(User.getCurrentUser().getStudyGoal()));
                break;
            case STUDY_TIME:
                label.setText(formatTime(User.getCurrentUser().getCurrentRecord().getStudy_time()));
                break;
            case COMPLETION_RATIO:
                label.setText(String.format("%.2f",User.getCurrentUser().getCompletionRatio()) + "%");
                break;
            case SCORE:
                label.setText(String.valueOf(User.getCurrentUser().getScore()));
                break;
            case SESSION_TIME:
                label.setText(formatTime(User.getCurrentUser().getCurrentRecord().getSession_time()));
                break;
            case TOTAL_SESSION_TIME:
                label.setText(formatTime(User.getCurrentUser().getTotalSessionTime()));
                break;
            case TOTAL_STUDY_TIME:
                label.setText(formatTime(User.getCurrentUser().getTotalStudyTime()));
                break;
            case RANKING:
                label.setText(String.valueOf(User.getCurrentUser().getRank()));
                break;
            case USER_NAME:
                label.setText(String.valueOf(User.getCurrentUser().getUserName()));
                break;
        }
    }

    public String formatTime(int time) {
        if (time < 3600) {
            return String.format("%dm", time/60);
        } else {
            return String.format("%dh%dm", time/3600, time%3600/60);
        }
    }
    public void getLineChart(LineChart<String, Number> lineChart, DataCategory dataCategory, String name) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        List<DailyRecord> recordList = User.getCurrentUser().getDailyRecordList();
        
        if (dataCategory == DataCategory.STUDY_TIME) {
            for (DailyRecord record  : recordList) {
                series.getData().add(new XYChart.Data<>(record.getAccessDate(), record.getStudy_time()));
            }
        } else if (dataCategory == DataCategory.SESSION_TIME) {
            for (DailyRecord record  : recordList) {
                series.getData().add(new XYChart.Data<>(record.getAccessDate(), record.getSession_time()));
            }
        }
        
        lineChart.getData().add(series);

        //Add tool tip
        for (XYChart.Data<String, Number> entry : series.getData()) {
            Tooltip tooltip = getTooltip(name, entry);

            Tooltip.install(entry.getNode(), tooltip);
            tooltip.setShowDelay(Duration.seconds(0));
        }
    }

    public void loadProfilePic(Circle profilePic) {
        String path = User.getCurrentUser().getImagePath();
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

    private static Tooltip getTooltip(String name, XYChart.Data<String, Number> entry) {
        Integer time = (Integer) entry.getYValue();
        String[] tmp = entry.getXValue().split("-");
        String date = tmp[2] + "/" + tmp[1] + "/" + tmp[0];
        Tooltip tooltip = null;
        if (time < 3600) {
            tooltip = new Tooltip(name + ":\n" + String.format("%.2f", time/60f) + " minutes\n" + String.format("Date: %s", date));
        } else {
            tooltip = new Tooltip(name + ":\n" + String.format("%.2f", time/60f/60f) + " hours\n" + String.format("Date: %s", date));
        }
        return tooltip;
    }

    public Status register(String userName, String passWord) throws SQLException, ClassNotFoundException {
        String usrNameRegex = "^[A-Za-z0-9.]+$";
        Pattern usrNamePattern = Pattern.compile(usrNameRegex);

        if (userName.isEmpty() || passWord.isEmpty()) {
            return Status.NULL_FIELD;
        }
        try {
            Matcher matcher = usrNamePattern.matcher(userName);
            if (matcher.matches()) {
                User.getCurrentUser().reset();
                passWord = hashPassword(passWord);
                User.getCurrentUser().newAccount(userName, passWord);
                User.getCurrentUser().setImagePath("/png/profilePictures/default.png");
                User.getCurrentUser().getUserDao().save(User.getCurrentUser());
                return Status.REGISTERED;
            } else {
                return Status.INVALID_CHARACTERS;
            }
        } catch (Exception e) {
            if (!Database.getUserDB().isClosed()) {
                return Status.USERNAME_TAKEN;
            } else {
                return Status.OFFLINE;
            }
        }
    }

    public Status login(String userName, String passWord) throws SQLException, ClassNotFoundException {
        if (userName.isEmpty() || passWord.isEmpty()) {
            return Status.NULL_FIELD;
        }
        try {
            User.getCurrentUser().getUserDao().get(userName);
            String dbPassword = User.getCurrentUser().getPassWord();
            passWord = hashPassword(passWord);

            if (!Database.getUserDB().isClosed()) {
                if (passWord.equals(dbPassword)) {
                    return Status.LOGGED_IN;
                } else {
                    return Status.INVALID_CREDENTIALS;
                }
            } else {
                return Status.OFFLINE;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (!Database.getUserDB().isClosed()) {
                return Status.INVALID_CREDENTIALS;
            } else {
                return Status.OFFLINE;
            }
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
