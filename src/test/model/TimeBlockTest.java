package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

public class TimeBlockTest {
    @Test
    void testLocalTimeLocalTimeConstructor() {
        TimeBlock timeBlock = new TimeBlock(LocalTime.of(11,0), LocalTime.of(12,0));
        assertEquals(LocalTime.of(11,0), timeBlock.getStartTime());
        assertEquals(LocalTime.of(12,0), timeBlock.getEndTime());
    }

    @Test
    void testStringStringConstructor() {
        TimeBlock timeBlock = new TimeBlock("11:00", "12:00");
        assertEquals(LocalTime.of(11,0), timeBlock.getStartTime());
        assertEquals(LocalTime.of(12,0), timeBlock.getEndTime());
    }

    @Test
    void testSettersGetters() {
        TimeBlock timeBlock = new TimeBlock("11:00", "12:00");

        timeBlock.setStartTime("08:00");
        timeBlock.setEndTime("09:00");
        timeBlock.setLabel("test");

        assertEquals(LocalTime.of(8,0), timeBlock.getStartTime());
        assertEquals(LocalTime.of(9,0), timeBlock.getEndTime());
        assertEquals("08:00", timeBlock.getStringStartTime());
        assertEquals("09:00", timeBlock.getStringEndTime());
        assertEquals("test", timeBlock.getLabel());
    }
}
