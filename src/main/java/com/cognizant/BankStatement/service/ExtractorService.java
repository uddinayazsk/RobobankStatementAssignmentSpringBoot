package com.cognizant.BankStatement.service;

import java.io.File;
import java.util.List;

import com.cognizant.BankStatement.dto.Record;

/**
 * @author Adil
 *
 */
public interface ExtractorService {

	public List<Record> extractStatmentFromCSV(File file) throws Exception;
	
	public List<Record> extractStatmentFromXML(File file) throws Exception;
	
}