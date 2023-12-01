package User;

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


    private User() {
        userDao = new UserDao();
        dailyRecordList = new ArrayList<>();
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
