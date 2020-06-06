package com.lab.jobportal.controller.secure;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.xml.HasXPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.lab.jobportal.controller.SecureJobPortalController;
import static com.lab.jobportal.config.SecurityConstants.SECURE_PATH;
/**
 * rathr1
 * 
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(SecureJobPortalController.class)
public class TestSecureJobPrortalControllerGetEndPoint {

	@Autowired
	private MockMvc mvc;

	private HttpHeaders getBasicAuthHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic YWRtaW46d2VsY29tZQ==");
		return headers;
	}

	@Test
	public void test_Get_all_end_point_with_json_data() throws Exception {
		mvc.perform(get(SECURE_PATH + "/all").headers(getBasicAuthHeader()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].jobId", is(1)));
	}

	@Test
	public void test_Get_all_end_point_with_xml_data() throws Exception {
		mvc.perform(get(SECURE_PATH + "/all").headers(getBasicAuthHeader()).header("Accept",
				MediaType.APPLICATION_XML_VALUE)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
				.andExpect(content().node(HasXPath.hasXPath("/List/item[1]/jobId", equalTo("1"))));
	}

	@Test
	public void test_Get_witid_end_point_with_json_data() throws Exception {
		mvc.perform(get(SECURE_PATH + "/find?id=1&jobTitle=Software Engg").headers(getBasicAuthHeader())
				.header("Accept", MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("jobId", is(1))).andExpect(jsonPath("jobTitle", is("Software Engg")));

	}

	@Test
	public void test_Get_witid_end_point_with_xml_data() throws Exception {
		mvc.perform(get(SECURE_PATH + "/find?id=1&jobTitle=Software Engg").headers(getBasicAuthHeader())
				.header("Accept", MediaType.APPLICATION_XML_VALUE)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
				.andExpect(content().node(hasXPath("/Job/jobTitle", equalTo("Software Engg"))));

	}

	@Test
	public void test_Get_witid_End_point_not_found() throws Exception {
		mvc.perform(get(SECURE_PATH + "/find?id=2&jobTitle=Software Engg").headers(getBasicAuthHeader())
				.header("Accept", MediaType.APPLICATION_XML_VALUE)).andExpect(status().isNotFound());

	}

	@Test
	public void test_Get_witid_end_point_missing_query_param() throws Exception {
		mvc.perform(get(SECURE_PATH + "/find?jobTitle=Software Engg").headers(getBasicAuthHeader()).header("Accept",
				MediaType.APPLICATION_XML_VALUE)).andExpect(status().isBadRequest());

	}

	@Test
	public void test_Get_witid_end_point_missing_basic_auth_header() throws Exception {
		mvc.perform(get(SECURE_PATH + "/find?jobTitle=Software Engg").header("Accept", MediaType.APPLICATION_XML_VALUE))
				.andExpect(status().isUnauthorized());

	}
}
