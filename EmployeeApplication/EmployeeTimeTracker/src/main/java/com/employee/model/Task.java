package com.employee.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
	private String id;
	private String EmpId;
	private String projectName;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private String category;
	private String description;
	private LocalTime Duration;

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalTime getDuration() {
		return Duration;
	}

	public void setDuration(LocalTime duration) {
		Duration = duration;
	}

	public String getEmpId() {
		return EmpId;
	}

	public void setEmpId(String EmpId) {
		this.EmpId = EmpId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Method to calculate hours spent on task
	public Integer getHoursSpent() {
		if (startTime != null && endTime != null) {
			return (int) java.time.Duration.between(startTime, endTime).toHours();
		}
		return 0; // Default value if startTime or endTime is null
	}
}
