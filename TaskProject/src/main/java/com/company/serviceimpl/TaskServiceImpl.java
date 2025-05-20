package com.company.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.entity.Task;
import com.company.entity.Users;
import com.company.exception.APIException;
import com.company.exception.TaskNotFound;
import com.company.exception.UserNotFoundException;
import com.company.payload.TaskDto;
import com.company.repository.TaskRepository;
import com.company.repository.UserRepository;
import com.company.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public TaskDto saveTask(long userId, TaskDto taskDto) {
		// TODO Auto-generated method stub

		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("User Id %d not found", userId)));
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUsers(user);
		Task savedTask = taskRepository.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(long userId) {
		// Check if user exists, otherwise throw exception
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("User Id %d not found", userId)));

		// Get all tasks associated with the user
		List<Task> tasks = taskRepository.findAllByUsersId(userId);

		// Initialize a single ModelMapper instance
		ModelMapper modelMapper = new ModelMapper();

		// Map each Task to TaskDto
		return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(long userId, long taskId) {
		// TODO Auto-generated method stub
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("User Id %d not found", userId)));
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFound(String.format("Task Id %d not found", taskId)));
		if (user.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskId, userId));
		}
		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public void deleteTask(long userId, long taskId) {
		// TODO Auto-generated method stub
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(String.format("User Id %d not found", userId)));
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFound(String.format("Task Id %d not found", taskId)));
		if (user.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskId, userId));
		}
		taskRepository.deleteById(taskId);
		
	}

}
