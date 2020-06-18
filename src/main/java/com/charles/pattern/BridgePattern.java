package com.charles.pattern;

/**
 * @author Charles
 */
public class BridgePattern {

    public interface Driver {
        void connect();
    }

    public static class MySqlDriver implements Driver {

        @Override
        public void connect() {
            System.out.println("mysql connected!");
        }
    }

    public static class OracleDriver implements Driver {

        @Override
        public void connect() {
            System.out.println("oracle connected!");
        }
    }

    public static class Bridge<D extends Driver> {
        private D driver;

        public Bridge(D driver) {
            this.driver = driver;
        }

        public void connect() {
            driver.connect();
        }

        public D getDriver() {
            return driver;
        }

        public void setDriver(D driver) {
            this.driver = driver;
        }
    }

    public static class MyBridge<D extends Driver> extends Bridge<D> {

        public MyBridge(D driver) {
            super(driver);
        }

        public void connect() {
            getDriver().connect();
        }
    }

    public static void main(String[] args) {
        new MyBridge<>(new MySqlDriver()).connect();
        new MyBridge<>(new OracleDriver()).connect();
    }
}
