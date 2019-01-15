package com.jhmk.cloudentity.earlywaring.entity.rule;

import com.jhmk.cloudutil.util.StringUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/10/30 18:06
 */

@Entity
@Table(name = "rule_yizhu")
public class Yizhu implements Serializable{
    private int id;
    private String patient_id;
    private String visit_id;
    private String inp_no;
    private String order_no;
    private String order_class_name;
    private String order_class_code;
    private String order_item_name;
    private String order_item_code;
    private String order_status_name;
    private String order_begin_time;
    private String order_end_time;
    private String frequency_name;
    private String frequency_code;
    private String dosage_form;
    private String specification;
    private String drug_amount_value;
    private String order_properties_name;
    private String duration_value;
    private String dosage_value;
    private String total_dosage_value;
    private String total_dosage_unit;
    private String status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "patient_id", nullable = false, length = 50)
    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    @Basic
    @Column(name = "visit_id", nullable = false, length = 50)
    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visitId) {
        this.visit_id = visitId;
    }

    @Basic
    @Column(name = "inp_no", nullable = true, length = 50)
    public String getInp_no() {
        return inp_no;
    }

    public void setInp_no(String inp_no) {
        this.inp_no = inp_no;
    }

    @Basic
    @Column(name = "order_no", nullable = true, length = 50)
    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    @Basic
    @Column(name = "order_class_name", nullable = true, length = 50)
    public String getOrder_class_name() {
        return order_class_name;
    }

    public void setOrder_class_name(String order_class_name) {
        this.order_class_name = order_class_name;
    }

    @Basic
    @Column(name = "order_class_code", nullable = true, length = 50)
    public String getOrder_class_code() {
        return order_class_code;
    }

    public void setOrder_class_code(String order_class_code) {
        this.order_class_code = order_class_code;
    }

    @Basic
    @Column(name = "order_item_name", nullable = false, length = 50)
    public String getOrder_item_name() {
        return order_item_name;
    }

    public void setOrder_item_name(String order_item_name) {
        this.order_item_name = StringUtil.stringTransform(order_item_name);
    }

    @Basic
    @Column(name = "order_item_code", nullable = true, length = 50)
    public String getOrder_item_code() {
        return order_item_code;
    }

    public void setOrder_item_code(String order_item_code) {
        this.order_item_code = order_item_code;
    }

    @Basic
    @Column(name = "order_status_name", nullable = true, length = 50)
    public String getOrder_status_name() {
        return order_status_name;
    }

    public void setOrder_status_name(String order_status_name) {
        this.order_status_name = order_status_name;
    }

    @Basic
    @Column(name = "order_begin_time", nullable = true, length = 50)
    public String getOrder_begin_time() {
        return order_begin_time;
    }

    public void setOrder_begin_time(String order_begin_time) {
        this.order_begin_time = order_begin_time;
    }

    @Basic
    @Column(name = "order_end_time", nullable = true, length = 50)
    public String getOrder_end_time() {
        return order_end_time;
    }

    public void setOrder_end_time(String order_end_time) {
        this.order_end_time = order_end_time;
    }

    @Basic
    @Column(name = "frequency_name", nullable = true, length = 50)
    public String getFrequency_name() {
        return frequency_name;
    }

    public void setFrequency_name(String frequency_name) {
        this.frequency_name = frequency_name;
    }

    @Basic
    @Column(name = "frequency_code", nullable = true, length = 50)
    public String getFrequency_code() {
        return frequency_code;
    }

    public void setFrequency_code(String frequency_code) {
        this.frequency_code = frequency_code;
    }

    @Basic
    @Column(name = "dosage_form", nullable = true, length = 50)
    public String getDosage_form() {
        return dosage_form;
    }

    public void setDosage_form(String dosage_form) {
        this.dosage_form = dosage_form;
    }

    @Basic
    @Column(name = "specification", nullable = true, length = 50)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Basic
    @Column(name = "drug_amount_value", nullable = true, length = 50)
    public String getDrug_amount_value() {
        return drug_amount_value;
    }

    public void setDrug_amount_value(String drug_amount_value) {
        this.drug_amount_value = drug_amount_value;
    }

    @Basic
    @Column(name = "order_properties_name", nullable = true, length = 50)
    public String getOrder_properties_name() {
        return order_properties_name;
    }

    public void setOrder_properties_name(String order_properties_name) {
        this.order_properties_name = order_properties_name;
    }

    @Basic
    @Column(name = "duration_value", nullable = true, length = 50)
    public String getDuration_value() {
        return duration_value;
    }

    public void setDuration_value(String duration_value) {
        this.duration_value = duration_value;
    }

    @Basic
    @Column(name = "dosage_value", nullable = true, length = 50)
    public String getDosage_value() {
        return dosage_value;
    }

    public void setDosage_value(String dosage_value) {
        this.dosage_value = dosage_value;
    }

    @Basic
    @Column(name = "total_dosage_value", nullable = true, length = 50)
    public String getTotal_dosage_value() {
        return total_dosage_value;
    }

    public void setTotal_dosage_value(String total_dosage_value) {
        this.total_dosage_value = total_dosage_value;
    }

    @Basic
    @Column(name = "total_dosage_unit", nullable = true, length = 50)
    public String getTotal_dosage_unit() {
        return total_dosage_unit;
    }

    public void setTotal_dosage_unit(String total_dosage_unit) {
        this.total_dosage_unit = total_dosage_unit;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 50)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Yizhu yizhu = (Yizhu) o;
        return id == yizhu.id &&
                Objects.equals(patient_id, yizhu.patient_id) &&
                Objects.equals(visit_id, yizhu.visit_id) &&
                Objects.equals(inp_no, yizhu.inp_no) &&
                Objects.equals(order_no, yizhu.order_no) &&
                Objects.equals(order_class_name, yizhu.order_class_name) &&
                Objects.equals(order_class_code, yizhu.order_class_code) &&
                Objects.equals(order_item_name, yizhu.order_item_name) &&
                Objects.equals(order_item_code, yizhu.order_item_code) &&
                Objects.equals(order_status_name, yizhu.order_status_name) &&
                Objects.equals(order_begin_time, yizhu.order_begin_time) &&
                Objects.equals(order_end_time, yizhu.order_end_time) &&
                Objects.equals(frequency_name, yizhu.frequency_name) &&
                Objects.equals(frequency_code, yizhu.frequency_code) &&
                Objects.equals(dosage_form, yizhu.dosage_form) &&
                Objects.equals(specification, yizhu.specification) &&
                Objects.equals(drug_amount_value, yizhu.drug_amount_value) &&
                Objects.equals(order_properties_name, yizhu.order_properties_name) &&
                Objects.equals(duration_value, yizhu.duration_value) &&
                Objects.equals(dosage_value, yizhu.dosage_value) &&
                Objects.equals(total_dosage_value, yizhu.total_dosage_value) &&
                Objects.equals(total_dosage_unit, yizhu.total_dosage_unit) &&
                Objects.equals(status, yizhu.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, patient_id, visit_id, inp_no, order_no, order_class_name, order_class_code, order_item_name, order_item_code, order_status_name, order_begin_time, order_end_time, frequency_name, frequency_code, dosage_form, specification, drug_amount_value, order_properties_name, duration_value, dosage_value, total_dosage_value, total_dosage_unit, status);
    }


    public static void main(String[] args) {
        Yizhu yizhu=new Yizhu();
        yizhu.setOrder_item_name("(eqw)");
        System.out.println(yizhu.getOrder_item_name());
    }
}
