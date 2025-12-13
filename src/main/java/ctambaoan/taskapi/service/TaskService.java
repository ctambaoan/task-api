package ctambaoan.taskapi.service;

import ctambaoan.taskapi.domain.Task;
import ctambaoan.taskapi.domain.TaskStatus;
import java.util.List;

public interface TaskService {

    Task create(String name, String description);

    List<Task> findAll();

    List<Task> findByStatus(TaskStatus status);

    Task findById(Long id);

    void markAsDone(Long id);

    void delete(Long id);

    void updateDescription(Long id, String description);
}