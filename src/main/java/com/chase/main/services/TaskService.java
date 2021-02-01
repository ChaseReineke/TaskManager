package com.chase.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chase.main.models.Task;
import com.chase.main.repos.TaskRepository;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    //Returns tasks
    public List<Task> allTasks() {
        return taskRepository.findAll();
    }
    //New task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    //Gets task
    public Task findTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()) {
            return optionalTask.get();
        } 
        else {
            return null;
        }
    }
    //Edits task
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }
    
    //Delete task
    public void deleteTask(Long id) {
    	taskRepository.deleteById(id);
    }
}