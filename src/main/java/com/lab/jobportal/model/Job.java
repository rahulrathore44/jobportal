package com.lab.jobportal.model;
/**
* rathr1
* 
**/

import java.util.LinkedList;
import java.util.List;

public class Job {

	private int jobId;
	private String jobTitle;
	private String jobDescription;
	private LinkedList<String> experience;
	private LinkedList<Project> project;

	public Job() {
		if (experience == null)
			experience = new LinkedList<String>();
		if (project == null)
			project = new LinkedList<Project>();
	}

	public int getJobId() {
		return jobId;
	}

	public Job setJobId(int jobId) {
		this.jobId = jobId;
		return this;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public Job setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
		return this;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public Job setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
		return this;
	}

	public LinkedList<String> getExperience() {
		return experience;
	}

	public Job setExperience(List<String> experience) {
		this.experience.addAll(experience);
		return this;
	}

	public LinkedList<Project> getProject() {
		return project;
	}

	public Job setProject(List<Project> project) {
		this.project.addAll(project);
		return this;
	}

	@Override
	public String toString() {
		return String.format("[jobId=%s, jobTitle=%s, jobDescription=%s, experience=%s, project=%s]", jobId, jobTitle,
				jobDescription, experience.toArray(), project.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Job) {
			return this.jobId == ((Job) obj).jobId;
		}
		return false;
	}

}
