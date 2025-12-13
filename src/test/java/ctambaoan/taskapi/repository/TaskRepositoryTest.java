package ctambaoan.taskapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ctambaoan.taskapi.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository repository;
    Task newTask;
    Task savedTask;

    @BeforeEach
    void setup() {
        newTask = new Task("name", "description");
        savedTask = repository.save(newTask);
    }

    @Test
    void canSaveTask() {
        assertThat(savedTask.getId()).isNotNull();
    }

    @Test
    void findTaskWorks() {
        Long id = savedTask.getId();
        assertThat(repository.findById(id)).isPresent();
    }
}
