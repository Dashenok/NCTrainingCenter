package jl10.controllers;

import jl10.models.Employee;

import java.sql.SQLException;
import java.util.List;


public interface EmployeeDao {

    boolean add(Employee employee) throws SQLException;

    /** ���������� ������ ��������������� ������ � ��������� ������ key ��� null */
    Employee read(int key) throws SQLException;

    /** ��������� ��������� ������� group � ���� ������ */
    void update(Employee employee)throws SQLException ;

    /** ������� ������ �� ������� �� ���� ������ */
    boolean delete(Employee employee) throws SQLException ;

    /** ���������� ������ �������� ��������������� ���� ������� � ���� ������ */
    public List<Employee> getAll() throws SQLException;

    Employee readByArrtibute(String attribute, String attributeName) throws SQLException;

}