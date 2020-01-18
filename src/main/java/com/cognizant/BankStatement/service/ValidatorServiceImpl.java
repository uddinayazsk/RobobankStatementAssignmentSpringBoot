package com.cognizant.BankStatement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.cognizant.BankStatement.dto.Record;

/**
 * @author Adil
 *
 */
public class ValidatorServiceImpl implements ValidatorService {
	private Logger log = LoggerFactory.getLogger(ValidatorService.class);


		public List<Record> getDuplicateRecords(List<Record> records) {
		Map<Integer, Record> uniqeRecords = new HashMap<Integer, Record>();
		List<Record> duplicateRecords = new ArrayList<Record>();
		for (Record record : records) {
			if (uniqeRecords.containsKey(record.getTransactionRef())) {
				duplicateRecords.add(record);
			} else {
				uniqeRecords.put(record.getTransactionRef(), record);
			}
		}
		List<Record> finalDuplicateRecords = new ArrayList<Record>();
		finalDuplicateRecords.addAll(duplicateRecords);
		for (Record record : duplicateRecords) {
			if (null != uniqeRecords.get(record.getTransactionRef())) {
				finalDuplicateRecords.add(uniqeRecords.get(record.getTransactionRef()));
				uniqeRecords.remove(record.getTransactionRef());
			}
		}
		return finalDuplicateRecords;
	}

	/**
	 * @return List<Records> if startbalance - mutation != endbalance then
	 *         endbalance is wrong that list ll be returned.
	 */
	public List<Record> getEndBalanceErrorRecords(List<Record> records) {
		List<Record> endBalanceErrorRecords = new ArrayList<Record>();
		for (Record record : records) {
			if (Math.round((record.getStartBalance() - record.getMutation()) - Math.round(record.getEndBalance())) != 0) {
				endBalanceErrorRecords.add(record);
			}
		}
		log.info("endBalanceErrorRecords.size=",endBalanceErrorRecords.size());
		return endBalanceErrorRecords;
	}

}
