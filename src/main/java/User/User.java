package User;

import Timer.SessionTime;

import java.util.ArrayList;
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
    }

    public void logoutHandler() {
        setOnline(false);
        currentUser = null;
        SessionTime.getInstance().stopCounter();
    }



    public void saveSessionData() {
        //Save session timer
        //Save score
        //Save access date
        userDao.save(currentUser);

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
