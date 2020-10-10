package model;

import java.time.LocalTime;
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
        if (isNoConflict(timeBlock)) {
            timeBlocks.add(timeBlock);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: Deletes timeblock from the list of allocated time blocks on this day if
    // it exists and returns true. Otherwise, does nothing and returns false.
    public boolean deleteTimeBlock(TimeBlock timeBlock) {
        return timeBlocks.remove(timeBlock);
    }

    // MODIFIES: this, timeBlock
    // EFFECTS: Moves given timeBlock to the given day at the given start and end times.
    // if the given day refuses to add timeBlock, it returns false. Otherwise, it removes
    // the timeBlock from this day, modifies timeblock accordingly and returns true.
    public boolean modifyTimeBlock(TimeBlock timeBlock, Day day, LocalTime newStart, LocalTime newEnd) {
        // Cache old times
        LocalTime oldStart = timeBlock.getStartTime();
        LocalTime oldEnd = timeBlock.getEndTime();
        // Apply new times
        timeBlock.setStartTime(newStart);
        timeBlock.setEndTime(newEnd);
        // Check for conflicts
        if (day.equals(this)) {
            if (isNoConflict(timeBlock)) {
                return true;
            }
        } else {
            if (day.addTimeBlock(timeBlock)) {
                deleteTimeBlock(timeBlock);
                return true;
            }
        }
        // Restore old times since there is conflict
        timeBlock.setStartTime(oldStart);
        timeBlock.setEndTime(oldEnd);
        return false;
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
    private boolean isNoConflict(TimeBlock newTimeBlock) {
        LocalTime newStart = newTimeBlock.getStartTime();
        LocalTime newEnd = newTimeBlock.getEndTime();

        for (TimeBlock currTimeBlock : timeBlocks) {
            if (newTimeBlock == currTimeBlock) {
                continue;
            }

            LocalTime currStart = currTimeBlock.getStartTime();
            LocalTime currEnd = currTimeBlock.getEndTime();

            if (newStart.isAfter(currStart) && newStart.isBefore(currEnd)) {
                return false;
            }
            if (newEnd.isAfter(currStart) && newEnd.isBefore(currEnd)) {
                return false;
            }
            if (currStart.isAfter(newStart) && currEnd.isBefore(newEnd)) {
                return false;
            }
            if (newStart.equals(currStart) || newEnd.equals(currEnd)) {
                return false;
            }
        }
        return true;
    }
}
