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

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> findById(@PathVariable(value = "projectId") long projectId,
                                         @PathVariable(value = "taskId") long taskId) {
        Task task = taskService.find(taskId);
        if (task != null && task.getProjectId() == projectId){
            return ResponseEntity.ok().body(task);
        }
        return  ResponseEntity.notFound().build();
    }

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

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Task> delete(@PathVariable(value = "projectId") long projectId,
                                       @PathVariable(value = "taskId") Long taskId) {
        Task task = taskService.delete(projectId, taskId);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    private ResponseEntity<Task> responseBuilderWithLocationEntity(Task task, long projectId, HttpStatus httpStatus, String url) {
        HttpHeaders headers = new HttpHeaders();
        try {
            URI locationUri = new URI(url + API_PROJECT + projectId + "/task/" + task.getId());
            headers.setLocation(locationUri);
        } catch (Exception ignored){}
        return new ResponseEntity<>(task, headers, httpStatus);
    }

    private String ucbToString(UriComponentsBuilder ucb){
        return ucb.build().toString();
    }

}
