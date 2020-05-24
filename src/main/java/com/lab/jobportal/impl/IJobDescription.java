package com.lab.jobportal.impl;

import java.util.List;
import java.util.Optional;

import com.lab.jobportal.model.Job;

/**
* rathr1
* 
**/

public interface IJobDescription {
	public void createJobDescription(Job aJob);
	public Optional<Job> deleteJobDescription(int id);
	public Optional<Job> updateJobDescription(Job aJob);
	public Optional<Job> findJobDescription(int id,String name);
	public List<Job> getAllJobDescription();
	public Optional<Job> updateJobDetails(int id,String jobTitle,String jobDescription);
}
