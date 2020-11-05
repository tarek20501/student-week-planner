package ui;

import javax.swing.*;
import java.awt.*;

public class WeekPlannerFrame extends JFrame {
    public static int COLUMN_WIDTH = 150;
    public static int COLUMN_HEIGHT = 800;
    public static int NUMBER_OF_ROWS = 16;
    public static int START_HOUR = 9;

    public WeekPlannerFrame() {
        this.setTitle("Week Planner");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(true);

        this.add(BorderLayout.CENTER, new WeekPanel());
        this.add(BorderLayout.WEST, new TimeLegend());
        this.pack();
        this.setVisible(true);
    }

    public static int getCellHeight(JPanel panel) {
        return panel.getHeight() / NUMBER_OF_ROWS;
    }

    private static class TimeLegend extends JPanel {

        public TimeLegend() {
            this.setLayout(null);
            this.setPreferredSize(new Dimension(35, COLUMN_HEIGHT));
        }

        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;

            int cellHeight = getCellHeight(this);
            int startHour = START_HOUR;
            for (int i = 1; i < NUMBER_OF_ROWS; i++) {
                int y = cellHeight * i + 4;
                g2d.drawString(startHour + ":00", 1, y);
                startHour++;
            }
        }
    }
}
