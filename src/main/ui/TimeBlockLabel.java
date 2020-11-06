package ui;

import model.TimeBlock;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

import static ui.WeekPlannerFrame.*;

public class TimeBlockLabel extends JLabel {
    TimeBlock timeBlock;

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

    public int getStartPixel(int cellHeight) {
        return time2pixel(timeBlock.getStartTime(), cellHeight);
    }

    public int getEndPixel(int cellHeight) {
        return time2pixel(timeBlock.getEndTime(), cellHeight);
    }

    private int time2pixel(LocalTime time, int cellHeight) {
        int hour = time.getHour();
        int minute = time.getMinute();
        int pixelHour = (hour - (START_HOUR - 1)) * cellHeight;
        int pixelMinute = (int) ((minute / 60.0) * cellHeight);
        return pixelHour + pixelMinute;
    }
}
