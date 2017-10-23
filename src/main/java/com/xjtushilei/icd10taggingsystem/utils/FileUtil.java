package com.xjtushilei.icd10taggingsystem.utils;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

/**
 * @author shilei
 * @Date 2017/10/17.
 */
public class FileUtil {

    public static void main(String file_path[]) {

        getRecommend("支气管炎");
    }

    public static List<Map<String, Object>> getRecommend(String name) {
        List<Map<String, Object>> myResult = new ArrayList<>();

        HashSet<String> result = new HashSet<>();
        if (name == null || "".equals(name)) {
            return new ArrayList<>();
        }
        String icd10Info = FileUtil.getIcd10Info();
        List<Map<String, Object>> d1 = JsonPath.read(icd10Info, "$.*");

        d1.forEach(d11 -> {
            final boolean[] flag2 = {false};
            List<Map<String, Object>> d2 = JsonPath.read(d11.get("list"), "$.*");
            d2.forEach(d22 -> {
                final boolean[] flag3 = {false};
                List<Map<String, Object>> d3 = JsonPath.read(d22.get("sanweima"), "$.*");
                d3.forEach(d33 -> {
                    final boolean[] flag4 = {false};
                    List<Map<String, Object>> d4 = JsonPath.read(d33.get("siweidaima"), "$.*");
                    d4.forEach(d44 -> {
                        if (name.equals(d44.get("name").toString()) || (name.contains(d44.get("name").toString()) && d44.get("name").toString().equals("")) ||
                                d44.get("name")
                                        .toString().contains(name)) {
                            result.add("1:<" + d11.get("name") + "> 2:<" + d22.get("name") + "> 3:<" + d33.get("name") + "> 4:<" + d44.get("name") + ">");
                            Map<String, Object> temp = new HashMap<>();
                            temp.put("l1", d11);
                            temp.put("l2", d22);
                            temp.put("l3", d33);
                            temp.put("l4", d44);
                            flag4[0] = true;
                            flag3[0] = true;
                            flag2[0] = true;
                            myResult.add(temp);
                        }
                    });
                    if ((name.equals(d33.get("name").toString()) || (name.contains(d33.get("name").toString()) &&
                            d33.get("name").toString().equals("")) || d33.get
                            ("name")
                            .toString().contains(name)) && !flag4[0]) {
                        result.add("1:<" + d11.get("name") + "> 2:<" + d22.get("name") + "> 3:<" + d33.get("name") + ">");
                        Map<String, Object> temp = new HashMap<>();
                        temp.put("l1", d11);
                        temp.put("l2", d22);
                        temp.put("l3", d33);
                        flag3[0] = true;
                        flag2[0] = true;
                        myResult.add(temp);
                    }
                });
                if ((name.equals(d22.get("name").toString()) || (name.contains(d22.get("name").toString()) && d22.get
                        ("name").toString().equals("")) || d22.get("name")
                        .toString().contains(name)) && !flag3[0]) {
                    result.add("1:<" + d11.get("name") + "> 2:<" + d22.get("name") + ">");
                    Map<String, Object> temp2 = new HashMap<>();
                    temp2.put("l1", d11);
                    temp2.put("l2", d22);
                    myResult.add(temp2);
                    flag2[0] = true;
                }
            });
            if ((name.equals(d11.get("name").toString()) || (name.contains(d11.get("name").toString()) && d11.get
                    ("name").toString().equals("")) || d11.get
                    ("name")
                    .toString().contains(name)) && !flag2[0]) {
                result.add("1:<" + d11.get("name") + ">");
                Map<String, Object> temp1 = new HashMap<>();
                temp1.put("l1", d11);
                myResult.add(temp1);
            }
        });

        return myResult;
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
