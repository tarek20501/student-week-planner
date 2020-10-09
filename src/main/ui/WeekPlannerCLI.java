package ui;

import model.Day;
import model.TimeBlock;

import java.time.LocalTime;

public class WeekPlannerCLI {
    public static void main(String[] args) {
        System.out.println("Hello WeekPlanner");

        Day sat = new Day("Saturday");
        Day sun = new Day("Sunday");

        TimeBlock cpsc121 = new TimeBlock(LocalTime.of(9,30), LocalTime.of(11,0));
        TimeBlock cpsc210 = new TimeBlock(LocalTime.of(11,0), LocalTime.of(12,0));

        sat.addTimeBlock(cpsc121);
        sun.addTimeBlock(cpsc210);

        sat.deleteTimeBlock(cpsc121);

        sun.resizeTimeBlock(cpsc210, LocalTime.of(10,0), LocalTime.of(13,0));

        sun.moveTimeBlock(cpsc210, sat, LocalTime.of(11,0), LocalTime.of(12,0));
    }
}
