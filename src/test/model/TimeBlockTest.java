package model;

import exception.InvalidTimeBlockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TimeBlockTest {
    private TimeBlock redTimeBlock;
    private TimeBlock yellowTimeBlock;

    @BeforeEach
    void setupTest() {
        try {
            redTimeBlock = new TimeBlock("11:00", "12:00", TimeBlock.Color.RED);
            yellowTimeBlock = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0), TimeBlock.Color.YELLOW);
        } catch (InvalidTimeBlockException e) {
            fail();
        }
        assertEquals("11:00", redTimeBlock.getStringStartTime());
        assertEquals("12:00", redTimeBlock.getStringEndTime());
    }

    @Test
    void testSettersGetters() {
        redTimeBlock.setStartTime("08:00");
        redTimeBlock.setEndTime("09:00");
        redTimeBlock.setLabel("test");

        assertEquals("08:00", redTimeBlock.getStringStartTime());
        assertEquals("09:00", redTimeBlock.getStringEndTime());
        assertEquals("test", redTimeBlock.getLabel());
        assertEquals(TimeBlock.Color.RED, redTimeBlock.getColor());
        assertEquals(TimeBlock.Color.YELLOW, yellowTimeBlock.getColor());
    }

    @Test
    void testInvalidTimeBlock() {
        try {
            new TimeBlock(LocalTime.of(11,0), LocalTime.of(10,0));
            fail("Expected an exception!!!");
        } catch (InvalidTimeBlockException e) {
            //pass
        }
    }

    @Test
    void testInvalidTime() {
        try {
            new TimeBlock("11", "12");
            fail("Expected an exception!!!");
        } catch (InvalidTimeBlockException e) {
            fail("Wrong exception thrown");
        } catch (DateTimeParseException e) {
            // pass
        }
    }
}
