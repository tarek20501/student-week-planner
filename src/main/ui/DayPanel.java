package ui;

import model.Day;
import model.TimeBlock;

import javax.swing.*;
import java.awt.*;
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
        this.setBackground(Color.green);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(COLUMN_WIDTH, COLUMN_HEIGHT));
        // Initialize Fields
        this.day = day;
        dayLabel = new JLabel(day.getLabel());
        timeBlockLabels = new ArrayList<>();
        gridPanel = new GridPanel();
        for (TimeBlock timeBlock : day.getTimeBlocks()) {
            timeBlockLabels.add(new TimeBlockLabel(timeBlock));
        }
    }

    public void paint(Graphics g) {
        drawTimeBlocks();
        drawGrid();
        drawDayLabel();
        super.paint(g);
    }

    private void drawDayLabel() {
        dayLabel.setBackground(Color.WHITE);
        dayLabel.setOpaque(true);
        dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dayLabel.setBounds(0, 0, this.getWidth(), getCellHeight(this));
        this.add(dayLabel);
    }

    private void drawGrid() {
        gridPanel.setBounds(0,0,this.getWidth(), this.getHeight());
        this.add(gridPanel);
    }

    private void drawTimeBlocks() {
        for (TimeBlockLabel timeBlockLabel : timeBlockLabels) {
            int startPixel = timeBlockLabel.getStartPixel(getCellHeight(this));
            int endPixel = timeBlockLabel.getEndPixel(getCellHeight(this));
            timeBlockLabel.setBounds(0,
                    startPixel,
                    this.getWidth(),
                    endPixel - startPixel);
            this.add(timeBlockLabel);
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
            for (int i = 1; i < NUMBER_OF_ROWS; i++) {
                int y = getCellHeight(this) * i;
                g2d.drawLine(0, y, this.getWidth(), y);
            }
            g2d.drawLine(0,0, 0, this.getHeight());
        }
    }
}
