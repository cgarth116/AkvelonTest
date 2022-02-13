package com.irbish.akvelontest.repositories;

import com.irbish.akvelontest.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findById(long id);
}
