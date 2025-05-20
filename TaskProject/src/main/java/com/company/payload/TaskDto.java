package com.company.payload;

import lombok.Getter;
import lombok.Setter;

public class TaskDto {
	private long id;
	private String taskname;

	public TaskDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaskDto(long id, String taskname) {
		super();
		this.id = id;
		this.taskname = taskname;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

}
