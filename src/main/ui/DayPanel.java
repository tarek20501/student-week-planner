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

    public DayPanel(Day day) {
        // Setup this panel
        this.setBackground(Color.green);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(COLUMN_WIDTH, COLUMN_HEIGHT));
        // Initialize Fields
        this.day = day;
        dayLabel = new JLabel(day.getLabel());
        timeBlockLabels = new ArrayList<>();
        updateTimeBlockLabels();
        // Add components to the panel
        this.add(dayLabel);
        for (JLabel timeBlockLabel : timeBlockLabels) {
            this.add(timeBlockLabel);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        drawDayLabel();
        drawTimeBlocks();
        super.paint(g);
        drawGrid(g2d);
    }

    private void updateTimeBlockLabels() {
        for (TimeBlock timeBlock : day.getTimeBlocks()) {
            timeBlockLabels.add(new TimeBlockLabel(timeBlock));
        }
    }

    private void drawDayLabel() {
        dayLabel.setBackground(Color.WHITE);
        dayLabel.setOpaque(true);
        dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dayLabel.setBounds(0, 0, this.getWidth(), getCellHeight(this));
    }

    private void drawGrid(Graphics2D g2d) {
        for (int i = 1; i < NUMBER_OF_ROWS; i++) {
            int y = getCellHeight(this) * i;
            g2d.drawLine(0, y, this.getWidth(), y);
        }
        g2d.drawLine(0,0, 0, this.getHeight());
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
}
