package com.irbish.akvelontest.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private long        id;
    private String      description;
    private String      name;
    private Integer     priority;
    private long        projectId;
    private TaskStatus  status;

    public String toDatabaseFormat(){
        return "{ \"description\": \"" + description +
                "\", \"name\": \"" + name +
                "\", \"priority\": " + priority +
                ", \"status\": \"" + status + "\" }";
    }
}
