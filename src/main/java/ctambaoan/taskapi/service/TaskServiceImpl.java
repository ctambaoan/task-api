package ctambaoan.taskapi.service;

import ctambaoan.taskapi.domain.Task;
import ctambaoan.taskapi.domain.TaskStatus;
import ctambaoan.taskapi.exception.TaskNotFoundException;
import ctambaoan.taskapi.repository.TaskRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Override
    public Task create(String name, String description) {
        return repository.save(new Task(name, description));
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public Task findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public void markAsDone(Long id) {
        Task task = findById(id);
        task.markAsDone();
        repository.save(task);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public void updateDescription(Long id, String description) {
        Task task = findById(id);
        task.updateDescription(description);
        repository.save(task);
    }
}
