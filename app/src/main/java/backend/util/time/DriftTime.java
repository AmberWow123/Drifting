package backend.util.time;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

public class DriftTime {
    long currentTimeEpoch;

    public DriftTime(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        currentTimeEpoch = calendar.getTimeInMillis();
    }

    public long getTimestamp() {
        return currentTimeEpoch;
    }

    /* Return a date string given a Drifting custom timestamp*/
    public static String getDate(long timeInMillis) {
        Date date = new Date(timeInMillis);
        return date.toString();
    }
}
