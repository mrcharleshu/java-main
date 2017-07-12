package com.charles.common.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JDBC能否处理Blob和Clob？
 * Blob是指二进制大对象（Binary Large Object）
 * Clob是指大字符对象（Character Large Object）
 * 因此其中Blob是为存储大的二进制数据而设计的，而Clob是为存储大的文本数据而设计的。
 * JDBC的PreparedStatement和ResultSet都提供了相应的方法来支持Blob和Clob操作。
 * 下面的代码展示了如何使用JDBC操作LOB：
 * 下面以MySQL数据库为例，创建一个张有三个字段的用户表，包括编号（id）、姓名（name）和照片（photo），建表语句如下：
 * <p>
 * create table tb_user (
 * id int primary key auto_increment,
 * name varchar(20) unique not null,
 * photo longblob
 * );
 */
public class SimpleJDBC {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 1. 加载驱动（Java6以上版本可以省略）
            Class.forName("com.mysql.jdbc.Driver");
            // 2. 建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            // 3. 创建语句对象
            PreparedStatement ps = conn.prepareStatement("insert into tb_user values (default, ?, ?)");
            ps.setString(1, "Charles");              // 将SQL语句中第一个占位符换成字符串
            try (InputStream in = new FileInputStream("/Users/Charles/Desktop/yoga.jpg")) {    // Java 7的TWR
                ps.setBinaryStream(2, in);      // 将SQL语句中第二个占位符换成二进制流
                // 4. 发出SQL语句获得受影响行数
                System.out.println(ps.executeUpdate() == 1 ? "插入成功" : "插入失败");
            } catch (IOException e) {
                System.out.println("读取照片失败!");
            }
        } catch (ClassNotFoundException | SQLException e) {     // Java 7的多异常捕获
            e.printStackTrace();
        } finally { // 释放外部资源的代码都应当放在finally中保证其能够得到执行
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();    // 5. 释放数据库连接
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
