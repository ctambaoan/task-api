package ctambaoan.taskapi.controller;

import ctambaoan.taskapi.domain.Task;
import ctambaoan.taskapi.domain.TaskStatus;
import ctambaoan.taskapi.dto.CreateTaskRequest;
import ctambaoan.taskapi.dto.TaskResponse;
import ctambaoan.taskapi.dto.UpdateDescriptionRequest;
import ctambaoan.taskapi.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        Task task = service.create(request.name(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TaskResponse.map(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable Long id) {
        Task task = service.findById(id);
        return ResponseEntity.ok(TaskResponse.map(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAllOrByStatus(
            @RequestParam(required = false) TaskStatus status) {

        List<Task> tasks = status == null ?
                service.findAll() :
                service.findByStatus(status);
        return ResponseEntity.ok(tasks.stream()
                .map(TaskResponse::map)
                .toList());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Object> completeTask(@PathVariable Long id) {
        service.markAsDone(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDescription(@PathVariable Long id,
            @Valid @RequestBody UpdateDescriptionRequest request) {
        service.updateDescription(id, request.description());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
