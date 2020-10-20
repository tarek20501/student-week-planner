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

    // REQUIRES: start time is earlier than end time and both be in "HH:MM" format
    // EFFECTS: initializes start and end times of this time block to start and end
    // respectively given in String HH:MM. label of this day is initialized to empty string.
    public TimeBlock(String start, String end) {
        this(parseTime(start), parseTime(end));
    }

    // MODIFIES: this
    // EFFECTS: set start time to given LocalTime startTime
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // REQUIRES: startTime be "HH:MM" format
    // MODIFIES: this
    // EFFECTS: set start time to given startTime
    public void setStartTime(String startTime) {
        setStartTime(parseTime(startTime));
    }

    // MODIFIES: this
    // EFFECTS: set end time to given LocalTime endTime
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // REQUIRES: endTime be "HH:MM" format
    // MODIFIES: this
    // EFFECTS: set end time to given endTime
    public void setEndTime(String endTime) {
        setEndTime(parseTime(endTime));
    }

    // MODIFIES: this
    // EFFECTS: sets label of this time block to label
    public void setLabel(String label) {
        this.label = label;
    }

    // EFFECTS: returns start time
    public LocalTime getStartTime() {
        return startTime;
    }

    // EFFECTS: returns start time as a string in this format "HH:MM"
    public String getStringStartTime() {
        return localTimeToString(getStartTime());
    }

    // EFFECTS: returns end time
    public LocalTime getEndTime() {
        return endTime;
    }

    // EFFECTS: returns end time as a string in this format "HH:MM"
    public String getStringEndTime() {
        return localTimeToString(getEndTime());
    }

    // EFFECTS: returns the label of this time block
    public String getLabel() {
        return label;
    }

    // REQUIRES: time be "HH:MM" format
    // EFFECTS: instantiate a LocalTime object from time
    // and returns a reference to it
    private static LocalTime parseTime(String time) {
        return LocalTime.parse(time);
    }

    // EFFECTS: returns a string "HH:MM" representation of time object
    private static String localTimeToString(LocalTime time) {
        return time.toString();
    }
}
