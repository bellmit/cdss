package com.jhmk.cloudentity.cdss.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/7/27 10:10
 * 疾病字典
 */

@Entity
@Table(name = "sys_diseases", schema = "jhmk_waring")
public class SysDiseases {
    private int id;
    private String chinaDiseases;
    private String engDiseases;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "china_diseases", nullable = false, length = 30)
    public String getChinaDiseases() {
        return chinaDiseases;
    }

    public void setChinaDiseases(String chinaDiseases) {
        this.chinaDiseases = chinaDiseases;
    }

    @Basic
    @Column(name = "eng_diseases", nullable = false, length = 30)
    public String getEngDiseases() {
        return engDiseases;
    }

    public void setEngDiseases(String engDiseases) {
        this.engDiseases = engDiseases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysDiseases that = (SysDiseases) o;
        return id == that.id &&
                Objects.equals(chinaDiseases, that.chinaDiseases) &&
                Objects.equals(engDiseases, that.engDiseases);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, chinaDiseases, engDiseases);
    }

    @Override
    public String toString() {
        return "SysDiseases{" +
                "id=" + id +
                ", chinaDiseases='" + chinaDiseases + '\'' +
                ", engDiseases='" + engDiseases + '\'' +
                '}';
    }
}
