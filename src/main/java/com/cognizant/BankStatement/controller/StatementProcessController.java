package com.cognizant.BankStatement.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.BankStatement.constant.PortalConstants;
import com.cognizant.BankStatement.dto.AppResponse;
import com.cognizant.BankStatement.dto.Record;
import com.cognizant.BankStatement.service.ExtractorService;
import com.cognizant.BankStatement.service.ValidatorService;


//import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Adil
 *
 */
@RestController
@RequestMapping("/assignment")
public class StatementProcessController { 
	private org.slf4j.Logger log = LoggerFactory.getLogger(StatementProcessController.class);


	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private ExtractorService extractorService;
	
	@GetMapping
	@RequestMapping("/hi")
	public String hellofun(){
		log.debug("controller ......");
		log.info("controller ......in console");
		return "hi";
	}
	@GetMapping
	@RequestMapping("/test")
	public @ResponseBody AppResponse test() throws Exception {
		AppResponse appResponse = new AppResponse();
		return appResponse;
	}

	@PostMapping
	@RequestMapping("/bankstmt")
	public @ResponseBody AppResponse handleFileUpload(@RequestParam("file") MultipartFile multipart) throws Exception {
		log.debug("controller ...... processing");
		log.info("controller processing......in console");
		AppResponse appResponse = new AppResponse();
		if (!multipart.isEmpty()) {
			log.info(multipart.getContentType());
			if (multipart.getContentType().equalsIgnoreCase(PortalConstants.FILE_TYPE_CSV)|| multipart.getContentType().equals("application/vnd.ms-excel") ) {
				List<Record> errorRecords = new ArrayList<Record>();
				File csvFile = new File(multipart.getOriginalFilename());
				multipart.transferTo(csvFile);
				List<Record> extractedRecords = extractorService.extractStatmentFromCSV(csvFile);
				errorRecords.addAll(validatorService.getDuplicateRecords(extractedRecords));
				errorRecords.addAll(validatorService.getEndBalanceErrorRecords(extractedRecords));
				if (!errorRecords.isEmpty()) {
					appResponse.setResponseCode(PortalConstants.HTTP_CODE_SUCCESS);
					appResponse.setResponseMessage(PortalConstants.VALIDATION_ERROR);
					appResponse.setRecords(errorRecords);
				} else {
					appResponse.setResponseCode(PortalConstants.HTTP_CODE_SUCCESS);
					appResponse.setResponseMessage(PortalConstants.VALIDATION_ERROR);
				}
			} else if (multipart.getContentType().equalsIgnoreCase(PortalConstants.FILE_TYPE_XML) || multipart.getContentType().equals("text/xml")) {
				List<Record> errorRecords = new ArrayList<Record>();
				File xmlFile = new File(multipart.getOriginalFilename());
				multipart.transferTo(xmlFile);
				List<Record> extractedRecords = extractorService.extractStatmentFromXML(xmlFile);
				errorRecords.addAll(validatorService.getDuplicateRecords(extractedRecords));
				errorRecords.addAll(validatorService.getEndBalanceErrorRecords(extractedRecords));
				if (!errorRecords.isEmpty()) {
					appResponse.setResponseCode(PortalConstants.HTTP_CODE_SUCCESS);
					appResponse.setResponseMessage(PortalConstants.VALIDATION_ERROR);
					appResponse.setRecords(errorRecords);
				} else {
					appResponse.setResponseCode(PortalConstants.HTTP_CODE_SUCCESS);
					appResponse.setResponseMessage(PortalConstants.VALIDATION_ERROR);
				}
			} else {
				log.info(multipart.getContentType());

				appResponse.setResponseCode(PortalConstants.HTTP_CODE_INVALID_INPUT);
				appResponse.setResponseMessage(PortalConstants.UNSUPORTED_FILE_FORMAT);
			}
		} else {
			appResponse.setResponseCode(PortalConstants.HTTP_CODE_INVALID_INPUT);
			appResponse.setResponseMessage(PortalConstants.INVALID_INPUT);
		}
		return appResponse;
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody AppResponse handleException(HttpServletRequest request, Exception ex) {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(PortalConstants.HTTP_CODE_ERROR);
		appResponse.setResponseMessage(PortalConstants.UNEXPECTED_SERVER_ERROR);
		return appResponse;
	}

}
