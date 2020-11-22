package exception;

public class InvalidTimeBlockException extends Exception {
    public InvalidTimeBlockException() {
        super("Start time is after end time!!!");
    }
}
