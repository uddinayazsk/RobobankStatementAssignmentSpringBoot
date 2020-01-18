package com.cognizant.BankStatement.test;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.cognizant.BankStatement.constant.TestCaseUtil;
import com.cognizant.BankStatement.dto.Record;
import com.cognizant.BankStatement.service.ExtractorService;
import com.cognizant.BankStatement.service.ExtractorServiceImpl;
import com.cognizant.BankStatement.service.ValidatorService;
import com.cognizant.BankStatement.service.ValidatorServiceImpl;

import junit.framework.Assert;


/**
 * @author Adil
 *
 */
public class StatementProcessContollerTest {

	/**
	 * Type : Positive 
	 * scenario : Duplicate check in input files
	 * Statement
	 */
	@Test
	public void getDuplicateRecordsTestCaseWithDuplilcate() {
		List<Record> inputList = Arrays.asList(
				new Record(172833, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),
				new Record(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.61));
		ValidatorService validatorServiceImpl = new ValidatorServiceImpl();
		List<Record> duplicateRecords = validatorServiceImpl.getDuplicateRecords(inputList);
		assertEquals(inputList.size(), duplicateRecords.size());

	}

	/**
	 * Type : Negative 
	 * scenario : Duplicate check in Statement
	 */
	@Test
	public void getDuplicateRecordsTestCaseWithOutDuplilcate() {
		List<Record> inputList = Arrays.asList(
				new Record(172823, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),
				new Record(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.61));
		ValidatorService validatorServiceImpl = new ValidatorServiceImpl();
		List<Record> duplicateRecords = validatorServiceImpl.getDuplicateRecords(inputList);
		assertEquals(0, duplicateRecords.size());

	}

	/**
	 * Type : Positive 
	 * scenario : EndBalance validation in Statement
	 */
	@Test
	public void getEndBalanceErrorRecordsTestCaseWithWrongValue() {
		List<Record> inputList = Arrays.asList(
				new Record(172833, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),
				new Record(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.80));
		ValidatorService validatorServiceImpl = new ValidatorServiceImpl();
		List<Record> endBalanceErrorRecords = validatorServiceImpl.getEndBalanceErrorRecords(inputList);
		assertEquals(inputList.size(), endBalanceErrorRecords.size());

	}

	/**
	 * Type : Negative 
	 * scenario : EndBalance validation in Statement
	 */
	@Test
	public void getEndBalanceErrorRecordsTestCaseWithCorrectValue() {
		List<Record> inputList = Arrays.asList(
				new Record(172833, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 108.46),
				new Record(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", -26.57));
		ValidatorService validatorServiceImpl = new ValidatorServiceImpl();
		List<Record> endBalanceErrorRecords = validatorServiceImpl.getEndBalanceErrorRecords(inputList);
		assertEquals(0, endBalanceErrorRecords.size());
	}

	/**
	 * Type : Positive 
	 * scenario : Processing the input CSV file and extracting
	 * values as DTO object for validation process
	 */
	@Test
	public void extractStatmentFromCSVTestCase() {
		ExtractorService extractorServiceImpl = new ExtractorServiceImpl();
		File inputFile = new File("records.csv");
		try {
			int totalLineInInputCSV = TestCaseUtil.getNumberOfLine(inputFile);
			List<Record> extractedRecords = extractorServiceImpl.extractStatmentFromCSV(inputFile);
			assertEquals(totalLineInInputCSV-1, extractedRecords.size());
		} catch (IOException e) {
			Assert.fail("File processing error!!" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Type : Positive 
	 * scenario : Processing the input XML file and extracting
	 * values as DTO object for validation process
	 */
	@Test
	public void extractStatmentFromXMLTestCase() {
		ExtractorService extractorServiceImpl = new ExtractorServiceImpl();
		File inputFile = new File("records.xml");
		try {
			int totalLineInInputXML = 10; /// let. input XML file has 10 records.
			List<Record> extractedRecords = extractorServiceImpl.extractStatmentFromXML(inputFile);
			assertEquals(totalLineInInputXML, extractedRecords.size());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}
}
