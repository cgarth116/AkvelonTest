package com.irbish.akvelontest.service;

import com.irbish.akvelontest.model.Task;

import java.util.List;

public interface TaskService {

    /**
     * Creating a new task
     * in accordance with the submitted task
     * @param task - task in accordance with which you need to create the data
     * @return - task if the data has been created, otherwise null
     */
    Task create(Task task);

    /**
     * Returns the task by id
     * @param id - task id
     * @return - task with given id if found, otherwise null
     */
    Task find(long id);

    /**
     * Returns a list of all existing tasks
     * for the given project
     * @param id - id of the given project
     * @return - list of tasks
     */
    List<Task> findAllTasksForProject(long id);

    /**
     * Updates the task
     * if the task exists with the given id or creation if not.
     * in accordance with the submitted task
     * @param task - task in accordance with which you need to update the data
     * @param id - id of the updated task
     * @return - task if the data has been updated/created, otherwise null
     */
    Task update(Task task, long id);

    /**
     * Deletes the task with the given projectId and taskId
     * @param projectId - id of the project from which we are deleting the task
     * @param taskId - id task to delete
     * @return - deleted task if the data has been deleted, otherwise null
     */
    Task delete(long projectId,long taskId);

}
