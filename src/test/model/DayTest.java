package model;

import exception.InvalidTimeBlockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {
    private Day testDay;

    @BeforeEach
    public void testSetup() {
        testDay = new Day("TestDay");
        assertEquals("TestDay", testDay.getLabel());
    }

    @Test
    public void testAddTimeBlockEmptyDay() {
        TimeBlock timeBlock = null;
        try {
            timeBlock = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }
        assertTrue(testDay.addTimeBlock(timeBlock));
        assertEquals(1, testDay.getTimeBlocks().size());
        assertTrue(testDay.getTimeBlocks().contains(timeBlock));
    }

    @Test
    public void testAddTimeBlockNoConflictGap() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        TimeBlock timeBlock3 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
            timeBlock2 = new TimeBlock(LocalTime.of(13,0), LocalTime.of(14,0));
            timeBlock3 = new TimeBlock(LocalTime.of(8,0), LocalTime.of(9,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertTrue(testDay.addTimeBlock(timeBlock2));
        assertTrue(testDay.addTimeBlock(timeBlock3));
        assertEquals(3, testDay.getTimeBlocks().size());
    }

    @Test
    public void testAddTimeBlockNoConflictNoGap() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        TimeBlock timeBlock3 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
            timeBlock2 = new TimeBlock(LocalTime.of(11,0), LocalTime.of(12,0));
            timeBlock3 = new TimeBlock(LocalTime.of(9,0), LocalTime.of(10,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertTrue(testDay.addTimeBlock(timeBlock2));
        assertTrue(testDay.addTimeBlock(timeBlock3));
        assertEquals(3, testDay.getTimeBlocks().size());
    }

    @Test
    public void testAddTimeBlockConflictStartInAnother() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        TimeBlock timeBlock3 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
            timeBlock2 = new TimeBlock(LocalTime.of(10,30), LocalTime.of(12,0));
            timeBlock3 = new TimeBlock(LocalTime.of(10,30), LocalTime.of(11,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertFalse(testDay.addTimeBlock(timeBlock2));
        assertFalse(testDay.addTimeBlock(timeBlock3));
        assertEquals(1, testDay.getTimeBlocks().size());
    }

    @Test
    public void testAddTimeBlockConflictEndInAnother() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        TimeBlock timeBlock3 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
            timeBlock2 = new TimeBlock(LocalTime.of(9,0), LocalTime.of(10,30));
            timeBlock3 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(10,30));

        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertFalse(testDay.addTimeBlock(timeBlock2));
        assertFalse(testDay.addTimeBlock(timeBlock3));
        assertEquals(1, testDay.getTimeBlocks().size());
    }

    @Test
    public void testAddTimeBlockConflictStartAndEndInAnother() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(9,0), LocalTime.of(12,0));
            timeBlock2 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertFalse(testDay.addTimeBlock(timeBlock2));
        assertEquals(1, testDay.getTimeBlocks().size());
    }

    @Test
    public void testAddTimeBlockConflictSameStartAndEnd() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(9,0), LocalTime.of(12,0));
            timeBlock2 = new TimeBlock(LocalTime.of(9,0), LocalTime.of(12,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertFalse(testDay.addTimeBlock(timeBlock2));
        assertEquals(1, testDay.getTimeBlocks().size());
    }

    @Test
    public void testAddTimeBlockConflictSameStart() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(9,0), LocalTime.of(12,0));
            timeBlock2 = new TimeBlock(LocalTime.of(9,0), LocalTime.of(13,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertFalse(testDay.addTimeBlock(timeBlock2));
        assertEquals(1, testDay.getTimeBlocks().size());
    }

    @Test
    public void testAddTimeBlockConflictSameEnd() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(9,0), LocalTime.of(12,0));
            timeBlock2 = new TimeBlock(LocalTime.of(8,0), LocalTime.of(12,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertFalse(testDay.addTimeBlock(timeBlock2));
        assertEquals(1, testDay.getTimeBlocks().size());
    }

    @Test
    public void testAddTimeBlockConflictAnotherInside() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
            timeBlock2 = new TimeBlock(LocalTime.of(9,0), LocalTime.of(12,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertFalse(testDay.addTimeBlock(timeBlock2));
        assertEquals(1, testDay.getTimeBlocks().size());
    }

    @Test
    public void testDeleteTimeBlockExist() {
        TimeBlock timeBlock = null;
        try {
            timeBlock = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }
        assertTrue(testDay.addTimeBlock(timeBlock));
        assertEquals(1, testDay.getTimeBlocks().size());
        assertTrue(testDay.deleteTimeBlock(timeBlock));
        assertEquals(0, testDay.getTimeBlocks().size());
    }

    @Test
    public void testDeleteTimeBlockNotExist() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
            timeBlock2 = new TimeBlock(LocalTime.of(12,0), LocalTime.of(13,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertEquals(1, testDay.getTimeBlocks().size());
        assertFalse(testDay.deleteTimeBlock(timeBlock2));
        assertEquals(1, testDay.getTimeBlocks().size());
    }

    @Test
    public void testModifyTimeBlockSameDayNoConflict() {
        TimeBlock timeBlock = null;
        try {
            timeBlock = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }
        assertTrue(testDay.addTimeBlock(timeBlock));
        assertEquals(1, testDay.getTimeBlocks().size());

        LocalTime expectedStart = LocalTime.of(9,0);
        LocalTime expectedEnd = LocalTime.of(13,0);

        assertTrue(testDay.modifyTimeBlock(timeBlock, testDay, expectedStart, expectedEnd));
        LocalTime actualStart = testDay.getTimeBlocks().get(0).getStartTime();
        LocalTime actualEnd = testDay.getTimeBlocks().get(0).getEndTime();

        assertEquals(expectedStart, actualStart);
        assertEquals(expectedEnd, actualEnd);
    }

    @Test
    public void testModifyTimeBlockSameDayConflict() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
            timeBlock2 = new TimeBlock(LocalTime.of(11,0), LocalTime.of(12,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertTrue(testDay.addTimeBlock(timeBlock2));
        assertEquals(2, testDay.getTimeBlocks().size());

        assertFalse(testDay.modifyTimeBlock(timeBlock1, testDay, LocalTime.of(10,0), LocalTime.of(12,0)));
        LocalTime actualStart = testDay.getTimeBlocks().get(0).getStartTime();
        LocalTime actualEnd = testDay.getTimeBlocks().get(0).getEndTime();
        LocalTime expectedStart = LocalTime.of(10,0);
        LocalTime expectedEnd = LocalTime.of(11,0);
        assertEquals(expectedStart, actualStart);
        assertEquals(expectedEnd, actualEnd);
    }

    @Test
    public void testModifyTimeBlockAnotherDayNoConflict() {
        TimeBlock timeBlock = null;
        try {
            timeBlock = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }
        Day anotherDay = new Day("AnotherDay");

        assertTrue(testDay.addTimeBlock(timeBlock));
        assertEquals(1, testDay.getTimeBlocks().size());

        LocalTime expectedStart = LocalTime.of(9,0);
        LocalTime expectedEnd = LocalTime.of(13,0);

        assertTrue(testDay.modifyTimeBlock(timeBlock, anotherDay, expectedStart, expectedEnd));

        assertEquals(0, testDay.getTimeBlocks().size());
        assertEquals(1, anotherDay.getTimeBlocks().size());
        LocalTime actualStart = anotherDay.getTimeBlocks().get(0).getStartTime();
        LocalTime actualEnd = anotherDay.getTimeBlocks().get(0).getEndTime();

        assertEquals(expectedStart, actualStart);
        assertEquals(expectedEnd, actualEnd);
    }

    @Test
    public void testModifyTimeBlockAnotherDayConflict() {
        TimeBlock timeBlock1 = null;
        TimeBlock timeBlock2 = null;
        try {
            timeBlock1 = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0));
            timeBlock2 = new TimeBlock(LocalTime.of(11,0), LocalTime.of(12,0));
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect an exception");
        }

        Day anotherDay = new Day("AnotherDay");

        assertTrue(testDay.addTimeBlock(timeBlock1));
        assertTrue(anotherDay.addTimeBlock(timeBlock2));
        assertEquals(1, testDay.getTimeBlocks().size());
        assertEquals(1, anotherDay.getTimeBlocks().size());

        assertFalse(testDay.modifyTimeBlock(timeBlock1, anotherDay, LocalTime.of(10,0), LocalTime.of(12,0)));
        assertEquals(1, testDay.getTimeBlocks().size());
        assertEquals(1, anotherDay.getTimeBlocks().size());

        LocalTime actualStart = testDay.getTimeBlocks().get(0).getStartTime();
        LocalTime actualEnd = testDay.getTimeBlocks().get(0).getEndTime();
        LocalTime expectedStart = LocalTime.of(10,0);
        LocalTime expectedEnd = LocalTime.of(11,0);
        assertEquals(expectedStart, actualStart);
        assertEquals(expectedEnd, actualEnd);
    }
}
