package toko;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/bpltb";
    private String username = "root";
    private String password = "";

    public Connection getConn() throws SQLException {
        Connection connection = DriverManager.getConnection(
                url,username,password
        );

        return connection;
    }
}