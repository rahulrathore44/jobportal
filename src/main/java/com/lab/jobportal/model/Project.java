package com.lab.jobportal.model;
/**
* rathr1
* 
**/

import java.util.LinkedList;
import java.util.List;

public class Project {

	private String projectName;

	public Project(String projectName, List<String> technology) {
		this.projectName = projectName;

		if (this.technology == null)
			this.technology = new LinkedList<String>();

		this.technology.addAll(technology);
	}

	public Project() {
		if (this.technology == null)
			this.technology = new LinkedList<String>();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public LinkedList<String> getTechnology() {
		return technology;
	}

	public void setTechnology(LinkedList<String> technology) {
		this.technology = technology;
	}

	private LinkedList<String> technology;

	@Override
	public String toString() {
		return String.format("[projectName=%s, technology=%s]", projectName, technology.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Project) {
			this.projectName.equalsIgnoreCase(((Project) obj).projectName);
		}
		return false;
	}

}
