package com.charles.pattern;

import java.util.Arrays;

/**
 * @author Charles
 */
public class AdapterPattern {

    public static class UsbSocket {

        public String readFromUsb() {
            return "this is USB content";
        }
    }

    public static class TypeCSocket {

        public byte[] readFromTypeC() {
            return "this is TypeC content".getBytes();
        }
    }

    public interface Adapter {

        /**
         * 与原类中的方法相同
         */
        String readFromUsb();

        /**
         * 新类的方法
         */
        byte[] readFromTypeC();
    }

    public static class NewestMacBookAdapter implements Adapter {
        private UsbSocket usbSocket;
        private TypeCSocket typeCSocket;
        private boolean bound;

        public void bind(UsbSocket usbSocket, TypeCSocket typeCSocket) {
            this.usbSocket = usbSocket;
            this.typeCSocket = typeCSocket;
            this.bound = true;
        }

        @Override
        public String readFromUsb() {
            if (!bound) {
                throw new IllegalStateException("Please bind first");
            }
            return new String(typeCSocket.readFromTypeC());
        }

        @Override
        public byte[] readFromTypeC() {
            if (!bound) {
                throw new IllegalStateException("Please bind first");
            }
            return usbSocket.readFromUsb().getBytes();
        }
    }

    public static void main(String[] args) {
        UsbSocket usbSocket = new UsbSocket();
        TypeCSocket typeCSocket = new TypeCSocket();
        NewestMacBookAdapter newestMacBook = new NewestMacBookAdapter();
        newestMacBook.bind(usbSocket, typeCSocket);
        System.out.println(Arrays.toString(newestMacBook.readFromTypeC()));
    }
}