package jl10.controllers;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by user on 16.11.2015.
 */
public interface DaoFactory {
    //���������� ����������� � ���� ������
    Connection getConnection() throws SQLException;

    /* ���������� ������ ��� ���������� ������������� ���������� ������� Group */
    EmployeeDao getEmployeeDao(Connection connection);

}
