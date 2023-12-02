package User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DailyRecord {
    private String accessDate;
    private int study_time;
    private int session_time;
    private String userName;

    private DailyRecordDAO recordDAO;

    public DailyRecord() {

    }

    public DailyRecordDAO getRecordDAO() {
        if (recordDAO == null) {
            recordDAO = new DailyRecordDAO();
        }
        return recordDAO;
    }

    public DailyRecord(String userName, String accessDate, int studyTime, int sessionTime) {
        this.userName = userName;
        this.accessDate = accessDate;
        this.study_time = studyTime;
        this.session_time = sessionTime;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public int getStudy_time() {
        return study_time;
    }

    public int getSession_time() {
        return session_time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAccessDate(String accessDate) {
        this.accessDate = accessDate;
    }

    public void setSession_time(int session_time) {
        this.session_time = session_time;
    }

    public void setStudy_time(int study_time) {
        this.study_time = study_time;
    }
}
