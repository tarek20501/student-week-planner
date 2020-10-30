package persistence;

import org.json.JSONObject;

// Adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// an interface that writable classes (whose data can be persistent) must implement
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
