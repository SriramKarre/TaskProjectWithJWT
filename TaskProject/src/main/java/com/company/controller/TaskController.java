package com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.company.payload.TaskDto;
import com.company.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {
	@Autowired
	private TaskService taskService;

	// save the task
	@PostMapping("/{userId}/tasks")
	public ResponseEntity<TaskDto> saveTask(@PathVariable("userId") long userId, @RequestBody TaskDto taskDto) {
		return new ResponseEntity<>(taskService.saveTask(userId, taskDto), HttpStatus.CREATED);
	}

	// get all the task
	@GetMapping("/{userId}/tasks")
	public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable("userId") long userId) {

		return new ResponseEntity<>(taskService.getAllTasks(userId), HttpStatus.OK);
	}

	// get task by id
	@GetMapping("/{userId}/tasks/{taskId}")
	public ResponseEntity<TaskDto> getTask(@PathVariable("taskId") long userId, @PathVariable("taskId") long taskId) {
		return new ResponseEntity<>(taskService.getTask(userId, taskId), HttpStatus.OK);
	}

	// delete task by id
	@DeleteMapping("/{userId}/tasks/{taskId}")
	public ResponseEntity<String> deleteTask(@PathVariable("taskId") long userId, @PathVariable("taskId") long taskId) {
		taskService.deleteTask(userId, taskId);
		return new ResponseEntity<>("Task Deleted Successfully!!", HttpStatus.OK);
	}

}
