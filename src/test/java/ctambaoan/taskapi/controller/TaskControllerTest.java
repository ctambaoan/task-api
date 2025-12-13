package ctambaoan.taskapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import ctambaoan.taskapi.domain.Task;
import ctambaoan.taskapi.domain.TaskStatus;
import ctambaoan.taskapi.dto.CreateTaskRequest;
import ctambaoan.taskapi.dto.UpdateDescriptionRequest;
import ctambaoan.taskapi.exception.TaskNotFoundException;
import ctambaoan.taskapi.service.TaskService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService service;

    @Test
    void createTaskShouldReturn400WhenValidationFailed() throws Exception {
        String request = objectMapper.writeValueAsString(new CreateTaskRequest("", "test"));

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTaskShouldReturn201WithValidInput() throws Exception {
        Task task = new Task("test1", "");
        String request = objectMapper.writeValueAsString(new CreateTaskRequest("test1", ""));

        when(service.create(any(String.class), any(String.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test1"))
                .andExpect(jsonPath("$.description").value(""))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    void findTaskByIdShouldReturn404WhenTaskNotFound() throws Exception {
        when(service.findById(1L)).thenThrow(TaskNotFoundException.class);

        mockMvc.perform(get("/api/tasks/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findTaskByIdShouldReturnTaskResponseWhenExists() throws Exception {
        when(service.findById(1L)).thenReturn(new Task("test-name", "test-description"));

        mockMvc.perform(get("/api/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test-name"))
                .andExpect(jsonPath("$.description").value("test-description"));
    }

    @Test
    void findAllTasksShouldReturnAList() throws Exception {
        List<Task> tasks = List.of(
                new Task("task1", ""),
                new Task("task2", ""));
        when(service.findAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findTaskByStatusShouldReturnAList() throws Exception {
        Task task = new Task("task1", "");
        task.markAsDone();
        List<Task> tasks = List.of(task);
        when(service.findByStatus(TaskStatus.DONE)).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks?status=DONE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void completeTaskShouldReturn404WhenNotFound() throws Exception {
        doThrow(new TaskNotFoundException(999L)).when(service).markAsDone(999L);

        mockMvc.perform(put("/api/tasks/{id}/complete", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void completeTaskShouldReturn204WhenExists() throws Exception {
        doNothing().when(service).markAsDone(1L);

        mockMvc.perform(put("/api/tasks/{id}/complete", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTaskShouldReturn404WhenNotFound() throws Exception {
        doThrow(new TaskNotFoundException(999L)).when(service).delete(999L);

        mockMvc.perform(delete("/api/tasks/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTaskShouldReturn204WhenExists() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/tasks/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateDescriptionShouldReturn404WhenNotFound() throws Exception {
        doThrow(new TaskNotFoundException(999L)).when(service).updateDescription(999L, "test");
        String request = objectMapper.writeValueAsString(
                new UpdateDescriptionRequest("test"));

        mockMvc.perform(put("/api/tasks/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateDescriptionShouldReturn400WhenValidationFailed() throws Exception {
        doNothing().when(service).updateDescription(999L, "");
        String request = objectMapper.writeValueAsString(
                new UpdateDescriptionRequest(""));

        mockMvc.perform(put("/api/tasks/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateDescriptionShouldReturn204WhenValid() throws Exception {
        doNothing().when(service).updateDescription(1L, "test");
        String request = objectMapper.writeValueAsString(
                new UpdateDescriptionRequest("test"));

        mockMvc.perform(put("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNoContent());
    }
}
