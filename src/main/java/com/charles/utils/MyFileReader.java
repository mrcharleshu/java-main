package com.charles.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MyFileReader {

    public static void main(String[] args) {

//  Path path = Paths.get("C:\\Users\\user\\Documents\\帕科数据样例\\mysqlinsertsample.txt");
//  String targetFile = "C:\\Users\\user\\Documents\\帕科数据样例\\out.txt";

        Path path = Paths.get(args[0]);
        System.out.println("Read from file:" + path.toString());
        String targetFile = args[1];
        System.out.println("Write to file:" + targetFile);

        BufferedWriter bufWriter;

//  Path path = Paths.get("t_tencent_content_video.sql");
        try {
            bufWriter = new BufferedWriter(new FileWriter(targetFile));

            List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
            for (int i = 43; i < lines.size(); i++) {
                System.out.println("Processing " + i + "th line...");
                String line = lines.get(i);
                line = line.replace("INSERT INTO `t_tencent_content_video` VALUES (", "),(").replace(");", "").replace("),(", "###");

                String[] values = line.split("###");
                int ln = 0;
                for (String val : values) {
//     if(ln==0)
//      continue;
                    String newval = val.replace(",\\", ".\\").replace(",", "|")
                            .replace("\'", "").replace(".", ",")
                            .replace("\\\"", "").replace("\\", "");
                    if (null != newval || !newval.isEmpty()) {
                        bufWriter.write(newval);
                        bufWriter.newLine();
                    }
//     System.out.println(newval);
                }

            }
            System.out.println("Done.");
            bufWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}