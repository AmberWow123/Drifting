package backend.util.time;

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
}
