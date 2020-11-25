package ui;

import model.Day;
import model.TimeBlock;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ui.WeekPlannerFrame.*;

// week panel connects day panels (columns) in a row forming a calendar
public class WeekPanel extends CalendarPanel implements MouseListener {
    private List<DayPanel> dayPanels;
    private volatile LocalTime pressTime;
    private volatile LocalTime releaseTime;
    private volatile DayPanel pressDay;
    private volatile DayPanel releaseDay;
    private volatile int mouseButton;
    private TimeBlock.Color timeBlockColor;

    // EFFECTS: setup this panel with grid layout
    public WeekPanel() {
        this.addMouseListener(this);
        this.setLayout(new GridLayout(1,0));
        this.setBackground(MIDNIGHT_BLUE);
        timeBlockColor = TimeBlock.Color.RED;
        initializeDayPanels();
    }

    // NOT USED
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    // MODIFIES: this
    // EFFECTS: get mouse coordinates and map them to day and time
    //          Get the pressed button and cash all the this data
    @Override
    public void mousePressed(MouseEvent e) {
        pressDay = pixel2Day(e.getX());
        pressTime = pixel2Time(trimY(e.getY()));
        mouseButton = e.getButton();
    }

    // MODIFIES: this
    // EFFECTS: get mouse coordinates and map them to day and time
    //          cash the data and call the function that handles mouse commands
    @Override
    public void mouseReleased(MouseEvent e) {
        releaseDay = pixel2Day(e.getX());
        releaseTime = pixel2Time(trimY(e.getY()));
        handleMouseCommands();
    }

    // NOT USED
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    // NOT USED
    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void setTimeBlockColor(TimeBlock.Color color) {
        timeBlockColor = color;
    }

    // EFFECTS: trim pixelY such that it stays between 9:00 and 23:59
    private int trimY(int pixelY) {
        int lowerLimit = getCellHeight();
        int upperLimit = getCellHeight() * NUMBER_OF_ROWS - 1;
        if (pixelY < lowerLimit) {
            return lowerLimit;
        } else if (pixelY > upperLimit) {
            return upperLimit;
        } else {
            return pixelY;
        }
    }

    // EFFECTS: based on pressTime, releaseTime, pressDay, releaseDay, mouseButton and timeBlockColor,
    //          call the appropriate function from the backend.
    private void handleMouseCommands() {
        if (pressDay == releaseDay) {
            if (pressTime.isBefore(releaseTime)) {
                pressDay.addTimeBlockLabel(pressTime, releaseTime, timeBlockColor);
            } else if (pressTime.isAfter(releaseTime)) {
                pressDay.addTimeBlockLabel(releaseTime, pressTime, timeBlockColor);
            } else if (pressTime.equals(releaseTime)) {
                TimeBlockLabel timeBlockLabel = pressDay.isThereTimeBlock(pressTime);
                if (timeBlockLabel != null) {
                    switch (mouseButton) {
                        case MouseEvent.BUTTON1:
                            String label = JOptionPane.showInputDialog("Enter label: ", timeBlockLabel.getLabel());
                            timeBlockLabel.setLabel(label);
                            break;
                        case MouseEvent.BUTTON3:
                            int response = JOptionPane.showConfirmDialog(null,"Do you want to delete?");
                            if (response == 0) {
                                pressDay.deleteTimeBlockLabel(timeBlockLabel);
                            }
                            break;
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads json array from file the user chooses
    // verifies that data are valid and assign it to the list of days
    public void loadWeekPlan(String file) {
        JsonReader jsonReader = new JsonReader(file);

        try {
            List<Day> loadedWeek = jsonReader.read();

            if (!verifyLoadedData(loadedWeek)) {
                return;
            }
            loadDayPanels(loadedWeek);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: saves week plan to a file the user can choose
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

    // EFFECTS: convert y coordinate in pixels to time
    private LocalTime pixel2Time(int y) {
        int cellHeight = getCellHeight();
        int hour = (y / cellHeight + (START_HOUR - 1)) % 24;
        int minute = (int)(((float)(y % cellHeight) / (float)cellHeight) * 59);
        return LocalTime.of(hour, minute);
    }

    // EFFECTS: convert x coordinate in pixels to day
    private DayPanel pixel2Day(int x) {
        return dayPanels.get(x / calculateColumnWidth());
    }

    // EFFECTS: calculates and returns the width of a column (day panel)
    private int calculateColumnWidth() {
        return this.getWidth() / 7;
    }

    // EFFECTS: initialize the week panel with empty day panels
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

    // EFFECTS: replace current day panels with new ones created from days in given week
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
