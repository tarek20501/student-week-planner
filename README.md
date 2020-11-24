# Student Week Planner

## What is this project?
This app has an interface that looks like a calendar showing one week. The students can use this
interface to mark their class times and any other commitments they might have (extracurricular 
activities) in red indicating their busy time. The rest of times will be automatically marked in green
indicating free time. The students can use this color-coded interface to help them plan when to do
their tasks (homework, exam prep and projects). They can allocate time blocks to their tasks by
drawing boxes in the green areas in their calendar. Those time boxes will be yellow to differentiate
them from the other colors. Inside those boxes they can type few words to identify their tasks.

## Why am I doing it?
This program is interesting to me because over the past few years I wished I had a program that is
as convenient to use as described above. I have tried Google Spreadsheet, Microsoft Excel and OneNote
to achieve the same idea. However, maintaining tables in all of these programs is a hassle. Using
a Calendar App such as Google Calendar is also a hassle because there are a number of settings that
you need to go through to set a calendar event all of which are unnecessary for the purpose I need.
I have not found an app that is simple and quick-to-use to plan when I am doing what.

## User Stories
- As a student, I want to add my busy time to my weekly planner by drawing red boxes.
- As a student, I want to add my to-do tasks to my weekly planner by drawing yellow boxes.
- As a student, I want to label the boxes with few words after drawing them and modify the labels later.
- As a student, I want to see the areas where I did not draw any box in green to visualize my free time.
- As a student, I want to delete the any box by selecting the box and pressing delete.
- As a student, I want to modify any box by changing the size of the box or dragging it across the week.
- As a student, I want to save my week plan to a file.
- As a student, I want to load my week plan from a file.

## Phase 4: Task 2
- I chose to test and design a class in your model package that is robust
- Robust class in model is Timeblock.
- The method that throws a checked exception is the constructor of TimeBlock.
- Tests cases when expection is expected and when it is not can be found in TimeBlockTest

## Phase 4: Task 3
- This software system is made of `WeekPanel` and `ToolsPanel` created by `WeekPlannerFrame`.
- `ToolsPanel` is associated to `WeekPanel` to call save and load methods from `WeekPanel`.
- `WeekPanel` has a dependency on `JsonReader` and `JsonWriter` to load and save persistent data.
- `WeekPanel` always has 7 `DayPanel`'s representing 7 days a week.
- Each of `DayPanel` and `TimeBlockLabel` wraps the functions of its back-end counterpart, 
`Day` and `TimeBlock` respectively, and it handles front-end related features.
