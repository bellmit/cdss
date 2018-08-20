package com.jhmk.clouddatasource.entity.repository;

import com.jhmk.clouddatasource.CloudDatasourceApplication;
import com.jhmk.clouddatasource.entity.PatDiagnosis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ziyu.zhou
 * @date 2018/7/25 10:02
 */
@SpringBootTest(classes = CloudDatasourceApplication.class)
@RunWith(SpringRunner.class)
public class PatDiagnosisRepositoryTest {

    @Autowired
    PatDiagnosisRepository repository;

    @Test
    public void contextLoads() {
        List<PatDiagnosis> allByParentIdAndVisitId = repository.findAllByPatientIdAndVisitId("000A35212300", Long.valueOf(2));
        System.out.println(1);
    }
}