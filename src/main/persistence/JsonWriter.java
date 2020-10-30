package persistence;

import model.Day;
import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

// a writer that points to a JSON file to which list of days can be written
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes JSON representation of list of days to file
    public void write(List<Day> week) {
        JSONArray jsonArray = new JSONArray();
        for (Day day : week) {
            jsonArray.put(day.toJson());
        }
        saveToFile(jsonArray.toString(TAB));
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
