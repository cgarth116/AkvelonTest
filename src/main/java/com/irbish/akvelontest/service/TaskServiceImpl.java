package com.irbish.akvelontest.service;

import com.irbish.akvelontest.model.EntityTaskInDatabase;
import com.irbish.akvelontest.model.Task;
import com.irbish.akvelontest.model.TaskStatus;
import com.irbish.akvelontest.repositories.ProjectRepository;
import com.irbish.akvelontest.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Task create(Task task) {
        if (projectRepository.findById(task.getProjectId()) != null){
            EntityTaskInDatabase entityTaskInDatabase = fromTaskToEntityTaskInDatabase(task);
            entityTaskInDatabase = taskRepository.save(entityTaskInDatabase);
            return fromJsonToObject(entityTaskInDatabase);
        }
        return null;
    }

    @Override
    public Task find(long id) {
        EntityTaskInDatabase task = taskRepository.findById(id);
        if (task != null){
            return fromJsonToObject(task);
        }
        return null;
    }

    @Override
    public List<Task> findAllTasksForProject(long projectId) {
        List<Task> taskList = new ArrayList<>();
        List<EntityTaskInDatabase> entityTaskInDatabases = taskRepository.findByProjectId(projectId);
        entityTaskInDatabases.forEach(value -> taskList.add(fromJsonToObject(value)));
        return taskList;
    }

    @Override
    public Task update(Task task, long id){
        EntityTaskInDatabase taskInDatabase = new EntityTaskInDatabase();
        EntityTaskInDatabase inDatabase = taskRepository.findById(id);
        if (projectRepository.findById(task.getProjectId()) == null ||
                (inDatabase != null && inDatabase.getProjectId() != task.getProjectId())) {
            return null;
        }
        if (inDatabase != null) {
            taskInDatabase.setId(id);
        }
        taskInDatabase.setProjectId(task.getProjectId());
        taskInDatabase.setTask(task.toDatabaseFormat());
        return fromJsonToObject(taskRepository.save(taskInDatabase));
    }

    @Override
    public Task delete(long projectId, long taskId) {
        Task task = find(taskId);
        if (task != null && Objects.equals(task.getProjectId(), projectId)){
            taskRepository.deleteById(taskId);
            return task;
        }
        return null;
    }

    private Task fromJsonToObject(EntityTaskInDatabase task) {
        Task taskConverted = new Task();

        JSONObject values = new JSONObject(task.getTask());
        taskConverted.setId(task.getId());
        taskConverted.setProjectId(task.getProjectId());
        try {
            taskConverted.setDescription(values.getString("description"));
        } catch(Exception ignored){}
        try {
            taskConverted.setName(values.getString("name"));
        } catch(Exception ignored){}
        try {
            taskConverted.setPriority(values.getInt("priority"));
        } catch(Exception ignored){}
        try {
            taskConverted.setStatus(TaskStatus.valueOf(values.getString("status")));
        } catch(Exception ignored){}
        return taskConverted;
    }

    private EntityTaskInDatabase fromTaskToEntityTaskInDatabase(Task task){
        EntityTaskInDatabase entityTaskInDatabase = new EntityTaskInDatabase();
        entityTaskInDatabase.setProjectId(task.getProjectId());
        entityTaskInDatabase.setTask(task.toDatabaseFormat());
        return entityTaskInDatabase;
    }

}