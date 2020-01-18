package com.cognizant.BankStatement.service;

import java.util.List;

import com.cognizant.BankStatement.dto.Record;

public interface ValidatorService {
	
	public List<Record> getDuplicateRecords(List<Record> records);
	
	public List<Record> getEndBalanceErrorRecords(List<Record> records);

}
