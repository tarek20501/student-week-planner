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

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getLabel() {
        return label;
    }
}
