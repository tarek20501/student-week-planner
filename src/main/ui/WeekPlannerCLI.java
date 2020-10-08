package ui;

import model.Day;
import model.Time;
import model.TimeBlock;

public class WeekPlannerCLI {
    public static void main(String[] args) {
        System.out.println("Hello WeekPlanner");

        Day sat = new Day("Saturday");
        Day sun = new Day("Sunday");

        TimeBlock cpsc121 = new TimeBlock(new Time("09:30"), new Time("11:00"));
        TimeBlock cpsc210 = new TimeBlock(new Time("11:00"), new Time("12:00"));

        sat.addTimeBlock(cpsc121);
        sun.addTimeBlock(cpsc210);

        sat.deleteTimeBlock(cpsc121);

        sun.resizeTimeBlock(cpsc210, new Time("10:00"), new Time("13:00"));

        sun.moveTimeBlock(cpsc210, sat, new Time("10:00"), new Time("12:00"));
    }
}
