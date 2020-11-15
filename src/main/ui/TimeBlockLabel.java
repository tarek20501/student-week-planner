package ui;

import model.TimeBlock;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

import static ui.WeekPlannerFrame.*;

// Colored label visualizing time block
public class TimeBlockLabel extends JLabel {
    TimeBlock timeBlock;

    // EFFECT: sets color, background and border of this label
    //          the color is set based on the color of field timeBlock in the back end
    public TimeBlockLabel(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
        this.setText(timeBlock.getLabel());
        this.setHorizontalAlignment(SwingConstants.CENTER);

        Color background;
        Color border;
        switch (timeBlock.getColor()) {
            case RED:
                background = POMEGRANATE;
                border = ALIZARIN;
                break;
            case YELLOW:
                background = ORANGE;
                border = SUN_FLOWER;
                break;
            default:
                background = Color.GRAY;
                border = Color.BLACK;
        }
        this.setBackground(background);
        this.setBorder(BorderFactory.createMatteBorder(10,1, 10, 0, border));
        this.setOpaque(true);
    }

    // EFFECTS: return the Y pixel where the label starts based on start time of field timeBlock
    public int getStartPixel(int cellHeight) {
        return time2pixel(timeBlock.getStartTime(), cellHeight);
    }

    // EFFECTS: return the Y pixel where the label ends based on end time of field timeBlock
    public int getEndPixel(int cellHeight) {
        return time2pixel(timeBlock.getEndTime(), cellHeight);
    }

    public TimeBlock getTimeBlock() {
        return timeBlock;
    }

    // EFFECTS: wraps TimeBlock.setLabel in the back end
    public void setLabel(String label) {
        timeBlock.setLabel(label);
        this.setText(label);
        this.repaint();
    }

    // EFFECTS: return the label of the field timeBlock
    public String getLabel() {
        return timeBlock.getLabel();
    }

    // EFFECTS: return the Y pixel equivalent of given time
    private int time2pixel(LocalTime time, int cellHeight) {
        int hour = time.getHour();
        int minute = time.getMinute();
        int pixelHour = (hour - (START_HOUR - 1)) * cellHeight;
        int pixelMinute = (int) ((minute / 60.0) * cellHeight);
        return pixelHour + pixelMinute;
    }
}
