package com.irbish.akvelontest.repositories;

import com.irbish.akvelontest.model.EntityTaskInDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TaskRepository extends JpaRepository<EntityTaskInDatabase, Long> {
    List<EntityTaskInDatabase> findByProjectId(long projectId);
    EntityTaskInDatabase findById(long id);
}
