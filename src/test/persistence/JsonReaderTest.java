package persistence;

import model.Day;
import model.TimeBlock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDay() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDay.json");
        try {
            List<Day> days = reader.read();
            Day day = days.get(0);
            assertEquals("TestEmptyDay", day.getLabel());
            assertEquals(0, day.getTimeBlocks().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderBusyDay() {
        JsonReader reader = new JsonReader("./data/testReaderBusyDay.json");
        try {
            List<Day> days = reader.read();
            Day day = days.get(0);
            assertEquals("TestBusyDay", day.getLabel());
            List<TimeBlock> timeBlocks = day.getTimeBlocks();
            assertEquals(2, timeBlocks.size());
            checkTimeBlock("work", "09:00", "17:00", timeBlocks.get(0));
            checkTimeBlock("dinner", "17:00", "18:00", timeBlocks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
