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

    public static void main(String file_path[]) throws IOException {

        CSVParser records = null;
        try {
            File file = ResourceUtils.getFile("classpath:data/_diagnosis_merge_unique.csv");
            Reader in = new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF-8");
            records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int[] all = {0};
        final int[] success = {0};
        final int[] fail = {0};
        List<String> list= new ArrayList<>();
        Iterator<CSVRecord> it = records.iterator();
        while (it.hasNext()){
            all[0]++;
            CSVRecord csv=it.next();
//            if (Integer.parseInt(csv.get(0).trim())>100){
//                break;
//            }

            Map<String ,List> map =getRecommend(csv.get(1).trim());
            if (map.get("containsResult").size()>0||map.get("similarityResult").size()>0){
                success[0]++;
            }
            else {
                fail[0]++;
                list.add(csv.get(1));
            }
        }
        StringBuffer a =new StringBuffer();
        for(int i=0;i<list.size();i++)
        {
            a.append(list.get(i)+"\n");
        }
        System.out.println(all[0]);
        System.out.println(success[0]);
        System.out.println(fail[0]);

        org.aspectj.util.FileUtil.writeAsString(new File("D://a.txt"),a.toString());

    }


    public static Map<String ,List> getRecommend(String name) {
//        if (isPhonticName(name)){
//            System.out.println("全字母:"+name);
//        }
        List<Map<String, Object>> containsResult = new ArrayList<>();

        List<Map<String, Object>> similarityResult = new ArrayList<>();

        if (name == null || "".equals(name)) {
            HashMap<String ,List> map = new HashMap<>();
            map.put("containsResult", new ArrayList<>());
            map.put("similarityResult", new ArrayList<>());
            return map;
        }
        String icd10Info = FileUtil.getIcd10Info();
        List<Map<String, Object>> d1 = JsonPath.read(icd10Info, "$.*");

        d1.forEach(d11 -> {
            final boolean[] flag2 = {false};
            final boolean[] flag2similarity = {false};
            List<Map<String, Object>> d2 = JsonPath.read(d11.get("list"), "$.*");
            d2.forEach(d22 -> {
                final boolean[] flag3 = {false};
                final boolean[] flag3similarity = {false};

                List<Map<String, Object>> d3 = JsonPath.read(d22.get("sanweima"), "$.*");
                d3.forEach(d33 -> {
                    final boolean[] flag4 = {false};
                    final boolean[] flag4similarity = {false};
                    final int[] count4similarity = {0};
                    final int[] count3similarity = {0};
                    List<Map<String, Object>> d4 = JsonPath.read(d33.get("siweidaima"), "$.*");
                    d4.forEach(d44 -> {
                        if (SimilarityAlgorithm.getContainSimilarity(name, d44.get("name").toString())) {
                            flag4[0] = true;
                            flag3[0] = true;
                            flag2[0] = true;
                        }
                        else if (SimilarityAlgorithm.getSimilarityCount(name, d44.get("name").toString())>0) {
                            flag2similarity[0] = true;
                            flag3similarity[0] = true;
                            flag4similarity[0] = true;
                        }
                    });
                    if (SimilarityAlgorithm.getContainSimilarity(name, d33.get("name").toString()) || flag4[0]) {
                        Map<String, Object> ContainSTemp = new HashMap<>();
                        ContainSTemp.put("l1", d11);
                        ContainSTemp.put("l2", d22);
                        ContainSTemp.put("l3", d33);
                        flag3[0] = true;
                        flag2[0] = true;
                        containsResult.add(ContainSTemp);
                    }
                    else if (SimilarityAlgorithm.getSimilarityCount(name, d33.get("name").toString())>0 ||
                            flag4similarity[0]) {
                        Map<String, Object> temp = new HashMap<>();
                        temp.put("l1", d11);
                        temp.put("l2", d22);
                        temp.put("l3", d33);
                        count3similarity[0]=SimilarityAlgorithm.getSimilarityCount(name, d33.get("name").toString())
                                +count4similarity[0]
                        ;
                        temp.put("count", count3similarity[0]);
                        flag3similarity[0] = true;
                        flag2similarity[0] = true;
                        similarityResult.add(temp);
                    }
                });
                if (SimilarityAlgorithm.getContainSimilarity(name, d22.get("name").toString()) && !flag3[0]) {
                    Map<String, Object> ContainSTemp = new HashMap<>();
                    ContainSTemp.put("l1", d11);
                    ContainSTemp.put("l2", d22);
                    containsResult.add(ContainSTemp);
                    flag2[0] = true;
                }
                else if (SimilarityAlgorithm.getSimilarityCount(name, d22.get("name").toString())>0 &&
                        !flag3similarity[0]) {
                    Map<String, Object> temp2 = new HashMap<>();
                    temp2.put("l1", d11);
                    temp2.put("l2", d22);
                    temp2.put("count", 0);
                    similarityResult.add(temp2);
                    flag2similarity[0] = true;
                }
            });
            if (SimilarityAlgorithm.getContainSimilarity(name, d11.get("name").toString()) && !flag2[0]) {
                Map<String, Object> temp1 = new HashMap<>();
                temp1.put("l1", d11);
                containsResult.add(temp1);
            }
            else if (SimilarityAlgorithm.getSimilarityCount(name, d11.get("name").toString()) >0&&
                    !flag2similarity[0]) {
                Map<String, Object> temp1 = new HashMap<>();
                temp1.put("l1", d11);
                temp1.put("count", 0);
                similarityResult.add(temp1);
            }
        });
        Map<String ,List> map = new HashMap<>();
        map.put("containsResult", containsResult);
        Collections.sort(similarityResult,(c1,c2)-> Integer.parseInt(c2.get("count").toString())-Integer.parseInt(c1.get("count").toString()));
        List<Map<String, Object>> similarityResultnew =new ArrayList<>();
        for(int i=0;i<12 && i<similarityResult.size();i++){
            similarityResultnew.add(similarityResult.get(i));
        }
        map.put("similarityResult", similarityResultnew);
        return map;
    }


    public static List getSimilarityListWithNCode(int n, List<Map<String, Object>> d1, String name) {
        List<Map<String, Object>> similarityResult = new ArrayList<>();
        d1.forEach(d11 -> {
            final boolean[] flag2similarity = {false};
            List<Map<String, Object>> d2 = JsonPath.read(d11.get("list"), "$.*");
            d2.forEach(d22 -> {
                final boolean[] flag3similarity = {false};
                List<Map<String, Object>> d3 = JsonPath.read(d22.get("sanweima"), "$.*");
                d3.forEach(d33 -> {
                    final int[] count4similarity = {0};
                    final int[] count3similarity = {0};
                    final boolean[] flag4similarity = {false};
                    List<Map<String, Object>> d4 = JsonPath.read(d33.get("siweidaima"), "$.*");
                    d4.forEach(d44 -> {
                        if (SimilarityAlgorithm.getSimilarityWithN(name, d44.get("name").toString(),n)) {
//                            Map<String, Object> Similaritytemp = new HashMap<>();
//                            Similaritytemp.put("l1", d11);
//                            Similaritytemp.put("l2", d22);
//                            Similaritytemp.put("l3", d33);
//                            Similaritytemp.put("l4", d44);
                            flag2similarity[0] = true;
                            flag3similarity[0] = true;
                            flag4similarity[0] = true;
//                            similarityResult.add(Similaritytemp);
                        }
                    });
                    if (SimilarityAlgorithm.getSimilarityWithN(name, d33.get("name").toString(),n) ||
                            flag4similarity[0]) {
                        Map<String, Object> Similaritytemp = new HashMap<>();
                        Similaritytemp.put("l1", d11);
                        Similaritytemp.put("l2", d22);
                        Similaritytemp.put("l3", d33);
                        flag3similarity[0] = true;
                        flag2similarity[0] = true;
                        similarityResult.add(Similaritytemp);
                    }
                });
                if (SimilarityAlgorithm.getSimilarityWithN(name, d22.get("name").toString(),n) && !flag3similarity[0]) {
                    Map<String, Object> Similaritytemp = new HashMap<>();
                    Similaritytemp.put("l1", d11);
                    Similaritytemp.put("l2", d22);
                    similarityResult.add(Similaritytemp);
                    flag2similarity[0] = true;
                }
            });
            if (SimilarityAlgorithm.getSimilarityWithN(name, d11.get("name").toString(),n) && !flag2similarity[0]) {
                Map<String, Object> Similaritytemp = new HashMap<>();
                Similaritytemp.put("l1", d11);
                similarityResult.add(Similaritytemp);
            }
        });
        return similarityResult;
    }

    public static CSVParser getCsv() {
        CSVParser records = null;
        try {
            File file = ResourceUtils.getFile("classpath:data/a.csv");
            Reader in = new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF-8");
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

    /**
     * 用来判断一个字串中字符是否全为字母
     */
    public static boolean isPhonticName(String str) {
        char[] chars=str.toCharArray();
        boolean isPhontic = false;
        for(int i = 0; i < chars.length; i++) {
            isPhontic = (chars[i] >= 'a' && chars[i] <= 'z') || (chars[i] >= 'A' && chars[i] <= 'Z');
            if (!isPhontic) {
                return false;
            }
        }
        return true;
    }

}
