package ui;

import javax.swing.*;
import java.awt.*;

// initialize all gui components and add them to the window
public class WeekPlannerFrame extends JFrame {
    public static int COLUMN_WIDTH = 150;
    public static int COLUMN_HEIGHT = 800;
    public static int NUMBER_OF_ROWS = 16;
    public static int START_HOUR = 9;
    // Color palette source: https://flatuicolors.com/palette/defo
    public static Color MIDNIGHT_BLUE = new Color(0x2c3e50);
    public static Color WET_ASPHALT = new Color(0x34495e);
    public static Color NEPHRITIS = new Color(0x27ae60);
    public static Color EMERALD = new Color(0x2ecc71);
    public static Color POMEGRANATE = new Color(0xc0392b);
    public static Color ALIZARIN = new Color(0xe74c3c);
    public static Color ORANGE = new Color(0xf39c12);
    public static Color SUN_FLOWER = new Color(0xf1c40f);

    // EFFECTS: setup this frame with border layout
    //          instantiate week panel snd put it in center
    //          instantiate time panel and put it in the west
    //          instantiate tools panel and out it in the north
    public WeekPlannerFrame() {
        this.setTitle("Week Planner");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(true);
        this.setBackground(MIDNIGHT_BLUE);

        WeekPanel weekPanel = new WeekPanel();
        this.add(BorderLayout.CENTER, weekPanel);
        this.add(BorderLayout.WEST, new TimePanel());
        this.add(BorderLayout.NORTH, new ToolsPanel(weekPanel));
        this.pack();
        this.setVisible(true);
    }

    // REQUIRES: panel has height equal to week panel's height
    // EFFECTS: returns the height of a cell in the calendar
    public static int getCellHeight(JPanel panel) {
        return panel.getHeight() / NUMBER_OF_ROWS;
    }

    // a vertical panel with hours starting from 9:00 till 23:00
    private static class TimePanel extends JPanel {

        // EFFECTS: setup the panel with NULL layout
        public TimePanel() {
            this.setLayout(null);
            this.setPreferredSize(new Dimension(35, COLUMN_HEIGHT));
            this.setBackground(MIDNIGHT_BLUE);
            this.setOpaque(true);
        }

        // EFFECTS: draw hours vertically starting from 9:00 on top until 23:00 on the bottom
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;

            int cellHeight = getCellHeight(this);
            int startHour = START_HOUR;
            g2d.setColor(Color.WHITE);
            for (int i = 1; i < NUMBER_OF_ROWS; i++) {
                int y = cellHeight * i + 4;
                g2d.drawString(startHour + ":00", 1, y);
                startHour++;
            }
        }
    }
}
