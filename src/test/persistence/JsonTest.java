package persistence;

import model.TimeBlock;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    protected void checkTimeBlock(String label, String start, String end, TimeBlock timeBlock) {
        assertEquals(label, timeBlock.getLabel());
        assertEquals(LocalTime.parse(start), timeBlock.getStartTime());
        assertEquals(LocalTime.parse(end), timeBlock.getEndTime());
    }
}
