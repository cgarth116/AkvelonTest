package com.irbish.akvelontest.service;

import com.irbish.akvelontest.model.Project;

import java.util.List;

/**
 * Interface for implementing the project entity
 */
public interface ProjectService {

    /**
     * Create a new project
     * in accordance with the submitted project
     * @param project - project in accordance with which you need to create the data
     * @return - project if the data has been created, otherwise null
     */
    Project create(Project project);

    /**
     * Returns the project by id
     * @param id - project id
     * @return - project with given id if found, otherwise null
     */
    Project find(long id);

    /**
     * Returns a list of all existing projects
     * @return - list of projects
     */
    List<Project> findAll();

    /**
     * Updates the project
     * if the project exists with the given id or creation if not.
     * In accordance with the submitted project
     * @param project - project in accordance with which you need to update the data
     * @param id - id of the updated project
     * @return - project if the data has been updated/created, otherwise null
     */
    Project update(Project project, long id);

    /**
     * Deletes the project with the given id
     * @param id - id of the project to be deleted
     * @return - deleted project if the data has been deleted, otherwise null
     */
    Project delete(long id);

}
