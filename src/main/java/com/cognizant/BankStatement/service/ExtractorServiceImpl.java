package com.cognizant.BankStatement.service;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognizant.BankStatement.dto.Record;
import com.cognizant.BankStatement.dto.Records;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;


/**
 * @author Adil
 *
 */
public class ExtractorServiceImpl implements ExtractorService {
	private Logger log = LoggerFactory.getLogger(ExtractorServiceImpl.class);

	/**
	 * @return List<Records>
	 */
	public List<Record> extractStatmentFromCSV(File file) throws Exception {
		log.info("parsing csv by Ayaz");
		log.debug("parsing csv for logger by Ayaz");
		HeaderColumnNameTranslateMappingStrategy<Record> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Record>();
		beanStrategy.setType(Record.class);

		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("Reference", "transactionRef");
		columnMapping.put("Account Number", "accountNumber");
		columnMapping.put("Description", "description");
		columnMapping.put("Start Balance", "startBalance");
		columnMapping.put("Mutation", "mutation");
		columnMapping.put("End Balance", "endBalance");

		beanStrategy.setColumnMapping(columnMapping);

		CsvToBean<Record> csvToBean = new CsvToBean<Record>();
		CSVReader reader = new CSVReader(new FileReader(file));
		List<Record> records = csvToBean.parse(beanStrategy, reader);
		//log.info(records.get);
		return records;
	}

	/**
	 * @return List<Records>
	 */
	public List<Record> extractStatmentFromXML(File file) throws Exception {
		log.info("Parsing xml files");
		  
        JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);  
   
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
        Records rootRecord= (Records) jaxbUnmarshaller.unmarshal(file);  

		return rootRecord.getRecord();
	}

}
