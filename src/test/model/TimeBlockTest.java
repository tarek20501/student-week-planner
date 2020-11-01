package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

public class TimeBlockTest {
    private TimeBlock redTimeBlock;
    private TimeBlock yellowTimeBlock;

    @BeforeEach
    void setupTest() {
        redTimeBlock = new TimeBlock("11:00", "12:00", TimeBlock.Color.RED);
        yellowTimeBlock = new TimeBlock(LocalTime.of(10,0), LocalTime.of(11,0), TimeBlock.Color.YELLOW);
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
}
