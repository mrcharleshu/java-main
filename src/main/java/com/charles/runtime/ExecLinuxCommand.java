package com.charles.runtime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecLinuxCommand {
    public static void main(String[] args) {
        InputStream in;
        try {
            String[] commands = new String[]{"/bin/sh", "-c", "pwd && ls -l"};
            // String[] commands = {"/bin/sh", "-c", "ps -ef|grep java"};
            Process process = Runtime.getRuntime().exec(commands);
            process.waitFor();
            in = process.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = read.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
