package com.charles.jssc;

import jssc.SerialPort;
import jssc.SerialPortList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String[] portNames = SerialPortList.getPortNames();
        for (int i = 0; i < portNames.length; i++) {
            System.out.println(portNames[i]);
        }
        Thread.sleep(10000);
    }
}