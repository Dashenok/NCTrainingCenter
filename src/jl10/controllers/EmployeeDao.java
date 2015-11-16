package jl10.controllers;

import jl10.models.Employee;

import java.sql.SQLException;
import java.util.List;


public interface EmployeeDao {

    Employee create();

    /** ���������� ������ ��������������� ������ � ��������� ������ key ��� null */
    Employee read(int key) throws SQLException;

    /** ��������� ��������� ������� group � ���� ������ */
    void update(Employee group);

    /** ������� ������ �� ������� �� ���� ������ */
    void delete(Employee group);

    /** ���������� ������ �������� ��������������� ���� ������� � ���� ������ */
    public List<Employee> getAll() throws SQLException;

}