package User;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DailyRecord {
    private int timeInSeconds;
    private DateTimeFormatter dtt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDateTime currentTime = LocalDateTime.now();
    private String currentDate;
    DailyRecordDAO recordDAO;

    public DailyRecord() {

    }


}
