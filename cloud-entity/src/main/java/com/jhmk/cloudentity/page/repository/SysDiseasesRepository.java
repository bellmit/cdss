package com.jhmk.cloudentity.page.repository;

import com.jhmk.cloudentity.page.bean.SysDiseases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

public interface SysDiseasesRepository extends JpaRepository<SysDiseases, Integer>, JpaSpecificationExecutor<SysDiseases> {
    @Query("select s.chinaDiseases from SysDiseases s where s.engDiseases like CONCAT('%',?1,'%')")
    List<String> findAllByEngDiseases(String enddiseases);

    @Query("select s.chinaDiseases from SysDiseases s where s.chinaDiseases like CONCAT('%',?1,'%')and s.engDiseases like CONCAT('%',?2,'%')")
    List<String> findAllByChinaDiseasesAndEngDiseases(String chinese, String enddiseases);
}
