package ui;

import exception.InvalidTimeBlockException;
import model.Day;
import model.TimeBlock;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ui.WeekPlannerFrame.*;

// Green vertical panel that houses color coded time blocks
public class DayPanel extends CalendarPanel {
    private final Day day;
    private final JLabel dayLabel;
    private final List<TimeBlockLabel> timeBlockLabels;
    private final GridPanel gridPanel;

    // EFFECT: setup this panel with null layout
    //          add time blocks first (top layer)
    //          add grid second (middle layer)
    //          add day label third (bottom layer)
    public DayPanel(Day day) {
        // Setup this panel
        this.setBackground(NEPHRITIS);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(COLUMN_WIDTH, COLUMN_HEIGHT));
        // Initialize Fields and add them to the panel
        this.day = day;
        timeBlockLabels = new ArrayList<>();
        initializeTimeBlockLabels();
        gridPanel = new GridPanel();
        this.add(gridPanel);
        dayLabel = new JLabel(day.getLabel());
        this.add(dayLabel);
    }

    // EFFECTS: calls functions that draw
    //          - time blocks
    //          - grid
    //          - day label
    //          in the order above
    public void paint(Graphics g) {
        drawTimeBlocks();
        drawGrid();
        drawDayLabel();
        super.paint(g);
    }

    public Day getDay() {
        return day;
    }

    // MODIFIES: this
    // EFFECTS: wraps Day.addTimeBlock function in the low end
    public void addTimeBlockLabel(LocalTime start, LocalTime end, TimeBlock.Color color) {
        try {
            TimeBlock timeBlock = new TimeBlock(start, end, color);
            if (day.addTimeBlock(timeBlock)) {
                TimeBlockLabel timeBlockLabel = new TimeBlockLabel(timeBlock);
                timeBlockLabels.add(timeBlockLabel);
                this.add(timeBlockLabel,0);
                this.repaint();
            }
        } catch (InvalidTimeBlockException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: wraps Day.deleteTimeBlock function in the low end
    public void deleteTimeBlockLabel(TimeBlockLabel timeBlockLabel) {
        this.remove(timeBlockLabel);
        day.deleteTimeBlock(timeBlockLabel.getTimeBlock());
        timeBlockLabels.remove(timeBlockLabel);
        this.repaint();
    }

    // EFFECTS: return reference to timeBlockLabel if time falls the timeBlockLabel range
    //          otherwise, returns null
    public TimeBlockLabel isThereTimeBlock(LocalTime time) {
        for (TimeBlockLabel timeBlockLabel : timeBlockLabels) {
            LocalTime startTime = timeBlockLabel.getTimeBlock().getStartTime();
            LocalTime endTime = timeBlockLabel.getTimeBlock().getEndTime();
            if (time.isAfter(startTime) && time.isBefore(endTime)) {
                return timeBlockLabel;
            }
        }
        return null;
    }

    // EFFECTS: get time blocks from day field
    //          create time block labels from the time blocks
    //          add those labels to this panel
    private void initializeTimeBlockLabels() {
        for (TimeBlock timeBlock : day.getTimeBlocks()) {
            timeBlockLabels.add(new TimeBlockLabel(timeBlock));
        }
        for (TimeBlockLabel timeBlockLabel : timeBlockLabels) {
            this.add(timeBlockLabel);
        }
    }

    // EFFECTS: set day label properties
    //          update width and height based on this panel size
    private void drawDayLabel() {
        dayLabel.setBackground(MIDNIGHT_BLUE);
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setOpaque(true);
        dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dayLabel.setBounds(0, 0, this.getWidth(), getCellHeight());
    }

    // EFFECTS: update grid width and height based on this panel size
    private void drawGrid() {
        gridPanel.setBounds(0,0,this.getWidth(), this.getHeight());
    }

    // EFFECTS: update time block bounds based on this panel size
    private void drawTimeBlocks() {
        for (TimeBlockLabel timeBlockLabel : timeBlockLabels) {
            int startPixel = timeBlockLabel.getStartPixel(getCellHeight());
            int endPixel = timeBlockLabel.getEndPixel(getCellHeight());
            timeBlockLabel.setBounds(0,
                    startPixel,
                    this.getWidth(),
                    endPixel - startPixel);
        }
    }

    // grid made of equally distanced horizontal lines and one vertical line to the left of the panel
    private static class GridPanel extends CalendarPanel {
        // EFFECTS: setup this panel with null layout and make it transparent
        public GridPanel() {
            setOpaque(false);
            setLayout(null);
        }

        // EFFECTS: draws the grid
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;
            drawGrid(g2d);
        }

        // EFFECTS: Draw NUMBER_OF_ROWS + 1 equally distant horizontal lines
        //          Draw one vertical line to the left of the panel
        private void drawGrid(Graphics2D g2d) {
            g2d.setColor(EMERALD);
            for (int i = 1; i < NUMBER_OF_ROWS; i++) {
                int y = getCellHeight() * i;
                g2d.drawLine(0, y, this.getWidth(), y);
            }
            g2d.drawLine(0,0, 0, this.getHeight());
            g2d.setColor(WET_ASPHALT);
            g2d.drawLine(0,0, 0, getCellHeight());
        }
    }
}
