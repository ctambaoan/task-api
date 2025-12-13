package ctambaoan.taskapi.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskTest {

    @Test
    void taskNameCannotBeBlank() {
        assertThatThrownBy(() -> new Task("", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Task name cannot be blank");
    }

    @Test
    void taskNameCannotExceed50Characters() {
        String longName = "a".repeat(51);
        assertThatThrownBy(() -> new Task(longName, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Task name cannot exceed 50 characters");
    }

    @Test
    void newTaskShouldHaveDefaultStatusTodo() {
        Task task = new Task("Buy milk", "From the store");
        assertThat(task.getStatus()).isEqualTo(TaskStatus.TODO);
    }

    @Test
    void newTaskShouldHaveDateCreated() {
        Task task = new Task("Hello", "World");
        assertThat(task.getCreated()).isNotNull();
    }

    @Test
    void canCreateValidTask() {
        Task task = new Task("Valid name", "Valid desc");
        assertThat(task.getName()).isEqualTo("Valid name");
        assertThat(task.getDescription()).isEqualTo("Valid desc");
    }

    @Test
    void taskCanBeMarkedAsDone() {
        Task task = new Task("Hello", "World");
        task.markAsDone();
        assertThat(task.getStatus()).isEqualTo(TaskStatus.DONE);
    }

    @Test
    void updateDescriptionShouldThrowExceptionWithBlank() {
        Task task = new Task("Hello", "");
        assertThatThrownBy(() -> task.updateDescription(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Description cannot be blank");
    }

    @Test
    void updateDescriptionShouldUpdateWithValid() {
        Task task = new Task("Hello", "");
        task.updateDescription("World!");
        assertThat(task.getDescription()).isEqualTo("World!");
    }

}
