package model;

import org.json.JSONObject;
import persistence.Writable;
import java.time.format.DateTimeParseException;
import java.time.LocalTime;

// time block that is defined by a start and end times. It has a label to identify
// what is happening during the time period it covers.
public class TimeBlock implements Writable {
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
    // respectively given in String HH:MM. label of this day is initialized to empty string.
    // throws DateTimeParseException if time is not "HH:MM"
    public TimeBlock(String start, String end) throws DateTimeParseException {
        this(parseTime(start), parseTime(end));
    }

    // MODIFIES: this
    // EFFECTS: set start time to given LocalTime startTime
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // MODIFIES: this
    // EFFECTS: set start time to given startTime
    // throws DateTimeParseException if time is not "HH:MM"
    public void setStartTime(String startTime) throws DateTimeParseException {
        setStartTime(parseTime(startTime));
    }

    // MODIFIES: this
    // EFFECTS: set end time to given LocalTime endTime
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // MODIFIES: this
    // EFFECTS: set end time to given endTime
    // throws DateTimeParseException if time is not "HH:MM"
    public void setEndTime(String endTime) throws DateTimeParseException {
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

    // EFFECTS: instantiate a LocalTime object from time
    // and returns a reference to it
    // throws DateTimeParseException if time is not "HH:MM"
    private static LocalTime parseTime(String time) throws DateTimeParseException {
        return LocalTime.parse(time);
    }

    // EFFECTS: returns a string "HH:MM" representation of time object
    private static String localTimeToString(LocalTime time) {
        return time.toString();
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("label", label);
        json.put("startTime", getStringStartTime());
        json.put("endTime", getStringEndTime());
        return json;
    }
}
