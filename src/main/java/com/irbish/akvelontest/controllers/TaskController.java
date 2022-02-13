package com.irbish.akvelontest.controllers;

import com.irbish.akvelontest.model.Task;
import com.irbish.akvelontest.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.irbish.akvelontest.controllers.PathForUrl.API_PROJECT;
import static com.irbish.akvelontest.controllers.PathForUrl.API_TASK;

@RestController
@RequestMapping(API_TASK)
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Getting a task by id
     * @param projectId - id of the project in which we get the task
     * @param taskId - task id
     * @return response "OK" with task if data exists, otherwise "notFound"
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> findById(@PathVariable(value = "projectId") long projectId,
                                         @PathVariable(value = "taskId") long taskId) {
        Task task = taskService.find(taskId);
        if (task != null && task.getProjectId() == projectId){
            return ResponseEntity.ok().body(task);
        }
        return  ResponseEntity.notFound().build();
    }

    /**
     * Getting a list of tasks for project
     * @param projectId - id of the project in which we get the tasks
     * @param ucb - inline path from call(inserted automatically)
     * @return list of response with task and location
     */
    @GetMapping()
    public List<ResponseEntity<Task>> findAll(@PathVariable(value = "projectId") long projectId, UriComponentsBuilder ucb){
        List<ResponseEntity<Task>> responseEntityList = new ArrayList<>();
        List<Task> taskList = taskService.findAllTasksForProject(projectId);
        if (taskList.size() != 0) {
            taskList.forEach(task -> responseEntityList.add(responseBuilderWithLocationEntity(task, projectId, HttpStatus.OK, ucbToString(ucb))));
        } else {
            responseEntityList.add(ResponseEntity.notFound().build());
        }
        return responseEntityList;
    }

    /**
     * Task creation
     * @param projectId - id of the project in which we create the task
     * @param task - object data
     * @param ucb - inline path from call(inserted automatically)
     * @return response "CREATED" with project and location if created, otherwise response "NOT_IMPLEMENTED"
     */
    @PostMapping()
    public ResponseEntity<Task> create(@PathVariable(value = "projectId") long projectId,
                                       @Validated @RequestBody Task task,
                                       UriComponentsBuilder ucb){
        HttpStatus httpStatus = HttpStatus.CREATED;
        task.setId(0);
        task.setProjectId(projectId);
        Task taskCreate = taskService.create(task);
        if (taskCreate == null){
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
        }
        return responseBuilderWithLocationEntity(taskCreate, projectId, httpStatus, ucbToString(ucb));
    }

    /**
     * Change task data if exists, otherwise create data
     * @param task - object data
     * @param projectId - id of the project in which we change the task
     * @param id - task id to change
     * @param ucb - inline path from call(inserted automatically)
     * @return response "CREATED" with task and location if new data exists,
     * otherwise "OK" with project and location if data updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@Validated @RequestBody Task task,
                                       @PathVariable(value = "projectId") long projectId,
                                       @PathVariable(value = "id") long id,
                                       UriComponentsBuilder ucb) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        task.setProjectId(projectId);
        Task taskUpdated = taskService.update(task, id);
        if (taskUpdated == null){
            httpStatus = HttpStatus.NOT_MODIFIED;
        } else {
            if (taskUpdated.getId() == id) {
                httpStatus = HttpStatus.OK;
            }
        }
        return responseBuilderWithLocationEntity(taskUpdated, projectId, httpStatus, ucbToString(ucb));
    }

    /**
     * Deleting a task
     * @param projectId - id of the project in which we delete the task
     * @param taskId - id of the task to be deleted
     * @return response "OK" with task and location deleted data, otherwise response "NOTFOUND"
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Task> delete(@PathVariable(value = "projectId") long projectId,
                                       @PathVariable(value = "taskId") Long taskId) {
        Task task = taskService.delete(projectId, taskId);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    /**
     * Response Builder
     * @param task - object data
     * @param projectId - id project
     * @param httpStatus - response status
     * @param url - inline path from call
     * @return response with task and location
     */
    private ResponseEntity<Task> responseBuilderWithLocationEntity(Task task, long projectId, HttpStatus httpStatus, String url) {
        HttpHeaders headers = new HttpHeaders();
        try {
            URI locationUri = new URI(url + API_PROJECT + projectId + "/task/" + task.getId());
            headers.setLocation(locationUri);
        } catch (Exception ignored){}
        return new ResponseEntity<>(task, headers, httpStatus);
    }

    /**
     * Transformation UriComponentsBuilder to String format
     * @param ucb - main inline path from call
     * @return string
     */
    private String ucbToString(UriComponentsBuilder ucb){
        return ucb.build().toString();
    }

}
