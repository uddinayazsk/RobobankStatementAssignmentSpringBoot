package com.cognizant.BankStatement.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cognizant.BankStatement.BankStatementApplication;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankStatementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankStatementApplicationTest {

	@Test
	public void applicationContextLoaded() {
		BankStatementApplication.main(new String[] {});
	}
	
}
