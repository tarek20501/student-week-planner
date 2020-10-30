package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// day that has a label (such as Monday) and a list of time blocks for busy time and tasks.
public class Day implements Writable {
    private final String label;
    private final List<TimeBlock> timeBlocks;

    // EFFECTS: Sets label of this day to the given label and initialize a list to hold
    // time blocks that will be allocated on this day.
    public Day(String label) {
        this.label = label;
        this.timeBlocks = new ArrayList<>();
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
    // EFFECTS: Deletes timeBlock from the list of allocated time blocks on this day if
    // it exists and returns true. Otherwise, does nothing and returns false.
    public boolean deleteTimeBlock(TimeBlock timeBlock) {
        return timeBlocks.remove(timeBlock);
    }

    // MODIFIES: this, timeBlock
    // EFFECTS: Moves timeBlock to day and sets its start and end times to newStart and newEnd
    // respectively. If day equals to this day, checks for conflicts and applies the new times (returns true).
    // If day is another day, tries to add it to that day. If the other day adds it, it is removed from this
    // day (returns true). Otherwise, nothing happens and false is returned.
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

    // EFFECTS: Checks if the given timeBlock conflicts with any time block on this day.
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

            if (!((newEnd.isBefore(currStart)
                    || newEnd.equals(currStart))
                    || newStart.isAfter(currEnd)
                    || newStart.equals(currEnd))) {
                return false;
            }
        }
        return true;
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("label", label);
        json.put("timeBlocks", timeBlocksToJson());
        return json;
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns time blocks in this day as a JSON array
    private JSONArray timeBlocksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (TimeBlock timeBlock : timeBlocks) {
            jsonArray.put(timeBlock.toJson());
        }
        return jsonArray;
    }
}
