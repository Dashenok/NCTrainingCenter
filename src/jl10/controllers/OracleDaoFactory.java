package jl10.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDaoFactory implements DaoFactory {
    private String user = "OLD_DB";
    private String password = "1";
    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private String driver = "oracle.jdbc.driver.OracleDriver";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public EmployeeDao getEmployeeDao(Connection connection) {
        return new OracleEmployeeDao(connection);
    }

    public OracleDaoFactory() {
        try {
            Class.forName(driver);
    } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
