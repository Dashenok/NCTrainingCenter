package jl10.controllers;

import jl10.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16.11.2015.
 */
public class OracleEmployeeDao implements EmployeeDao {
    private final Connection connection;

    @Override
    public boolean add(Employee employee) throws SQLException{
        try {
            String sql = "INSERT INTO EMPLOYEES(EMPLOYEE_ID, LAST_NAME, EMAIL, HIRE_DATE, JOB_ID, MANAGER_ID) " +
                    "VALUES (EMPLOYEES_SEC.NEXTVAL, "+ employee.getLastName()+", " + employee.getEmail()+", TO_DATE('01.01.2000'), 'IT_PROG', 101)";
            Statement stm = connection.createStatement();

            stm.executeUpdate(sql);

           // PreparedStatement stm = connection.prepareStatement(sql);
            //stm.setString(1, employee.getLastName());
           // stm.setString(2, employee.getEmail());
            //sql = "COMMIT";
            //stm = connection.prepareStatement(sql);
            return true;
        } catch (SQLException e) {

            return false;
        }
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
    public void update(Employee employee) { }

    @Override
    public boolean delete(Employee employee) throws SQLException {
       // try {
            String sql = "DELETE FROM OLD_DB.EMPLOYEES WHERE EMAIL = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, employee.getEmail());
            stm.execute();
            return true;
       // } catch (SQLException e) {
        //    return false;

        //}
    }

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

    @Override
    public Employee readByArrtibute(String attribute, String attributeValue) throws SQLException {
        String sql = "SELECT * FROM OLD_DB.EMPLOYEES emp, OLD_DB.DEPARTMENTS dept, OLD_DB.JOBS job " +
                "WHERE dept.DEPARTMENT_ID = emp.DEPARTMENT_ID AND job.JOB_ID = emp.JOB_ID AND emp."+attribute+" = ?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, attributeValue);

        ResultSet rs = stm.executeQuery();

        if(rs.next()) {
            Employee g = new Employee();
            g.setId(rs.getInt("EMPLOYEE_ID"));
            g.setLastName(rs.getString("LAST_NAME"));
            g.setDepartment(rs.getString("DEPARTMENT_NAME"));
            g.setJob(rs.getString("JOB_TITLE"));
            return g;
        }else{
            return null;
        }
    }

    public OracleEmployeeDao(Connection connection) {
        this.connection = connection;
    }
}
