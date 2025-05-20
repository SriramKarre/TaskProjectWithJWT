package com.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.entity.Task;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUsersId(long userId); // Use 'Users' instead of 'userId' if that's the correct field name
}
