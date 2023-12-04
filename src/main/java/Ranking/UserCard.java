package Ranking;

public class UserCard {
    String userName;
    int score;
    int rank;
    String profilePath;
    private UserCardShape userCardShape;

    public UserCard(String name, int score, String profilePath, int rank) {
        this.userName = name;
        this.score = score;
        this.rank = rank;
        this.profilePath = profilePath;
        this.userCardShape = new UserCardShape(name, profilePath, score, rank);
    }

    public UserCardShape getUserCardShape() {
        return userCardShape;
    }


    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }
}