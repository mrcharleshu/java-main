package com.charles.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunLinuxCmd {
    public static void main(String[] args) {
        try {
            String command = "ssh -l root 192.168.21.218 chmod 766 ./test.sh && ./test.sh";

            // int result = Runtime.getRuntime().exec(command).waitFor();
            // System.out.println("result: " + result);
            String result2 = runCommand(command);
            System.out.println("执行结果：" + result2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String runCommand(String command) throws IOException {
        Process ps = Runtime.getRuntime().exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            //执行结果加上回车
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
