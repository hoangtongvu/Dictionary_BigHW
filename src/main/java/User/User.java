package User;

public class User {
    private String userName;
    private String passWord;
    private Integer studyGoal;
    private String imagePath;
    private Integer score;
    private String startTime;
    private String endTime;
    private String studiedTime;
    private UserDao userDao;
    private static User currentUser;

    private User() {
        userDao = new UserDao();
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

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStudiedTime(String studiedTime) {
        this.studiedTime = studiedTime;
    }

    public void setStudyGoal(Integer studyGoal) {
        this.studyGoal = studyGoal;
    }

    public String getEndTime() {
        return endTime;
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

    public String getStartTime() {
        return startTime;
    }

    public String getStudiedTime() {
        return studiedTime;
    }

    public String getUserName() {
        return userName;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
