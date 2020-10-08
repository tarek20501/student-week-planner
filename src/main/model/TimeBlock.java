package model;

public class TimeBlock {
    private Time startTime;
    private Time endTime;
    private String label;

    // REQUIRES: start time is earlier than end time
    // EFFECTS: initializes start and end time of this time block to start and end
    // respectively. label of this day is initialized to empty string.
    public TimeBlock(Time start, Time end) {
        startTime = start;
        endTime = end;
        label = "";
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getLabel() {
        return label;
    }
}
