package ctambaoan.taskapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ctambaoan.taskapi.domain.Task;
import ctambaoan.taskapi.domain.TaskStatus;
import ctambaoan.taskapi.exception.TaskNotFoundException;
import ctambaoan.taskapi.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceImpl service;

    @Test
    void shouldReturnAValidTaskWhenCreated() {
        Task savedTask = new Task("Hello", "World");

        when(repository.save(any(Task.class))).thenReturn(savedTask);
        Task result = service.create("Hello", "World");

        verify(repository).save(any(Task.class));
        assertThat(result).isEqualTo(savedTask);
    }

    @Test
    void findByIdWhenExistsShouldReturnTask() {
        Optional<Task> foundTask = Optional.of(new Task("name", "desc"));
        when(repository.findById(any(Long.class))).thenReturn(foundTask);

        Task result = service.findById(1L);

        assertThat(result).isNotNull();
    }

    @Test
    void findByIdWhenNotExistsShouldThrowException() {
        Optional<Task> foundTask = Optional.empty();
        when(repository.findById(any(Long.class))).thenReturn(foundTask);

        assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void findAllShouldReturnAList() {
        List<Task> mockTasks = List.of(
                new Task("task1", ""),
                new Task("task2", ""));
        when(repository.findAll()).thenReturn(mockTasks);

        List<Task> result = service.findAll();
        assertThat(result).isEqualTo(mockTasks);
    }

    @Test
    void findByStatusWhenTodoShouldReturnTodoTasks() {
        List<Task> mockTodos = List.of(
                new Task("task1", ""),
                new Task("task2", ""));
        when(repository.findByStatus(TaskStatus.TODO)).thenReturn(mockTodos);

        List<Task> result = service.findByStatus(TaskStatus.TODO);
        assertThat(result).isEqualTo(mockTodos);
    }

    @Test
    void markAsDoneShouldUpdateStatusWhenTaskExists() {
        Optional<Task> mockTask = Optional.of(new Task("mock", "desc"));
        when(repository.findById(any(Long.class))).thenReturn(mockTask);

        service.markAsDone(1L);

        verify(repository).save(any(Task.class));
        assertThat(mockTask.get().getStatus()).isEqualTo(TaskStatus.DONE);
    }

    @Test
    void markAsDoneShouldThrowExceptionWhenTaskNotFound() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.markAsDone(1L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID: 1 does not exist in our records");
    }

    @Test
    void deleteShouldThrowExceptionWhenTaskNotFound() {
        when(repository.existsById(any(Long.class))).thenReturn(false);

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID: 1 does not exist in our records");
    }

    @Test
    void deleteShouldDeleteTaskWhenExists() {
        when(repository.existsById(any(Long.class))).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(any(Long.class));
    }

    @Test
    void updateDescriptionShouldThrowExceptionWhenTaskNotFound() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateDescription(1L, "Hello World!"))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID: 1 does not exist in our records");
    }

    @Test
    void updateDescriptionShouldUpdateWhenTaskExists() {
        Optional<Task> mockTask = Optional.of(new Task("task1", ""));
        when(repository.findById(any(Long.class))).thenReturn(mockTask);

        service.updateDescription(1L, "Hello World!");

        verify(repository).save(any(Task.class));
    }

}
