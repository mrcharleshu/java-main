package com.charles.region;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class RegionParser {
    private static final Logger logger = LoggerFactory.getLogger(RegionParser.class);
    private static final String ENCODING = "UTF-8";
    private static final String FILE_PATH_1
        = "/Users/Charles/Documents/workspace/IntelliJIDEA/springbootdemo/src/test/java/com/example/region/xzqh_1.txt";
    private static final String FILE_PATH_2
        = "/Users/Charles/Documents/workspace/IntelliJIDEA/springbootdemo/src/test/java/com/example/region/xzqh_2.txt";

    public static void main(String[] args) {
        parseNotSplitedLine();
    }

    private static void parseNotSplitedLine() {
        List<String> areaList = readTxtFile(FILE_PATH_2);
        String code, name;
        boolean isProvince, isCity;
        Province province = null;
        City city = null;
        County county;
        Region region = new Region();
        for (String entry : areaList) {
            code = entry.substring(0, 6);
            //2个空格并且下个字符不为空
            isProvince = entry.substring(6, 8).equals("  ") && !entry.substring(8, 9).equals(" ");
            //4个空格
            isCity = entry.substring(6, 10).equals("    ") && !entry.substring(10, 11).equals(" ");
//            System.out.println("IsProvince: " + entry.substring(6, 8) + "|" + isProvince + " # IsCity: " + entry.substring(6, 10) + "|" + isCity + " # " + entry);
            if (isProvince) {
                name = entry.substring(8);
                province = new Province(code, name);
                region.getProvinces().add(province);
            } else if (isCity) {
                name = entry.substring(10);
                city = new City(code, name);
                province.getCities().add(city);
            } else {
                name = entry.substring(12);
                county = new County(code, name);
                city.getCounties().add(county);
            }
        }
        region.toSQL();
        //System.out.println(region);
    }

    private static void parseSplitedLine() {
        List<String> areaList = readTxtFile(FILE_PATH_1);
        SortedMap<String, String> areaMap = Maps.newTreeMap();
        for (int i = 0; i < areaList.size(); i += 2) {
            areaMap.put(areaList.get(i), areaList.get(i + 1));
        }
        String code, name;
        boolean isProvince, isCity;
        Province province = null;
        City city = null;
        County county;
        Region region = new Region();
        for (Map.Entry<String, String> entry : areaMap.entrySet()) {
            code = entry.getKey();
            name = entry.getValue();
            isProvince = code.substring(3, 6).equals("000");
            isCity = code.substring(4, 6).equals("00");
            //System.out.println("IsProvince: " + code.substring(3, 6) + "|" + isProvince + " # IsCity: " + code.substring(4, 6) + "|" + isCity + " # " + entry);
            if (isProvince) {
                province = new Province(code, name);
                region.getProvinces().add(province);
            } else if (isCity) {
                city = new City(code, name);
                province.getCities().add(city);
            } else {
                county = new County(code, name);
                city.getCounties().add(county);
            }
        }
        System.out.println(region);
    }

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     */
    public static List<String> readTxtFile(String filePath) {
        List<String> list = Lists.newArrayList();
        try {
            File file = new File(filePath);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), ENCODING);//考虑到编码格式
            if (file.isFile() && file.exists()) { //判断文件是否存在
                BufferedReader br = new BufferedReader(isr);
                String lineTxt;
                while ((lineTxt = br.readLine()) != null) {
                    // System.out.println(lineTxt);
                    list.add(lineTxt);
                }
                isr.close();
            } else {
                logger.error("找不到指定的文件");
            }
        } catch (Exception e) {
            logger.error("读取文件内容出错");
            e.printStackTrace();
        }
        return list;
    }
}

