package com.jhmk.clouddatasource.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/7/25 11:11
 */

/**
 * 病人信息
 */
@Entity
@Table(name = "PAT_MASTER_INDEX", schema = "JHEMR", catalog = "")
public class PatMasterIndex {
    private String patientId;
    private String inpNo;
    private String name;
    private String namePhonetic;
    private String sex;
    private Time dateOfBirth;
    private String birthPlace;
    private String citizenship;
    private String nation;
    private String idNo;
    private String identity;
    private String chargeType;
    private String unitInContract;
    private String mailingAddress;
    private String zipCode;
    private String phoneNumberHome;
    private String phoneNumberBusiness;
    private String nextOfKin;
    private String relationship;
    private String nextOfKinAddr;
    private String nextOfKinZipCode;
    private String nextOfKinPhone;
    private Time lastVisitDate;
    private Boolean vipIndicator;
    private Time createDate;
    private String operator;
    private String hospitalNo;
    private String pixPatientId;
    private Long secretLevel;
    private String sexCvValue;
    private String citizenshipCvValue;
    private String nationCvValue;
    private String jiguan;
    private String homeCode;
    private String homeZip;
    private String cardNo;
    private String birthPlaceCode;
    private String jiguanCode;
    private String serviceAgencyMother;
    private String phoneNumberMother;
    private String idNoMother;
    private String nameMother;
    private String serviceAgencyFather;
    private String phoneNumberFather;
    private String idNoFather;
    private String nameFather;
    private Long visitId;
    private String isConfidential;
    private String occupation;
    private String maritalStatus;
    private String birthAddressQx;
    private String birthAddressJd;
    private String birthAddressBizCode;
    private String serviceAgency;
    private String nowAddressQx;
    private String nowAddressJd;
    private String nowZipCode;
    private String relationQx;
    private String currentWardName;
    private Boolean v6Updateerror;
    private String homeTel;
    private String workTel;
    private String workZip;

