package com.charles.lib.file;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFile {
    public static void main(String[] args) throws Exception {
        // imageRequest("http://172.31.236.24/files/logo.png");
        // imageRequest("http://172.31.236.24/files/agv.properties");
        // imageRequest("http://172.31.236.24/public/logo-l-w.png");
        // long ms = System.currentTimeMillis();
        // String mapJson = restTemplate.getForObject("http://172.31.236.24/files/rcs/map.json", String.class);
        // long costMillis = System.currentTimeMillis() - ms;
        // log.info("mapJson.length={}, costMillis={}", mapJson.length(), costMillis);

        FileUtils.copyURLToFile(new URL("http://172.31.236.24/files/rcs/map.json"),
                new File("/Users/Charles/Downloads/APM/map.json"));
        readMapJsonAndPrint("http://172.31.236.24/files/rcs/map.json");
        // readMapJsonAndPrint("http://172.31.236.24:9001/rcs/basic/warehouse/1/map/getMapByCode/TL001-RC");
    }

    public static void readMapJsonAndPrint(String mapUrl) {
        long ms = System.currentTimeMillis();
        String mapJson = readMapJson(mapUrl);
        try {
            FileUtils.writeStringToFile(new File("/Users/Charles/Downloads/APM/map-1.json"), mapJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long costMillis = System.currentTimeMillis() - ms;
        System.out.println(mapJson);
        System.out.println(costMillis);
    }

    public static String readMapJson(String mapUrl) {

        StringBuilder mapJson = new StringBuilder();
        String read;
        try {
            URL url = new URL(mapUrl);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setConnectTimeout(5000);
            urlCon.setReadTimeout(5000);
            BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            while ((read = br.readLine()) != null) {
                mapJson.append(read);
            }
            br.close();
        } catch (IOException ignore) {
            // TODO Auto-generated catch block
        }
        return mapJson.toString();
    }

    //获取网络图片
    public static void imageRequest(String fileUrl) throws Exception {
        //new一个URL对象
        URL url = new URL(fileUrl);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        int nameIndex = fileUrl.lastIndexOf("/");
        File imageFile = new File("/Users/Charles/Downloads/APM/" + fileUrl.substring(nameIndex));
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
    }

    public static byte[] readInputStream(InputStream is) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = is.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        is.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
