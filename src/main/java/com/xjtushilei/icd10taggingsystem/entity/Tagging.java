package com.xjtushilei.icd10taggingsystem.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author shilei
 * @Date 2017/10/17.
 */
@Entity
public class Tagging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String taggingDiseaseCode;

    private String taggingDiseaseName;

    private String categoryId;
    private String categoryCode;
    private String categoryName;

    private String firstCode;
    private String firstName;

    private String secondCode;
    private String secondName;

    private String thirdCode;
    private String thirdName;

    public Tagging() {
    }

    @Override
    public String toString() {
        return "Tagging{" +
                "id=" + id +
                ", taggingDiseaseCode='" + taggingDiseaseCode + '\'' +
                ", taggingDiseaseName='" + taggingDiseaseName + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", firstCode='" + firstCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondCode='" + secondCode + '\'' +
                ", secondName='" + secondName + '\'' +
                ", thirdCode='" + thirdCode + '\'' +
                ", thirdName='" + thirdName + '\'' +
                '}';
    }

    public Tagging(String taggingDiseaseCode, String taggingDiseaseName, String categoryId, String categoryCode, String categoryName, String firstCode, String firstName, String secondCode, String secondName, String thirdCode, String thirdName) {
        this.taggingDiseaseCode = taggingDiseaseCode;
        this.taggingDiseaseName = taggingDiseaseName;
        this.categoryId = categoryId;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.firstCode = firstCode;
        this.firstName = firstName;
        this.secondCode = secondCode;
        this.secondName = secondName;
        this.thirdCode = thirdCode;
        this.thirdName = thirdName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaggingDiseaseCode() {
        return taggingDiseaseCode;
    }

    public void setTaggingDiseaseCode(String taggingDiseaseCode) {
        this.taggingDiseaseCode = taggingDiseaseCode;
    }

    public String getTaggingDiseaseName() {
        return taggingDiseaseName;
    }

    public void setTaggingDiseaseName(String taggingDiseaseName) {
        this.taggingDiseaseName = taggingDiseaseName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondCode() {
        return secondCode;
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdCode() {
        return thirdCode;
    }

    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }
}
