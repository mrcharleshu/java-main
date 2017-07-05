package com.charles.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JavaProperties {

    public static void main(String[] args) throws IOException {
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "/src/main/resources/application.properties";
        System.out.println(getValueByKey(path, "name"));
    }

    public static String getValueByKey(String fileNamePath, String key) throws IOException {
        Properties props = new Properties();
        InputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(fileNamePath);
            // 如果将in改为下面的方法，必须要将.Properties文件和此class类文件放在同一个包中  
            //in = propertiesTools.class.getResourceAsStream(fileNamePath);  
            props.load(is);
            value = props.getProperty(key);
            // 有乱码时要进行重新编码  
            // new String(props.getProperty("name").getBytes("ISO-8859-1"), "GBK");  
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            return value;
        }
    }
}  