package com.xjtushilei.icd10taggingsystem.repository;


import com.xjtushilei.icd10taggingsystem.entity.Tagging;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by shilei on 2017/3/13.
 */
public interface TaggingRepository extends JpaRepository<Tagging, String> {

}
