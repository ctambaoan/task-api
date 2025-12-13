package ctambaoan.taskapi.repository;

import ctambaoan.taskapi.domain.Task;
import ctambaoan.taskapi.domain.TaskStatus;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface TaskRepository extends ListCrudRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);
}
