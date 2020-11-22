package persistence;

import exception.InvalidTimeBlockException;
import model.Day;
import model.TimeBlock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect InvalidTimeBlockException");
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
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect InvalidTimeBlockException");
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
            checkTimeBlock("work", "09:00", "17:00", TimeBlock.Color.RED, timeBlocks.get(0));
            checkTimeBlock("dinner", "17:00", "18:00", TimeBlock.Color.YELLOW, timeBlocks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidTimeBlockException e) {
            fail("Did not expect InvalidTimeBlockException");
        }
    }

    @Test
    void testInvalidTimeBlock() {
        JsonReader reader = new JsonReader("./data/testInvalidTimeBlock.json");
        try {
            reader.read();
            fail();
        } catch (InvalidTimeBlockException e) {
            //pass
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
    }
}
