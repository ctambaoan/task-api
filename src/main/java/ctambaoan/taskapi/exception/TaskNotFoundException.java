package ctambaoan.taskapi.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Task with ID: %d does not exist in our records".formatted(id));
    }
}
