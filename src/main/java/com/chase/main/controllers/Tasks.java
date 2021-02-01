package com.chase.main.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chase.main.models.Task;
import com.chase.main.models.User;
import com.chase.main.services.TaskService;
import com.chase.main.services.UserService;

@Controller
public class Tasks {
    private final TaskService taskService;
    private final UserService userService;
    
    public Tasks(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }
    
    //Show all tasks
    @RequestMapping("/tasks")
    public String index(Model model, HttpSession session) {
        List<Task> tasks = taskService.allTasks();
        Long user_id = (Long) session.getAttribute("userId");
        User u = userService.findUserById(user_id);
        model.addAttribute("tasks", tasks);
        model.addAttribute("userName", u.getName());
        return "dashboard.jsp";
    }
    
    //Show new task page
    @RequestMapping("/tasks/new")
    public String newTask(@ModelAttribute("task") Task task, Model model, HttpSession session) {
    	Long user_id = (Long) session.getAttribute("userId");
        User u = userService.findUserById(user_id);
    	List<User> users = userService.allUsers();
    	session.setAttribute("users", users);
    	model.addAttribute("currentUserName", u.getName());
        return "new.jsp";
    }
    
    //Commit new task
    @RequestMapping(value="/tasks", method=RequestMethod.POST)
    public String create(@Valid @ModelAttribute("task") Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "new.jsp";
        } else {
        	taskService.createTask(task);
            return "redirect:/tasks";
        }
    }
    
    //Show selected task
    @RequestMapping("tasks/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", taskService.findTask(id));
		return "show.jsp";
	}
    
    //Show the edit page
    @RequestMapping("/tasks/{id}/edit_page")
    public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
    	Long user_id = (Long) session.getAttribute("userId");
        User u = userService.findUserById(user_id);
        Task task = taskService.findTask(id);
        List<User> users = userService.allUsers();
        
        model.addAttribute("task", task);
    	session.setAttribute("users", users);
        model.addAttribute("userName", u.getName());
        return "edit.jsp";
    }
    
    //Commit task update
    @RequestMapping(value="/tasks/{id}/edit", method=RequestMethod.POST)
    public String update(@Valid @ModelAttribute("task") Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "edit.jsp";
        } else {
        	taskService.updateTask(task);
            return "redirect:/tasks";
        }
    }
    
    //Delete the task
    @RequestMapping(value="/tasks/{id}/delete", method=RequestMethod.POST)
    public String destroy(@PathVariable("id") Long id) {
    	taskService.deleteTask(id);
    	return "redirect:/tasks";
    }
}