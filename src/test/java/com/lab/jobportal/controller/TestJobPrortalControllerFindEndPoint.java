package com.lab.jobportal.controller;

import static com.lab.jobportal.config.SecurityConstants.NORMAL_PATH;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lab.jobportal.model.Job;
import com.lab.jobportal.model.Project;

/**
 * rathr1
 * 
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(JobPortalController.class)
public class TestJobPrortalControllerFindEndPoint {

	private static final String JOB_TITLE = "Software Engg";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	private MockMvc mvc;

	private int id = (int) (Math.random() * 100);

	private Job getJobWithRandomId() {
		Job aNewJob = new Job();
		aNewJob.setJobId(id).setJobTitle(JOB_TITLE).setJobDescription("To develop andriod application")
				.setExperience(Arrays.asList("Google", "Apple", "Mobile Iron"))
				.setProject(Arrays.asList(new Project("Movie App", Arrays.asList("Kotlin", "SQL Lite", "Gradle"))));
		return aNewJob;
	}

	@Before
	public void setUp() {
		String jsonContent = gson.toJson(getJobWithRandomId());
		try {
			mvc.perform(post(NORMAL_PATH + "/add").accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).content(jsonContent)).andExpect(status().isCreated());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_find_end_point_json() throws Exception {
		mvc.perform(get(NORMAL_PATH + "/find")
				.queryParam("id", String.valueOf(id))
				.queryParam("jobTitle", JOB_TITLE)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("jobId", is(id)))
		.andExpect(jsonPath("jobTitle", is(JOB_TITLE)));
		
	}
	
	@Test
	public void test_find_end_point_xml() throws Exception {
		mvc.perform(get(NORMAL_PATH + "/find")
				.queryParam("id", String.valueOf(id))
				.queryParam("jobTitle", JOB_TITLE)
				.accept(MediaType.APPLICATION_XML))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
		.andExpect(content().node(hasXPath("/Job/jobId", equalTo(String.valueOf(id)))))
		.andExpect(content().node(hasXPath("/Job/jobTitle", equalTo(JOB_TITLE))));
		
	}

	@Test
	public void test_find_end_when_query_param_missing() throws Exception {
		mvc.perform(get(NORMAL_PATH + "/find")
				.queryParam("jobTitle", JOB_TITLE)
				.accept(MediaType.APPLICATION_XML))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void test_find_end_point_when_id_notfound() throws Exception {
		mvc.perform(get(NORMAL_PATH + "/find")
				.queryParam("id", String.valueOf(id + 1))
				.queryParam("jobTitle", JOB_TITLE)
				.accept(MediaType.APPLICATION_XML))
		.andExpect(status().isNotFound());
	}

}
