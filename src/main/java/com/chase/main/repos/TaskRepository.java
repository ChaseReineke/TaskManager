package com.chase.main.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chase.main.models.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
	List<Task> findAll();
}