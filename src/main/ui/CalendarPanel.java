package ui;

import javax.swing.*;

import static ui.WeekPlannerFrame.NUMBER_OF_ROWS;

public abstract class CalendarPanel extends JPanel {

    // REQUIRES: panel has height equal to week panel's height
    // EFFECTS: returns the height of a cell in the calendar
    public int getCellHeight() {
        return this.getHeight() / NUMBER_OF_ROWS;
    }
}
