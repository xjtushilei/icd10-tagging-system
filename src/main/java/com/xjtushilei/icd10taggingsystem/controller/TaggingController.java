package com.xjtushilei.icd10taggingsystem.controller;

import com.jayway.jsonpath.JsonPath;
import com.xjtushilei.icd10taggingsystem.entity.Tagging;
import com.xjtushilei.icd10taggingsystem.repository.TaggingRepository;
import com.xjtushilei.icd10taggingsystem.utils.FileUtil;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;


/**
 * Created by shilei on 2017-10-17 17:29:54.
 */

@RestController
@RequestMapping("/tagging")
public class TaggingController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaggingRepository taggingRepository;

    @RequestMapping(value = "/getCsv", method = RequestMethod.GET)
    public String getCsv() {
        List<Tagging> list = new ArrayList<>();
        FileUtil.getCsv().forEach(csvRecord -> {
            String taggingDiseaseId = csvRecord.get(0);
            String taggingDiseaseName = csvRecord.get(1);
            list.add(new Tagging(taggingDiseaseId, taggingDiseaseName, "", "", "", "", "", "", "", "", ""));
        });
        return list.toString();
    }

    @RequestMapping(value = "/getIcd10Info", method = RequestMethod.GET)
    public String getIcd10Info() {
        return FileUtil.getIcd10Info();
    }

    @RequestMapping(value = "/getTagging", method = RequestMethod.GET)
    public Map<String, Object> getTagging(int page, int limit) {
        page = page - 1;
        Page<Tagging> pageList = taggingRepository.findAll(new PageRequest(page, limit));
        //对 Comment 这个类中的一些字段进行过滤
        List<Tagging> taggings = new ArrayList<>();
        pageList.forEach(tag -> taggings.add(tag));
        Map<String, Object> result = new HashMap<>();
        result.put("data", taggings);
        result.put("code", 0);
        result.put("msg", "success");
        result.put("count", pageList.getTotalElements());
        return result;
    }

    @RequestMapping(value = "/updateIcd10Info", method = RequestMethod.POST)
    public Tagging updateIcd10Info(Tagging tagging) {
        Tagging result = taggingRepository.save(tagging);
        return result;
    }


    @RequestMapping(value = "/insertData", method = RequestMethod.POST)
    public List<Tagging> insertData() {
        List<Tagging> list = new ArrayList<>();
        FileUtil.getCsv().forEach(csvRecord -> {
            String taggingDiseaseId = csvRecord.get(0);
            String taggingDiseaseName = csvRecord.get(1);
            list.add(new Tagging(taggingDiseaseId, taggingDiseaseName, "", "", "", "", "", "", "", "", ""));
        });
        return taggingRepository.save(list);
    }

    @RequestMapping(value = "/Recommend", method = RequestMethod.GET)
    public ArrayList<String> Recommend(String name) {

        return FileUtil.getRecommend(name);
    }

}
