package com.irbish.akvelontest.service;

import com.irbish.akvelontest.model.Project;
import com.irbish.akvelontest.model.ProjectStatus;
import com.irbish.akvelontest.repositories.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.mockito.Mockito.when;

/**
 * Test for {@link ProjectServiceImpl}
 */
@DisplayName("Project service tests")
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskService taskService;

    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        projectService = new ProjectServiceImpl(projectRepository, taskService);
    }

    private static Object[][] updateProject(){
        return new Object[][]{
                {1, createProject(1), 1, null},
                {2, createProject(1), 2, createProject(11)},
        };
    }

    @ParameterizedTest
    @MethodSource("updateProject")
    @DisplayName("Update Should Return Expected Result")
    void updateShouldReturnExpectedResult(long expected, Project project, long id, Project projectFromDataBase){
        when(projectRepository.findById(id)).thenReturn(projectFromDataBase);
        when(projectRepository.save(project)).thenReturn(project);
        Assertions.assertEquals(expected, projectService.update(project, id).getId());
    }

    private static Project createProject(long id){
        return new Project(id, "", new Timestamp(0), new Timestamp(0), ProjectStatus.Active, 1);
    }
}
