package model;

import java.time.LocalTime;

public class TimeBlock {
    private LocalTime startTime;
    private LocalTime endTime;
    private String label;

    // REQUIRES: start time is earlier than end time
    // EFFECTS: initializes start and end times of this time block to start and end
    // respectively. label of this day is initialized to empty string.
    public TimeBlock(LocalTime start, LocalTime end) {
        startTime = start;
        endTime = end;
        label = "";
    }

    // REQUIRES: start time is earlier than end time
    // EFFECTS: initializes start and end times of this time block to start and end
    // respectively. label of this day is initialized to empty string.
    public TimeBlock(String start, String end) {
        startTime = parseTime(start);
        endTime = parseTime(end);
        label = "";
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(String startTime) {
        setStartTime(parseTime(startTime));
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(String endTime) {
        setEndTime(parseTime(endTime));
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public String getStringStartTime() {
        return localTimeToString(startTime);
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getStringEndTime() {
        return localTimeToString(endTime);
    }

    public String getLabel() {
        return label;
    }

    // REQUIRES: time be "HH:MM" format
    // EFFECTS: instantiate a LocalTime object from time
    // and returns a reference to it
    private LocalTime parseTime(String time) {
        return LocalTime.parse(time);
    }

    // EFFECTS: returns a string "HH:MM" representation of time object
    private String localTimeToString(LocalTime time) {
        return time.toString();
    }
}
