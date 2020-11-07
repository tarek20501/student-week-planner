package ui;

import model.Day;
import model.TimeBlock;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ui.WeekPlannerFrame.*;

public class DayPanel extends JPanel {
    private final Day day;
    private final JLabel dayLabel;
    private final List<TimeBlockLabel> timeBlockLabels;
    private final GridPanel gridPanel;

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

    public void paint(Graphics g) {
        drawTimeBlocks();
        drawGrid();
        drawDayLabel();
        super.paint(g);
    }

    public Day getDay() {
        return day;
    }

    public void addTimeBlockLabel(LocalTime start, LocalTime end) {
        TimeBlock timeBlock = new TimeBlock(start, end);
        if (day.addTimeBlock(timeBlock)) {
            TimeBlockLabel timeBlockLabel = new TimeBlockLabel(timeBlock);
            timeBlockLabels.add(timeBlockLabel);
            this.add(timeBlockLabel,0);
            this.repaint();
        }
    }

    public void deleteTimeBlockLabel(TimeBlockLabel timeBlockLabel) {
        this.remove(timeBlockLabel);
        day.deleteTimeBlock(timeBlockLabel.getTimeBlock());
        timeBlockLabels.remove(timeBlockLabel);
        this.repaint();
    }

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

    private void initializeTimeBlockLabels() {
        for (TimeBlock timeBlock : day.getTimeBlocks()) {
            timeBlockLabels.add(new TimeBlockLabel(timeBlock));
        }
        for (TimeBlockLabel timeBlockLabel : timeBlockLabels) {
            this.add(timeBlockLabel);
        }
    }

    private void drawDayLabel() {
        dayLabel.setBackground(MIDNIGHT_BLUE);
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setOpaque(true);
        dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dayLabel.setBounds(0, 0, this.getWidth(), getCellHeight(this));
    }

    private void drawGrid() {
        gridPanel.setBounds(0,0,this.getWidth(), this.getHeight());
    }

    private void drawTimeBlocks() {
        for (TimeBlockLabel timeBlockLabel : timeBlockLabels) {
            int startPixel = timeBlockLabel.getStartPixel(getCellHeight(this));
            int endPixel = timeBlockLabel.getEndPixel(getCellHeight(this));
            timeBlockLabel.setBounds(0,
                    startPixel,
                    this.getWidth(),
                    endPixel - startPixel);
        }
    }

    private static class GridPanel extends JPanel {
        public GridPanel() {
            setOpaque(false);
            setLayout(null);
        }

        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;
            drawGrid(g2d);
        }

        private void drawGrid(Graphics2D g2d) {
            g2d.setColor(EMERALD);
            for (int i = 1; i < NUMBER_OF_ROWS; i++) {
                int y = getCellHeight(this) * i;
                g2d.drawLine(0, y, this.getWidth(), y);
            }
            g2d.drawLine(0,0, 0, this.getHeight());
            g2d.setColor(WET_ASPHALT);
            g2d.drawLine(0,0, 0, getCellHeight(this));
        }
    }
}
