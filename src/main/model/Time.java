package model;

import java.util.Arrays;
import java.util.List;

public class Time {
    public int hours;
    public int minutes;

    // REQUIRES: time to be in "HH:MM" format.
    public Time(String time) {
        String[] hoursMinutes;
        hoursMinutes = time.split(":", 2);
        this.hours = Integer.parseInt(hoursMinutes[0]);
        this.minutes = Integer.parseInt(hoursMinutes[1]);
    }

    //REQUIRES: hours in [0,23] and minutes in [0,59]
    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }
}
