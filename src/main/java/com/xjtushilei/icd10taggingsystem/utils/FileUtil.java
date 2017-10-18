package com.xjtushilei.icd10taggingsystem.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * @author shilei
 * @Date 2017/10/17.
 */
public class FileUtil {

    public static void main(String file_path[]) {


    }

    public static CSVParser getCsv() {
        CSVParser records = null;
        try {
            File file = ResourceUtils.getFile("classpath:data/a.csv");
            Reader in = new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF-8");
            ;
            records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;

    }

    public static String getIcd10Info() {
        String icd10Json = null;
        try {
            File file = ResourceUtils.getFile("classpath:data/icd10-data.json");
            icd10Json = org.apache.commons.io.FileUtils.readFileToString(file, "utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icd10Json;
    }


}
