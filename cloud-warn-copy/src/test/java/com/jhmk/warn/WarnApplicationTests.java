package com.jhmk.warn;

import com.jhmk.cloudentity.earlywaring.entity.SmShowLog;
import com.jhmk.cloudentity.earlywaring.entity.repository.SmShowLogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WarnApplicationTests {
	@Autowired
	SmShowLogRepository repository;

	@Test
	public void contextLoads() {
		int i = repository.updateSmHospitalById(0, 43, "2018-10-25 19:12:59", 51);
		System.out.println(i);
	}

}
