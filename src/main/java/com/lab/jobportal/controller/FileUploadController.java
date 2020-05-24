package com.lab.jobportal.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lab.jobportal.message.CustomMessage;

import io.swagger.annotations.Api;

/**
 * rathr1
 * 
 **/
@Api(value = "File Upload", description = "End Point to upload the File")
@RestController
@RequestMapping(path = { "/normal/webapi" })
public class FileUploadController {
	private final Logger oLog = LoggerFactory.getLogger(FileUploadController.class);
	private final String contextPath = "/normal/webapi";

	@RequestMapping(path = { "/upload" }, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file) {
		oLog.info("POST " + contextPath + "/upload ");
		String fileLocation = System.getProperty("java.io.tmpdir");
		try {
			File convertFile = new File(fileLocation + File.separator + file.getOriginalFilename());
			oLog.info(String.format("Starting the file %s upload in %s", file.getOriginalFilename(),
					fileLocation + File.separator));
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();
			return new ResponseEntity<Object>(new CustomMessage(
					String.format("File %s uploaded at %s", file.getOriginalFilename(), fileLocation + File.separator)),
					HttpStatus.OK);
		} catch (IOException e) {
			oLog.warn(e.getMessage());
			return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			oLog.error(e.getMessage());
			return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
