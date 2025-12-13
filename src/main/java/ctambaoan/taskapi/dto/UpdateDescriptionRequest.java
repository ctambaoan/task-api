package ctambaoan.taskapi.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateDescriptionRequest(
        @NotBlank(message = "Description is required")
        String description) {

}
