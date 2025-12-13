package ctambaoan.taskapi.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

@Getter
public class Task {

    @Id
    private Long id;
    private final String name;
    private String description;
    private TaskStatus status;
    private final LocalDateTime created;

    public Task(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Task name cannot be blank");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Task name cannot exceed 50 characters");
        }

        this.name = name;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.created = LocalDateTime.now();
    }

    @PersistenceCreator
    Task(Long id, String name, String description, TaskStatus status, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.created = created;
    }

    public void markAsDone() {
        status = TaskStatus.DONE;
    }

    public void updateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
        this.description = description;
    }
}