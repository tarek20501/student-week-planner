package ui;

import model.Day;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ui.WeekPlannerFrame.*;

public class WeekPanel extends JPanel implements MouseListener {
    private List<DayPanel> dayPanels;
    private volatile LocalTime pressTime;
    private volatile LocalTime releaseTime;
    private volatile DayPanel pressDay;
    private volatile DayPanel releaseDay;

    public WeekPanel() {
        this.addMouseListener(this);
        this.setLayout(new GridLayout(1,0));
        initializeDayPanels();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressDay = pixel2Day(e.getX());
        pressTime = pixel2Time(e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        releaseDay = pixel2Day(e.getX());
        releaseTime = pixel2Time(e.getY());
        handleMouseCommands();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void handleMouseCommands() {
        if (pressDay == releaseDay) {
            if (pressTime.isBefore(releaseTime)) {
                pressDay.addTimeBlockLabel(pressTime, releaseTime);
            } else if (pressTime.isAfter(releaseTime)) {
                pressDay.addTimeBlockLabel(releaseTime, pressTime);
            }
//            else if (pressTime.equals(releaseTime)) {
//                // if a time block is hit then edit label (left click) delete (right click)
//            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads json array from file
    // verifies that data are valid and assign it to the list of days
    public void loadWeekPlan(String file) {
        JsonReader jsonReader = new JsonReader(file);

        try {
            List<Day> loadedWeek = jsonReader.read();

            if (!verifyLoadedData(loadedWeek)) {
                return;
            }
            loadDayPanels(loadedWeek);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves week plan to ./data/week.json
    public void saveWeekPlan(String file) {
        JsonWriter writer = new JsonWriter(file);

        List<Day> week = new ArrayList<>();
        for (DayPanel dayPanel : dayPanels) {
            week.add(dayPanel.getDay());
        }

        try {
            writer.open();
            writer.write(week);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: Checks if loadedWeek list has seven days and the labels of the days are correct
    // and in the right order (Monday to Sunday)
    private boolean verifyLoadedData(List<Day> loadedWeek) {
        if (loadedWeek.size() != 7) {
            return false;
        }

        for (int i = 0; i < 7; i++) {
            if (!dayPanels.get(i).getDay().getLabel().equals(loadedWeek.get(i).getLabel())) {
                return false;
            }
        }
        return true;
    }

    private LocalTime pixel2Time(int y) {
        int cellHeight = getCellHeight(this);
        int hour = (y / cellHeight + (START_HOUR - 1)) % 24;
        int minute = (int)(((float)(y % cellHeight) / (float)cellHeight) * 59);
        return LocalTime.of(hour, minute);
    }

    private DayPanel pixel2Day(int x) {
        return dayPanels.get(x / calculateColumnWidth());
    }

    private int calculateColumnWidth() {
        return this.getWidth() / 7;
    }

    private void initializeDayPanels() {
        this.dayPanels = new ArrayList<>();
        List<Day> week = new ArrayList<>();
        week.add(new Day("Monday"));
        week.add(new Day("Tuesday"));
        week.add(new Day("Wednesday"));
        week.add(new Day("Thursday"));
        week.add(new Day("Friday"));
        week.add(new Day("Saturday"));
        week.add(new Day("Sunday"));
        for (Day day : week) {
            this.dayPanels.add(new DayPanel(day));
        }
        for (DayPanel dayPanel : dayPanels) {
            this.add(dayPanel);
        }
    }

    private void loadDayPanels(List<Day> week) {
        this.removeAll();
        this.dayPanels = new ArrayList<>();
        for (Day day : week) {
            this.dayPanels.add(new DayPanel(day));
        }
        for (DayPanel dayPanel : dayPanels) {
            this.add(dayPanel);
        }
        this.repaint();
        this.revalidate();
    }
}
