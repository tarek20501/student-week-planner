package ui;

import model.Day;
import model.TimeBlock;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeekPlannerCLI {
    private List<Day> week;
    private boolean stillRunning;
    private Scanner userInput;

    public WeekPlannerCLI() {
        week = new ArrayList<>();
        week.add(new Day("Monday"));
        week.add(new Day("Tuesday"));
        week.add(new Day("Wednesday"));
        week.add(new Day("Thursday"));
        week.add(new Day("Friday"));
        week.add(new Day("Saturday"));
        week.add(new Day("Sunday"));
        stillRunning = true;
        userInput = new Scanner(System.in);
        System.out.println("Hello WeekPlanner");
        printHelp();
        run();
    }

    private void run() {
        while (stillRunning) {
            System.out.print("Enter the character of what you want to do: ");
            String choice = userInput.next();
            switch (choice) {
                case "s":
                    showTimeBlocksInWeek();
                    break;
                case "a":
                    addTimeBlock();
                    break;
                case "m":
                    modifyTimeBlock();
                    break;
                case "d":
                    deleteTimeBlock();
                    break;
                case "x":
                    stillRunning = false;
                    break;
                default:
                    printHelp();
            }
        }
    }

    private void addTimeBlock() {
        Day dayChosen = getDayFromUser("to add to");
        TimeBlock timeBlock = getTimeBlockFromUser("add");
        if (dayChosen.addTimeBlock(timeBlock)) {
            System.out.println(timeBlock.getLabel() + " was added successfully.");
        } else {
            System.out.println(timeBlock.getLabel() + " cannot be added due to time conflicts.");
        }
    }

    private void modifyTimeBlock() {
        showTimeBlocksInWeek();
        Day chosenDay = getDayFromUser("to modify from");
        showTimeBlocksInDay(chosenDay);
        int timeBlockIndex = getIntInRangeFromUser(
                "Enter the number of time block to modify",
                0,
                chosenDay.getTimeBlocks().size() - 1);
        TimeBlock timeBlockToModify = chosenDay.getTimeBlocks().get(timeBlockIndex);
        Day targetDay = getDayFromUser("to move to");
        TimeBlock newTimeBlock = getTimeBlockFromUser("modify");
        boolean success = chosenDay.modifyTimeBlock(
                timeBlockToModify,
                targetDay,
                newTimeBlock.getStartTime(),
                newTimeBlock.getEndTime());
        if (success) {
            System.out.println("New settings applied successfully.");
        } else {
            System.out.println("Could not apply new settings due to time conflicts");
        }
    }

    private void deleteTimeBlock() {
        showTimeBlocksInWeek();
        Day chosenDay = getDayFromUser("to delete from");
        showTimeBlocksInDay(chosenDay);
        int timeBlockIndex = getIntInRangeFromUser(
                "Enter the number of time block to delete",
                0,
                chosenDay.getTimeBlocks().size() - 1);
        TimeBlock timeBlockToDelete = chosenDay.getTimeBlocks().get(timeBlockIndex);
        if (chosenDay.deleteTimeBlock(timeBlockToDelete)) {
            System.out.println(timeBlockToDelete.getLabel() + " was deleted.");
        } else {
            System.out.println("ERROR: " + timeBlockToDelete + " does not exist");
        }
    }

    private Day getDayFromUser(String purpose) {
        System.out.println("0.Mon 1.Tue 2.Wed 3.Thu 4.Fri 5.Sat 6.Sun");
        int choice = getIntInRangeFromUser(
                "Enter the number of the day " + purpose,
                0,
                6);
        return week.get(choice);
    }

    private TimeBlock getTimeBlockFromUser(String purpose) {
        System.out.print("Enter the label of the time block to " + purpose + ": ");
        String label = userInput.next();
        LocalTime startTime = getTimeFromUser("Enter the start time of " + label);
        LocalTime endTime = getTimeFromUser("Enter the end time of " + label);
        TimeBlock timeBlock = new TimeBlock(startTime, endTime);
        timeBlock.setLabel(label);
        return timeBlock;
    }

    private void showTimeBlocksInWeek() {
        for (Day day : week) {
            System.out.println(day.getLabel());
            for (TimeBlock timeBlock : day.getTimeBlocks()) {
                System.out.print("\t");
                printTimeBlock(timeBlock);
            }
        }
    }

    private void showTimeBlocksInDay(Day day) {
        int i = 0;
        for (TimeBlock timeBlock : day.getTimeBlocks()) {
            System.out.print(i + ". ");
            printTimeBlock(timeBlock);
            i++;
        }
    }

    private void printTimeBlock(TimeBlock timeBlock) {
        System.out.print(timeBlock.getLabel() + ": ");
        System.out.print(timeBlock.getStartTime().toString() + " - ");
        System.out.println(timeBlock.getEndTime().toString());
    }

    private void printHelp() {
        System.out.println("s: Show time blocks in your week");
        System.out.println("a: Add a time block to your week");
        System.out.println("m: Modify a time block in your week");
        System.out.println("d: Delete a time block in your week");
        System.out.println("x: Exit the program");
    }

    private int getIntInRangeFromUser(String prompt, int start, int end) {
        while (true) {
            System.out.print(prompt + ": ");
            try {
                int choice = userInput.nextInt();
                if (choice >= start && choice <= end) {
                    return choice;
                } else {
                    System.out.println("ERROR: you entered an out-of-range integer.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: you entered a non-integer.");
                userInput.nextLine();
            }
        }
    }

    private LocalTime getTimeFromUser(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String timeStr = userInput.next();
            try {
                return LocalTime.parse(timeStr);
            } catch (Exception e) {
                System.out.println("ERROR: You must enter in this format HH:MM (HH:00-23, MM:00-59)");
            }
        }
    }
}
