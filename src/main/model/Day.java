package model;

import java.util.ArrayList;
import java.util.List;

public class Day {
    private String label;
    private List<TimeBlock> timeBlocks;

    // EFFECTS: Sets label of this day to the given label and initialize a list to hold
    // time blocks that will be allocated on this day.
    public Day(String label) {
        this.label = label;
        this.timeBlocks = new ArrayList<TimeBlock>();
    }

    // MODIFIES: this
    // EFFECTS: Add timeBlock to the list of allocated time blocks on this day. returns
    // true on success and false on failure due to time conflicts
    public boolean addTimeBlock(TimeBlock timeBlock) {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: Deletes timeblock from the list of allocated time blocks on this day if
    // it exists. Otherwise, does nothing.
    public void deleteTimeBlock(TimeBlock timeBlock) {

    }

    // MODIFIES: this, timeBlock
    // EFFECTS: Resize timeBlock to the given start and end times. Returns true on
    // success and updates timeBlock's start and end times accordingly. Otherwise,
    // returns false on failure due to time conflicts.
    public boolean resizeTimeBlock(TimeBlock timeBlock, Time start, Time end) {
        return false; // stub
    }

    // MODIFIES: this, timeBlock
    // EFFECTS: Moves given timeBlock to the given day at the given start and end times.
    // if the given day refuses to add timeBlock, it returns false. Otherwise, it removes
    // the timeBlock from this day, modifies timeblock accordingly and returns true.
    public boolean moveTimeBlock(TimeBlock timeBlock, Day day, Time start, Time end) {
        return false; // stub
    }

    public String getLabel() {
        return label;
    }

    public List<TimeBlock> getTimeBlocks() {
        return timeBlocks;
    }

    // EFFECTS: Checks if the given timeblock conflicts with any time block on this day.
    // if it does, returns false. Otherwise, returns true. if the given timeBlock already
    // exist on this day, it skips checking if it conflicts with itself.
    private boolean isNoConflict(TimeBlock timeBlock) {
        return false; // stub
    }
}
