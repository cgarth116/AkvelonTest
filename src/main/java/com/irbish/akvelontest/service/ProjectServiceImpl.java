package com.irbish.akvelontest.service;

import com.irbish.akvelontest.model.Project;
import com.irbish.akvelontest.model.Task;
import com.irbish.akvelontest.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskService taskService;

    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project find(long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project update(Project project, long id){
        if (projectRepository.findById(id) != null) {
            project.setId(id);
        }
        return projectRepository.save(project);
    }

    @Override
    public Project delete(long id) {
        Project project = find(id);
        if (project != null){
            List<Task> taskList = taskService.findAllTasksForProject(id);
            taskList.forEach(task -> taskService.delete(id, task.getId()));
            projectRepository.deleteById(project.getId());
        }
        return project;
    }

}
