package persistence;

import model.Day;
import model.TimeBlock;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// a reader that points to a JSON file from which list of days can be read
public class JsonReader {
    private final String source;

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: read week plan from a file and returns a list of days
    // throws IOException if an error occurs reading data from file
    public List<Day> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseDays(jsonArray);
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }


        return contentBuilder.toString();
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns list of days from json array
    private List<Day> parseDays(JSONArray jsonArray) {
        List<Day> days = new ArrayList<>();

        for (Object json : jsonArray) {
            JSONObject day = (JSONObject) json;
            days.add(parseDay(day));
        }

        return days;
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns a day from a json object
    private Day parseDay(JSONObject jsonObject) {
        String label = jsonObject.getString("label");
        Day day = new Day(label);

        JSONArray timeBlocks = jsonObject.getJSONArray("timeBlocks");
        for (Object json : timeBlocks) {
            JSONObject timeBlock = (JSONObject) json;
            day.addTimeBlock(parseTimeBlock(timeBlock));
        }

        return day;
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns a time block from a json object
    private TimeBlock parseTimeBlock(JSONObject jsonObject) {
        String label = jsonObject.getString("label");
        String  start = jsonObject.getString("startTime");
        String end = jsonObject.getString("endTime");

        TimeBlock timeBlock = new TimeBlock(start, end);
        timeBlock.setLabel(label);

        return timeBlock;
    }
}
