package persistence;

import exception.InvalidTimeBlockException;
import model.Day;
import model.TimeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    private List<Day> testWeek;

    @BeforeEach
    void setupTest() {
        testWeek = new ArrayList<>();
        testWeek.add(new Day("TestDay"));
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDay() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDay.json");
            writer.open();
            writer.write(testWeek);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDay.json");
            testWeek = reader.read();
            assertEquals("TestDay", testWeek.get(0).getLabel());
            assertEquals(0, testWeek.get(0).getTimeBlocks().size());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterBusyDay() {
        try {
            Day testDay = testWeek.get(0);
            TimeBlock testBlock = null;
            try {
                testBlock = new TimeBlock("10:00", "11:00", TimeBlock.Color.RED);
                testBlock.setLabel("TestBlock");
            } catch (InvalidTimeBlockException e) {
                fail("Did not expect an exception");
            }

            testDay.addTimeBlock(testBlock);

            JsonWriter writer = new JsonWriter("./data/testWriterBusyDay.json");
            writer.open();
            writer.write(testWeek);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterBusyDay.json");
            testWeek = reader.read();
            assertEquals("TestDay", testWeek.get(0).getLabel());
            List<TimeBlock> timeBlocks = testWeek.get(0).getTimeBlocks();
            assertEquals(1, timeBlocks.size());
            checkTimeBlock("TestBlock", "10:00", "11:00", TimeBlock.Color.RED, timeBlocks.get(0));
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }
}
