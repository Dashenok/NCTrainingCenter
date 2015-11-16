package jl10.controllers;

import jl10.models.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16.11.2015.
 */
public class OracleEmployeeDao implements EmployeeDao {
    private final Connection connection;

    @Override
    public Employee create() {
        return null;
    }

    @Override
    public Employee read(int key) throws SQLException {
        String sql = "SELECT * FROM OLD_DB.EMPLOYEES emp, OLD_DB.DEPARTMENTS dept, OLD_DB.JOBS job " +
                "WHERE dept.DEPARTMENT_ID = emp.DEPARTMENT_ID AND job.JOB_ID = emp.JOB_ID AND emp.EMPLOYEE_ID = ?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, key);

        ResultSet rs = stm.executeQuery();

        if(rs.next()) {
            Employee g = new Employee();
            g.setId(rs.getInt("EMPLOYEE_ID"));
            g.setLastName(rs.getString("LAST_NAME"));
            g.setDepartment(rs.getString("DEPARTMENT_NAME"));
            g.setJob(rs.getString("JOB_TITLE"));
            return g;
        }else{
           throw new SQLException("User with id " + key + " is not found");
        }
    }

    @Override
    public void update(Employee group) { }

    @Override
    public void delete(Employee group) { }

    @Override
    public List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM EMPLOYEES";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<Employee> list = new ArrayList<Employee>();
        while (rs.next()) {
            Employee g = new Employee();
            g.setId(rs.getInt("EMPLOYEE_ID"));
            g.setLastName(rs.getString("LAST_NAME"));
            g.setDepartment(rs.getString("DEPARTMENT_ID"));
            list.add(g);
        }
        return list;
    }

    public OracleEmployeeDao(Connection connection) {
        this.connection = connection;
    }
}
