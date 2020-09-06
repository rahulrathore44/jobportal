package com.lab.jobportal.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.lab.jobportal.config.SecurityConstants.NORMAL_PATH;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
public class TestJobPrortalControllerUpdateQueryParam {

	private static final String JOB_TITLE = "Software Engg";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private XmlMapper xmlMapper = new XmlMapper();

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
	public void test_patch_end_point_with_json_data() throws Exception {
		mvc.perform(patch(NORMAL_PATH + "/update/details")
				.queryParam("id", String.valueOf(id))
				.queryParam("jobTitle", JOB_TITLE + "Update")
				.queryParam("jobDescription", "To develop andriod application Update")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("jobId", is(id)));
		mvc.perform(get(NORMAL_PATH + "/find").queryParam("id", String.valueOf(id)).queryParam("jobTitle",
				JOB_TITLE + "Update")).andExpect(status().isOk());
	}

	@Test
	public void test_patch_end_point_with_notfound() throws Exception {
		mvc.perform(patch(NORMAL_PATH + "/update/details")
				.queryParam("id", String.valueOf(id + 1))
				.queryParam("jobTitle", JOB_TITLE + "Update")
				.queryParam("jobDescription", "To develop andriod application Update")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
		mvc.perform(get(NORMAL_PATH + "/find").queryParam("id", String.valueOf(id)).queryParam("jobTitle",
				JOB_TITLE + "Update")).andExpect(status().isNotFound());
	}
}