    @Id
    @Column(name = "PATIENT_ID", nullable = false, length = 16)
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "INP_NO", nullable = true, length = 20)
    public String getInpNo() {
        return inpNo;
    }

    public void setInpNo(String inpNo) {
        this.inpNo = inpNo;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 36)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "NAME_PHONETIC", nullable = true, length = 50)
    public String getNamePhonetic() {
        return namePhonetic;
    }

    public void setNamePhonetic(String namePhonetic) {
        this.namePhonetic = namePhonetic;
    }

    @Basic
    @Column(name = "SEX", nullable = true, length = 50)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "DATE_OF_BIRTH", nullable = true)
    public Time getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Time dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "BIRTH_PLACE", nullable = true, length = 200)
    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Basic
    @Column(name = "CITIZENSHIP", nullable = true, length = 28)
    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    @Basic
    @Column(name = "NATION", nullable = true, length = 10)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "ID_NO", nullable = true, length = 20)
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Basic
    @Column(name = "IDENTITY", nullable = true, length = 32)
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Basic
    @Column(name = "CHARGE_TYPE", nullable = true, length = 30)
    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    @Basic
    @Column(name = "UNIT_IN_CONTRACT", nullable = true, length = 50)
    public String getUnitInContract() {
        return unitInContract;
    }

    public void setUnitInContract(String unitInContract) {
        this.unitInContract = unitInContract;
    }

    @Basic
    @Column(name = "MAILING_ADDRESS", nullable = true, length = 200)
    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    @Basic
    @Column(name = "ZIP_CODE", nullable = true, length = 6)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "PHONE_NUMBER_HOME", nullable = true, length = 30)
    public String getPhoneNumberHome() {
        return phoneNumberHome;
    }

    public void setPhoneNumberHome(String phoneNumberHome) {
        this.phoneNumberHome = phoneNumberHome;
    }

    @Basic
    @Column(name = "PHONE_NUMBER_BUSINESS", nullable = true, length = 30)
    public String getPhoneNumberBusiness() {
        return phoneNumberBusiness;
    }

    public void setPhoneNumberBusiness(String phoneNumberBusiness) {
        this.phoneNumberBusiness = phoneNumberBusiness;
    }

    @Basic
    @Column(name = "NEXT_OF_KIN", nullable = true, length = 50)
    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    @Basic
    @Column(name = "RELATIONSHIP", nullable = true, length = 32)
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Basic
    @Column(name = "NEXT_OF_KIN_ADDR", nullable = true, length = 80)
    public String getNextOfKinAddr() {
        return nextOfKinAddr;
    }

    public void setNextOfKinAddr(String nextOfKinAddr) {
        this.nextOfKinAddr = nextOfKinAddr;
    }

    @Basic
    @Column(name = "NEXT_OF_KIN_ZIP_CODE", nullable = true, length = 6)
    public String getNextOfKinZipCode() {
        return nextOfKinZipCode;
    }

    public void setNextOfKinZipCode(String nextOfKinZipCode) {
        this.nextOfKinZipCode = nextOfKinZipCode;
    }

    @Basic
    @Column(name = "NEXT_OF_KIN_PHONE", nullable = true, length = 20)
    public String getNextOfKinPhone() {
        return nextOfKinPhone;
    }

    public void setNextOfKinPhone(String nextOfKinPhone) {
        this.nextOfKinPhone = nextOfKinPhone;
    }

    @Basic
    @Column(name = "LAST_VISIT_DATE", nullable = true)
    public Time getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Time lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    @Basic
    @Column(name = "VIP_INDICATOR", nullable = true, precision = 0)
    public Boolean getVipIndicator() {
        return vipIndicator;
    }

    public void setVipIndicator(Boolean vipIndicator) {
        this.vipIndicator = vipIndicator;
    }

    @Basic
    @Column(name = "CREATE_DATE", nullable = true)
    public Time getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Time createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "OPERATOR", nullable = true, length = 8)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Id
    @Column(name = "HOSPITAL_NO", nullable = false, length = 16)
    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    @Basic
    @Column(name = "PIX_PATIENT_ID", nullable = true, length = 32)
    public String getPixPatientId() {
        return pixPatientId;
    }

    public void setPixPatientId(String pixPatientId) {
        this.pixPatientId = pixPatientId;
    }

    @Basic
    @Column(name = "SECRET_LEVEL", nullable = true, precision = 0)
    public Long getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(Long secretLevel) {
        this.secretLevel = secretLevel;
    }

    @Basic
    @Column(name = "SEX_CV_VALUE", nullable = true, length = 8)
    public String getSexCvValue() {
        return sexCvValue;
    }

    public void setSexCvValue(String sexCvValue) {
        this.sexCvValue = sexCvValue;
    }

    @Basic
    @Column(name = "CITIZENSHIP_CV_VALUE", nullable = true, length = 28)
    public String getCitizenshipCvValue() {
        return citizenshipCvValue;
    }

    public void setCitizenshipCvValue(String citizenshipCvValue) {
        this.citizenshipCvValue = citizenshipCvValue;
    }

    @Basic
    @Column(name = "NATION_CV_VALUE", nullable = true, length = 10)
    public String getNationCvValue() {
        return nationCvValue;
    }

    public void setNationCvValue(String nationCvValue) {
        this.nationCvValue = nationCvValue;
    }

    @Basic
    @Column(name = "JIGUAN", nullable = true, length = 200)
    public String getJiguan() {
        return jiguan;
    }

    public void setJiguan(String jiguan) {
        this.jiguan = jiguan;
    }

    @Basic
    @Column(name = "HOME_CODE", nullable = true, length = 16)
    public String getHomeCode() {
        return homeCode;
    }

    public void setHomeCode(String homeCode) {
        this.homeCode = homeCode;
    }

    @Basic
    @Column(name = "HOME_ZIP", nullable = true, length = 6)
    public String getHomeZip() {
        return homeZip;
    }

    public void setHomeZip(String homeZip) {
        this.homeZip = homeZip;
    }

    @Basic
    @Column(name = "CARD_NO", nullable = true, length = 18)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Basic
    @Column(name = "BIRTH_PLACE_CODE", nullable = true, length = 20)
    public String getBirthPlaceCode() {
        return birthPlaceCode;
    }

    public void setBirthPlaceCode(String birthPlaceCode) {
        this.birthPlaceCode = birthPlaceCode;
    }

    @Basic
    @Column(name = "JIGUAN_CODE", nullable = true, length = 20)
    public String getJiguanCode() {
        return jiguanCode;
    }

    public void setJiguanCode(String jiguanCode) {
        this.jiguanCode = jiguanCode;
    }

    @Basic
    @Column(name = "SERVICE_AGENCY_MOTHER", nullable = true, length = 40)
    public String getServiceAgencyMother() {
        return serviceAgencyMother;
    }

    public void setServiceAgencyMother(String serviceAgencyMother) {
        this.serviceAgencyMother = serviceAgencyMother;
    }

    @Basic
    @Column(name = "PHONE_NUMBER_MOTHER", nullable = true, length = 40)
    public String getPhoneNumberMother() {
        return phoneNumberMother;
    }

    public void setPhoneNumberMother(String phoneNumberMother) {
        this.phoneNumberMother = phoneNumberMother;
    }

    @Basic
    @Column(name = "ID_NO_MOTHER", nullable = true, length = 20)
    public String getIdNoMother() {
        return idNoMother;
    }

    public void setIdNoMother(String idNoMother) {
        this.idNoMother = idNoMother;
    }

    @Basic
    @Column(name = "NAME_MOTHER", nullable = true, length = 20)
    public String getNameMother() {
        return nameMother;
    }

    public void setNameMother(String nameMother) {
        this.nameMother = nameMother;
    }

    @Basic
    @Column(name = "SERVICE_AGENCY_FATHER", nullable = true, length = 40)
    public String getServiceAgencyFather() {
        return serviceAgencyFather;
    }

    public void setServiceAgencyFather(String serviceAgencyFather) {
        this.serviceAgencyFather = serviceAgencyFather;
    }

    @Basic
    @Column(name = "PHONE_NUMBER_FATHER", nullable = true, length = 30)
    public String getPhoneNumberFather() {
        return phoneNumberFather;
    }

    public void setPhoneNumberFather(String phoneNumberFather) {
        this.phoneNumberFather = phoneNumberFather;
    }

    @Basic
    @Column(name = "ID_NO_FATHER", nullable = true, length = 30)
    public String getIdNoFather() {
        return idNoFather;
    }

    public void setIdNoFather(String idNoFather) {
        this.idNoFather = idNoFather;
    }

    @Basic
    @Column(name = "NAME_FATHER", nullable = true, length = 20)
    public String getNameFather() {
        return nameFather;
    }

    public void setNameFather(String nameFather) {
        this.nameFather = nameFather;
    }

    @Basic
    @Column(name = "VISIT_ID", nullable = true, precision = 0)
    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    @Basic
    @Column(name = "IS_CONFIDENTIAL", nullable = true, length = 20)
    public String getIsConfidential() {
        return isConfidential;
    }

    public void setIsConfidential(String isConfidential) {
        this.isConfidential = isConfidential;
    }

    @Basic
    @Column(name = "OCCUPATION", nullable = true, length = 20)
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Basic
    @Column(name = "MARITAL_STATUS", nullable = true, length = 20)
    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Basic
    @Column(name = "BIRTH_ADDRESS_QX", nullable = true, length = 60)
    public String getBirthAddressQx() {
        return birthAddressQx;
    }

    public void setBirthAddressQx(String birthAddressQx) {
        this.birthAddressQx = birthAddressQx;
    }

    @Basic
    @Column(name = "BIRTH_ADDRESS_JD", nullable = true, length = 100)
    public String getBirthAddressJd() {
        return birthAddressJd;
    }

    public void setBirthAddressJd(String birthAddressJd) {
        this.birthAddressJd = birthAddressJd;
    }

    @Basic
    @Column(name = "BIRTH_ADDRESS_BIZ_CODE", nullable = true, length = 60)
    public String getBirthAddressBizCode() {
        return birthAddressBizCode;
    }

    public void setBirthAddressBizCode(String birthAddressBizCode) {
        this.birthAddressBizCode = birthAddressBizCode;
    }

    @Basic
    @Column(name = "SERVICE_AGENCY", nullable = true, length = 70)
    public String getServiceAgency() {
        return serviceAgency;
    }

    public void setServiceAgency(String serviceAgency) {
        this.serviceAgency = serviceAgency;
    }

    @Basic
    @Column(name = "NOW_ADDRESS_QX", nullable = true, length = 60)
    public String getNowAddressQx() {
        return nowAddressQx;
    }

    public void setNowAddressQx(String nowAddressQx) {
        this.nowAddressQx = nowAddressQx;
    }

    @Basic
    @Column(name = "NOW_ADDRESS_JD", nullable = true, length = 100)
    public String getNowAddressJd() {
        return nowAddressJd;
    }

    public void setNowAddressJd(String nowAddressJd) {
        this.nowAddressJd = nowAddressJd;
    }

    @Basic
    @Column(name = "NOW_ZIP_CODE", nullable = true, length = 60)
    public String getNowZipCode() {
        return nowZipCode;
    }

    public void setNowZipCode(String nowZipCode) {
        this.nowZipCode = nowZipCode;
    }

    @Basic
    @Column(name = "RELATION_QX", nullable = true, length = 60)
    public String getRelationQx() {
        return relationQx;
    }

    public void setRelationQx(String relationQx) {
        this.relationQx = relationQx;
    }

    @Basic
    @Column(name = "CURRENT_WARD_NAME", nullable = true, length = 60)
    public String getCurrentWardName() {
        return currentWardName;
    }

    public void setCurrentWardName(String currentWardName) {
        this.currentWardName = currentWardName;
    }

    @Basic
    @Column(name = "V6UPDATEERROR", nullable = true, precision = 0)
    public Boolean getV6Updateerror() {
        return v6Updateerror;
    }

    public void setV6Updateerror(Boolean v6Updateerror) {
        this.v6Updateerror = v6Updateerror;
    }

    @Basic
    @Column(name = "HOME_TEL", nullable = true, length = 18)
    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    @Basic
    @Column(name = "WORK_TEL", nullable = true, length = 18)
    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel;
    }

    @Basic
    @Column(name = "WORK_ZIP", nullable = true, length = 18)
    public String getWorkZip() {
        return workZip;
    }

    public void setWorkZip(String workZip) {
        this.workZip = workZip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatMasterIndex that = (PatMasterIndex) o;
        return Objects.equals(patientId, that.patientId) &&
                Objects.equals(inpNo, that.inpNo) &&
                Objects.equals(name, that.name) &&
                Objects.equals(namePhonetic, that.namePhonetic) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(dateOfBirth, that.dateOfBirth) &&
                Objects.equals(birthPlace, that.birthPlace) &&
                Objects.equals(citizenship, that.citizenship) &&
                Objects.equals(nation, that.nation) &&
                Objects.equals(idNo, that.idNo) &&
                Objects.equals(identity, that.identity) &&
                Objects.equals(chargeType, that.chargeType) &&
                Objects.equals(unitInContract, that.unitInContract) &&
                Objects.equals(mailingAddress, that.mailingAddress) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(phoneNumberHome, that.phoneNumberHome) &&
                Objects.equals(phoneNumberBusiness, that.phoneNumberBusiness) &&
                Objects.equals(nextOfKin, that.nextOfKin) &&
                Objects.equals(relationship, that.relationship) &&
                Objects.equals(nextOfKinAddr, that.nextOfKinAddr) &&
                Objects.equals(nextOfKinZipCode, that.nextOfKinZipCode) &&
                Objects.equals(nextOfKinPhone, that.nextOfKinPhone) &&
                Objects.equals(lastVisitDate, that.lastVisitDate) &&
                Objects.equals(vipIndicator, that.vipIndicator) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(hospitalNo, that.hospitalNo) &&
                Objects.equals(pixPatientId, that.pixPatientId) &&
                Objects.equals(secretLevel, that.secretLevel) &&
                Objects.equals(sexCvValue, that.sexCvValue) &&
                Objects.equals(citizenshipCvValue, that.citizenshipCvValue) &&
                Objects.equals(nationCvValue, that.nationCvValue) &&
                Objects.equals(jiguan, that.jiguan) &&
                Objects.equals(homeCode, that.homeCode) &&
                Objects.equals(homeZip, that.homeZip) &&
                Objects.equals(cardNo, that.cardNo) &&
                Objects.equals(birthPlaceCode, that.birthPlaceCode) &&
                Objects.equals(jiguanCode, that.jiguanCode) &&
                Objects.equals(serviceAgencyMother, that.serviceAgencyMother) &&
                Objects.equals(phoneNumberMother, that.phoneNumberMother) &&
                Objects.equals(idNoMother, that.idNoMother) &&
                Objects.equals(nameMother, that.nameMother) &&
                Objects.equals(serviceAgencyFather, that.serviceAgencyFather) &&
                Objects.equals(phoneNumberFather, that.phoneNumberFather) &&
                Objects.equals(idNoFather, that.idNoFather) &&
                Objects.equals(nameFather, that.nameFather) &&
                Objects.equals(visitId, that.visitId) &&
                Objects.equals(isConfidential, that.isConfidential) &&
                Objects.equals(occupation, that.occupation) &&
                Objects.equals(maritalStatus, that.maritalStatus) &&
                Objects.equals(birthAddressQx, that.birthAddressQx) &&
                Objects.equals(birthAddressJd, that.birthAddressJd) &&
                Objects.equals(birthAddressBizCode, that.birthAddressBizCode) &&
                Objects.equals(serviceAgency, that.serviceAgency) &&
                Objects.equals(nowAddressQx, that.nowAddressQx) &&
                Objects.equals(nowAddressJd, that.nowAddressJd) &&
                Objects.equals(nowZipCode, that.nowZipCode) &&
                Objects.equals(relationQx, that.relationQx) &&
                Objects.equals(currentWardName, that.currentWardName) &&
                Objects.equals(v6Updateerror, that.v6Updateerror) &&
                Objects.equals(homeTel, that.homeTel) &&
                Objects.equals(workTel, that.workTel) &&
                Objects.equals(workZip, that.workZip);
    }

    @Override
    public int hashCode() {

        return Objects.hash(patientId, inpNo, name, namePhonetic, sex, dateOfBirth, birthPlace, citizenship, nation, idNo, identity, chargeType, unitInContract, mailingAddress, zipCode, phoneNumberHome, phoneNumberBusiness, nextOfKin, relationship, nextOfKinAddr, nextOfKinZipCode, nextOfKinPhone, lastVisitDate, vipIndicator, createDate, operator, hospitalNo, pixPatientId, secretLevel, sexCvValue, citizenshipCvValue, nationCvValue, jiguan, homeCode, homeZip, cardNo, birthPlaceCode, jiguanCode, serviceAgencyMother, phoneNumberMother, idNoMother, nameMother, serviceAgencyFather, phoneNumberFather, idNoFather, nameFather, visitId, isConfidential, occupation, maritalStatus, birthAddressQx, birthAddressJd, birthAddressBizCode, serviceAgency, nowAddressQx, nowAddressJd, nowZipCode, relationQx, currentWardName, v6Updateerror, homeTel, workTel, workZip);
    }
}
