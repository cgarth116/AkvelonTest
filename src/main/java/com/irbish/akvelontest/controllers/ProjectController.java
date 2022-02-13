package com.irbish.akvelontest.controllers;

import com.irbish.akvelontest.model.Project;
import com.irbish.akvelontest.service.ProjectService;
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

@RestController
@RequestMapping(API_PROJECT)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * Getting a list of projects
     * @param ucb - inline path from call(inserted automatically)
     * @return list of response with project and location
     */
    @GetMapping
    public List<ResponseEntity<Project>> findAllProject(UriComponentsBuilder ucb){
        List<ResponseEntity<Project>> responseEntityList = new ArrayList<>();
        List<Project> projectList = projectService.findAll();
        if (projectList.size() != 0) {
            projectList.forEach(project -> responseEntityList.add(responseBuilderWithLocationEntity(project, HttpStatus.OK, ucbToString(ucb))));
        } else {
            responseEntityList.add(ResponseEntity.notFound().build());
        }
        return responseEntityList;
    }

    /**
     * Getting a project by id
     * @param id - id project
     * @return response "OK" with project if data exists, otherwise "notFound"
     */
    @GetMapping("/{id}")
    public ResponseEntity<Project> findProjectById(@PathVariable(value = "id") long id) {
        Project project = projectService.find(id);
        if (project != null){
            return  ResponseEntity.ok().body(project);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * project creation
     * @param project - object data
     * @param ucb - inline path from call(inserted automatically)
     * @return response "CREATED" with project and location
     */
    @PostMapping()
    public ResponseEntity<Project>  createProject(@Validated @RequestBody Project project, UriComponentsBuilder ucb){
        project.setId(0);
        return responseBuilderWithLocationEntity(projectService.create(project), HttpStatus.CREATED, ucbToString(ucb));
    }

    /**
     * Change project data if exists, otherwise create data
     * @param project - object data
     * @param id - project id to change
     * @param ucb - inline path from call(inserted automatically)
     * @return response "CREATED" with project and location if new data exists,
     * otherwise "OK" with project and location if data updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@Validated @RequestBody Project project,
                                                 @PathVariable(value = "id") long id,
                                                 UriComponentsBuilder ucb) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        if (projectService.update(project, id).getId() == id){
            httpStatus =HttpStatus.OK;
        }
        return responseBuilderWithLocationEntity(project, httpStatus, ucbToString(ucb));
    }

    /**
     * Deleting a project
     * @param id - id of the project to be deleted
     * @return response "OK" with project and location deleted data, otherwise response "NOTFOUND"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable(value = "id") Long id) {
        Project project = projectService.delete(id);
        return project != null ? ResponseEntity.ok(project): ResponseEntity.notFound().build();
    }

    /**
     * Response Builder
     * @param project - object data
     * @param httpStatus - response status
     * @param url - inline path from call
     * @return response with project and location
     */
    private ResponseEntity<Project> responseBuilderWithLocationEntity(Project project, HttpStatus httpStatus, String url) {
        HttpHeaders headers = new HttpHeaders();
        try {
            URI locationUri = new URI(url + API_PROJECT + project.getId());
            headers.setLocation(locationUri);
        } catch (Exception ignored){}
        return new ResponseEntity<>(project, headers, httpStatus);
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
