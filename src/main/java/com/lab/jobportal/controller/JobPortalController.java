package com.lab.jobportal.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.lab.jobportal.config.SecurityConstants.NORMAL_PATH;
import com.lab.jobportal.exception.JobNotFoundException;
import com.lab.jobportal.exception.RuntimeOperationException;
import com.lab.jobportal.impl.IJobDescription;
import com.lab.jobportal.impl.JobDescriptionImpl;
import com.lab.jobportal.model.Job;

import io.swagger.annotations.Api;

/**
 * rathr1
 * 
 **/
@Api(value = "Job Portal", description = "End Point for Job Portal Application")
@RestController
@RequestMapping(path = {NORMAL_PATH})
public class JobPortalController {
	private IJobDescription jobDescriptionImpl = new JobDescriptionImpl();
	private final Logger oLog = LoggerFactory.getLogger(JobPortalController.class);

	@RequestMapping(path = { "/all" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

	public ResponseEntity<List<Job>> getAllJobDescription() {
		oLog.info("GET " + NORMAL_PATH + "/all ");
		if (jobDescriptionImpl.getAllJobDescription().isEmpty()) {
			return new ResponseEntity<List<Job>>(jobDescriptionImpl.getAllJobDescription(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Job>>(jobDescriptionImpl.getAllJobDescription(), HttpStatus.OK);
	}

	@RequestMapping(path = { "/remove/{id}" }, method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> deleteJobDescriptionUsingId(@PathVariable("id") int id) {
		oLog.info(String.format("DELETE " + NORMAL_PATH + "/remove/%s", id));
		Optional<Job> isDeleted = jobDescriptionImpl.deleteJobDescription(id);
		if (!isDeleted.isPresent()) {
			throw new JobNotFoundException(String.format("Entry with id = %s not found", id));
		}
		return new ResponseEntity<Object>(isDeleted.get(), HttpStatus.OK);
	}

	@RequestMapping(path = { "/add" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> createJobDescription(@RequestBody Job aJob) {
		oLog.info(String.format("POST " + NORMAL_PATH + "/add with data = %s", aJob.toString()));
		try {
			jobDescriptionImpl.createJobDescription(aJob);
			return new ResponseEntity<Object>(aJob, HttpStatus.CREATED);
		} catch (Exception e) {
			oLog.error(e.getMessage());
		}
		throw new RuntimeOperationException(String.format("Failed to add Entry %s", aJob.toString()));
	}

	@RequestMapping(path = { "/update" }, method = RequestMethod.PUT, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> updateJobDescription(@RequestBody Job aJob) {
		oLog.info(String.format("PUT " + NORMAL_PATH + "/update with data = %s", aJob.toString()));
		Optional<Job> isUpdated = jobDescriptionImpl.updateJobDescription(aJob);
		if (!isUpdated.isPresent()) {
			throw new JobNotFoundException(String.format("Failed to update Entry %s", aJob.toString()));
		}
		return new ResponseEntity<Object>(isUpdated.get(), HttpStatus.OK);
	}

	@RequestMapping(path = { "/find" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> findJobDescription(@RequestParam int id, @RequestParam String jobTitle) {
		oLog.info(String.format("GET " + NORMAL_PATH + "/find?id=%s&jobTitle=%s", id, jobTitle));
		Optional<Job> isFound = jobDescriptionImpl.findJobDescription(id, jobTitle);
		if (!isFound.isPresent()) {
			throw new JobNotFoundException(String.format("Failed to find job with id=%s, jobTitle=%s", id, jobTitle));
		}
		return new ResponseEntity<Object>(isFound.get(), HttpStatus.OK);
	}

	@RequestMapping(path = { "/update/details" }, method = RequestMethod.PATCH, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> updateJobDetails(@RequestParam int id, @RequestParam String jobTitle,
			@RequestParam String jobDescription) {
		oLog.info(String.format("PATCH " + NORMAL_PATH + "/update/details?id=%s&jobTitle=%s&jobDescription=%s", id,
				jobTitle, jobDescription));
		Optional<Job> isFound = jobDescriptionImpl.updateJobDetails(id, jobTitle, jobDescription);
		if (!isFound.isPresent()) {
			throw new JobNotFoundException(String.format("Failed to find job with id=%s", id));
		}
		return new ResponseEntity<Object>(isFound.get(), HttpStatus.OK);
	}

}
