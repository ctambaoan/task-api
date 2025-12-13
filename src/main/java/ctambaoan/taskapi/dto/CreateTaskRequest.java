package ctambaoan.taskapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
        @NotBlank(message = "Task name is required")
        @Size(max = 50, message = "Task name should not exceed 50 characters")
        String name,
        String description) {
}
