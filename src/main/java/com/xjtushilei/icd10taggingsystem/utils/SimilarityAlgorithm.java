package com.xjtushilei.icd10taggingsystem.utils;

/**
 * @author shilei
 * @Date 2017/10/23.
 */
public class SimilarityAlgorithm {
    public static void main(String file_path[]) {
        System.out.println(getSimilarity("上感", "上呼吸道感染"));
    }


    public static boolean getSimilarity(String miaoshuName, String icd10Name) {
        int count = 0;
        for (int i = 0; i < miaoshuName.length(); i++) {
            if (icd10Name.contains(miaoshuName.charAt(i) + "")) {
                count++;
            }
        }
        if (count >= 2) {
            return true;
        }
        return false;
    }
    public static int getSimilarityCount(String miaoshuName, String icd10Name) {
        int count = 0;
        for (int i = 0; i < miaoshuName.length(); i++) {
            if (icd10Name.contains(miaoshuName.charAt(i) + "")) {
                count++;
            }
        }
        return count;
    }
    public static boolean getSimilarityWithN(String miaoshuName, String icd10Name,int n) {
        int count = 0;
        for (int i = 0; i < miaoshuName.length(); i++) {
            if (icd10Name.contains(miaoshuName.charAt(i) + "")) {
                count++;
            }
        }
        if (count >= n) {
            return true;
        }
        return false;
    }

    public static boolean getContainSimilarity(String miaoshuName, String icd10Name) {

//        if (icd10Name.contains(miaoshuName) || (miaoshuName.contains(icd10Name) && !icd10Name.equals(""))) {
        if (icd10Name.contains(miaoshuName) || miaoshuName.contains(icd10Name)) {
            return true;
        }
        return false;

    }
}
