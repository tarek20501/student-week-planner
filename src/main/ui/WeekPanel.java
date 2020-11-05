package ui;

import model.Day;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeekPanel extends JPanel {
    private List<Day> week;

    public WeekPanel() {
        week = new ArrayList<>();
        week.add(new Day("Monday"));
        week.add(new Day("Tuesday"));
        week.add(new Day("Wednesday"));
        week.add(new Day("Thursday"));
        week.add(new Day("Friday"));
        week.add(new Day("Saturday"));
        week.add(new Day("Sunday"));
        loadWeekPlan();

        this.setLayout(new GridLayout(1,0));

        for (Day day : week) {
            this.add(new DayPanel(day));
        }
    }

    private void loadWeekPlan() {
        JsonReader jsonReader = new JsonReader("./data/week.json");
        try {
            week = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
