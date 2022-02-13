package com.irbish.akvelontest.service;

import com.irbish.akvelontest.model.*;
import com.irbish.akvelontest.repositories.ProjectRepository;
import com.irbish.akvelontest.repositories.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test for {@link TaskServiceImpl}
 */
@DisplayName("Task service tests")
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        taskService = new TaskServiceImpl(taskRepository, projectRepository);
    }

    private static Object[][] updateTask(){
        return new Object[][]{
                {null, null, null, createTask(1,1), 1},
                {null, createEntityTaskInDatabase(1,1), null, createTask(1,1), 1},
                {null, createEntityTaskInDatabase(2,3), createProject(1), createTask(1,2), 1}
        };
    }

    @ParameterizedTest
    @MethodSource("updateTask")
    @DisplayName("Update Should Return Expected Result")
    void updateShouldReturnExpectedResult(Task expected, EntityTaskInDatabase entityTaskInDatabase, Project project, Task task, long id){
        when(taskRepository.findById(id)).thenReturn(entityTaskInDatabase);
        when(projectRepository.findById(task.getProjectId())).thenReturn(project);
        when(taskRepository.save(any())).thenReturn(expected);
        Assertions.assertEquals(expected, taskService.update(task, id));
    }

    private static Task createTask(long id, long projectId){
        return new Task(id, "", "", null, projectId, null);
    }

    private static EntityTaskInDatabase createEntityTaskInDatabase(long id, long projectId){
        return new EntityTaskInDatabase(id, projectId, "{}");
    }
    private static Project createProject(long id){
        return new Project(id, "", new Timestamp(0), new Timestamp(0), ProjectStatus.Active, 1);
    }

}
