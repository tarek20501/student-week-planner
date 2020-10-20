package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

public class TimeBlockTest {
    private TimeBlock timeBlock;

    @BeforeEach
    void setupTest() {
        timeBlock = new TimeBlock("11:00", "12:00");
        assertEquals("11:00", timeBlock.getStringStartTime());
        assertEquals("12:00", timeBlock.getStringEndTime());
    }

    @Test
    void testSettersGetters() {
        timeBlock.setStartTime("08:00");
        timeBlock.setEndTime("09:00");
        timeBlock.setLabel("test");

        assertEquals("08:00", timeBlock.getStringStartTime());
        assertEquals("09:00", timeBlock.getStringEndTime());
        assertEquals("test", timeBlock.getLabel());
    }
}
