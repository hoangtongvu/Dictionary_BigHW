package User;

import Main.Database;
import Timer.SessionTime;

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

    private boolean isOnline = false;

    public void newAccount(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
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

        //Save access date
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentTime = date.format(formatter);

        currentRecord.setUserName(userName);
        currentRecord.setAccessDate(currentTime);

        System.out.println(currentTime);

        try {
            currentRecord = currentRecord.getRecordDAO().get(userName + " " + currentTime).get();
            System.out.println("Got existing record");
            System.out.println(currentRecord.getSession_time());
        } catch (Exception e) {
            currentRecord.setSession_time(0);
            System.out.println(e.getMessage());
            System.out.println("Date record does not exist");
        }
        dailyRecordList = currentRecord.getRecordDAO().getAll();
        for (DailyRecord record : dailyRecordList) {
            System.out.println(record.getAccessDate() + " " + record.getSession_time());
        }
    }

    public void logoutHandler() {
        setOnline(false);
        SessionTime.getInstance().stopCounter();
        saveSessionData();
        currentUser = null;
    }



    public void saveSessionData() {
        //Save session timer

        //Save score

        //Save access date
        saveCurrentRecord();
        //Save changes to user credential
        saveUserCredentials();
    }

    public void saveUserCredentials() {
        try {
            userDao.save(currentUser);
        } catch (Exception e) {
            System.out.println("User already exist, this exception is for updating user credential");
            System.out.println(e.getMessage());
        }
    }

    public void saveCurrentRecord() {
        currentRecord.setSession_time(SessionTime.getInstance().getSeconds() + currentRecord.getSession_time());
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setStudyGoal(Integer studyGoal) {
        this.studyGoal = studyGoal;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
