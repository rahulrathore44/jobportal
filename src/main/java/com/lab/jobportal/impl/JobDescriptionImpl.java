package com.lab.jobportal.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import com.lab.jobportal.model.Job;
import com.lab.jobportal.model.Project;

/**
 * rathr1
 * 
 **/
public class JobDescriptionImpl implements IJobDescription {
	private CopyOnWriteArrayList<Job> jobList = new CopyOnWriteArrayList<Job>();

	public JobDescriptionImpl() {
		if (jobList.isEmpty())
			jobList.addAll(getDefaultJob());
	}

	public void createJobDescription(Job aJob) {
		jobList.add(aJob);
	}

	public Optional<Job> deleteJobDescription(int id) {
		List<Job> deletedJob = jobList.stream().filter((aJob) -> {
			return aJob.getJobId() == id;
		}).collect(Collectors.toList());

		if (!deletedJob.isEmpty()) {
			jobList.removeIf((aJob) -> {
				return aJob.getJobId() == id;
			});
			return deletedJob.stream().findFirst();
		}
		return Optional.empty();
	}

	public Optional<Job> updateJobDescription(Job update) {
		Assert.notNull(update, "Input object is null");
		boolean isDeleted = jobList.removeIf((aJob) -> {
			return aJob.getJobId() == update.getJobId();
		});

		if (isDeleted) {
			jobList.add(update);
			return Optional.of(update);
		}
		return Optional.empty();
	}

	public Optional<Job> findJobDescription(int id, String jobTitle) {
		Assert.notNull(jobTitle, "Job title cannot be is null");
		List<Job> jobFound = jobList.stream().filter((aJob) -> {
			return aJob.getJobId() == id && aJob.getJobTitle().equalsIgnoreCase(jobTitle);
		}).collect(Collectors.toList());

		if (!jobFound.isEmpty())
			return jobFound.stream().findFirst();
		return Optional.empty();

	}

	public List<Job> getAllJobDescription() {
		return jobList;
	}

	private List<Job> getDefaultJob() {
		Job aNewJob = new Job();
		aNewJob.setJobId(1).setJobTitle("Software Engg").setJobDescription("To develop andriod application")
				.setExperience(Arrays.asList("Google", "Apple", "Mobile Iron"))
				.setProject(Arrays.asList(new Project("Movie App", Arrays.asList("Kotlin", "SQL Lite", "Gradle"))));
		return Arrays.asList(aNewJob);
	}

	@Override
	public Optional<Job> updateJobDetails(int id, String jobTitle, String jobDescription) {
		Assert.notNull(jobTitle, "Job title cannot be is null");
		Assert.notNull(jobDescription, "Job Description cannot be is null");
		
		for (Job job : jobList) {
			if(job.getJobId() == id){
				job.setJobTitle(jobTitle);
				job.setJobDescription(jobDescription);
				return Optional.of(job);
			}
		}
		return Optional.empty();
	}

}
