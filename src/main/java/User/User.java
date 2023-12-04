package User;

import Main.Database;
import Main.FxmlFileManager;
import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import Ranking.LeaderBoard;
import Timer.SessionTime;
import Timer.StudyTimer;
import javafx.fxml.FXML;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private String userName;
    private String passWord;
    private Integer studyGoal;
    private String imagePath;
    private Integer score;
    private UserDao userDao;
    private static User currentUser;
    private List<DailyRecord> dailyRecordList;
    private DailyRecord currentRecord;

    private int totalSessionTime;
    private int totalStudyTime;
    private int rank;

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return LeaderBoard.getInstance().getUserRank(currentUser);
    }

    private boolean isOnline = false;

    public void newAccount(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        imagePath = "/png/profilePictures/default.png";
        score = 0;
        studyGoal = 0;
        imagePath = ""; //Default profile picture
        totalSessionTime = 0;
        totalStudyTime = 0;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOnline() {
        return isOnline;
    }

    private User() {
        userDao = new UserDao();
        dailyRecordList = new ArrayList<>();
        currentRecord = new DailyRecord();
    }

    public void reset() {
        currentUser = null;
    }

    public void loginHandler() {
        setOnline(true);
         userDao.get(userName);
        //Start session counter
        SessionTime.getInstance().startCounter();

        //Initiate access date
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentTime = date.format(formatter);

        currentRecord.setUserName(userName);
        currentRecord.setAccessDate(currentTime);

        System.out.println(currentTime);

        dailyRecordList = currentRecord.getRecordDAO().getAll();

        try {
            currentRecord = currentRecord.getRecordDAO().get(userName + " " + currentTime).get();
            System.out.println("Got existing record");
            System.out.println(currentRecord.getSession_time());
        } catch (Exception e) {
            currentRecord.setSession_time(0);
            System.out.println(e.getMessage());
            dailyRecordList.add(currentRecord);
            System.out.println("Date record does not exist");
        }

        for (DailyRecord record : dailyRecordList) {
            totalSessionTime += record.getSession_time();
            totalStudyTime += record.getStudy_time();
        }

        FxmlFileManager.getInstance().navigationPaneSC.update();
        FxmlFileManager.getInstance().homeSC.update();
    }

    public double getCompletionRatio() {
        if (studyGoal == 0) {
            return 0;
        } else {
            return (double) currentRecord.getStudy_time() /studyGoal * 100;

        }
    }
    public int getTotalSessionTime() {
        return totalSessionTime;
    }

    public int getTotalStudyTime() {
        return totalStudyTime;
    }

    public void logoutHandler() {
        setOnline(false);
        SessionTime.getInstance().stopCounter();
        updateAll();
        saveSessionData();
        updateAllGUI();
        currentUser = null;
    }

    public void saveSessionData() {
        //Save access date
        saveCurrentRecord();
        //Save changes to user credential
        saveUserCredentials();
    }

    public void saveUserCredentials() {
        try {
            updateAll();
        } catch (Exception e) {
            System.out.println("User already exist, this exception is for updating user credential");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveCurrentRecord() {
        currentRecord.setSession_time(SessionTime.getInstance().getSeconds() + currentRecord.getSession_time());
        currentRecord.setStudy_time(StudyTimer.getInstance().getTotalTime()  + currentRecord.getStudy_time());
        try {
            currentRecord.getRecordDAO().save(currentRecord);
            System.out.println("Saved new date record");
        } catch (Exception e) {
            System.out.println("Overridden new save record");

            String[] param = {"study_time", String.valueOf(currentRecord.getStudy_time())};
            currentRecord.getRecordDAO().update(currentRecord, param);

            param = new String[]{"session_time", String.valueOf(currentRecord.getSession_time())};
            currentRecord.getRecordDAO().update(currentRecord, param);
        }
    }

    public void updateAllGUI() {
        FxmlFileManager.getInstance().profileSC.update();
        FxmlFileManager.getInstance().homeSC.update();
        FxmlFileManager.getInstance().navigationPaneSC.update();
    }

    public static User getCurrentUser() {
        if (currentUser == null) {
            currentUser = new User();
        }
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public DailyRecord getCurrentRecord() {
        return currentRecord;
    }

    public List<DailyRecord> getDailyRecordList() {
        return dailyRecordList;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void updateScore() {
        this.userDao.update(currentUser, new String[] {"score", String.valueOf(score)});
        //Update in database, only in effect until the next load from database
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void updateImagePath() {
        this.userDao.update(currentUser, new String[] {"profile_image_path", imagePath});
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void updatePassWord() {
        this.userDao.update(currentUser, new String[] {"pass_word", passWord});
    }

    public void setStudyGoal(Integer studyGoal) {
        this.studyGoal = studyGoal;
    }

    public void updateStudyGoal() {
        this.userDao.update(currentUser, new String[] {"study_goal", String.valueOf(studyGoal)});
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void updateUserName() {
        this.userDao.update(currentUser, new String[] {"user_name", String.valueOf(userName)});
    }

    public void updateAll() {
        updateUserName();
        updateScore();
        updateImagePath();
        updatePassWord();
        updateStudyGoal();
    }

    public Integer getScore() {
        return score;
    }

    public Integer getStudyGoal() {
        return studyGoal;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getUserName() {
        return userName;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
