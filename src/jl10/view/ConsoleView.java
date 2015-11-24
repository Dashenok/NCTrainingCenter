package jl10.view;

import jl10.controllers.EmployeeDao;
import jl10.models.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleView {

    private Connection con;
    private EmployeeDao employeeDao;

    public ConsoleView(Connection con, EmployeeDao employeeDao) {
        this.con = con;
        this.employeeDao = employeeDao;
    }

    public static void showMenu(){
        System.out.println("            MENU");
        System.out.println("1. Show user: Show");
        System.out.println("2. Add user: Add");
        System.out.println("3. Delete user: Del");
        System.out.println("4. Exit: Exit");
    }
    public String readCommand() {
        System.out.println("Enter command name to execute:");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNext()) {
            return sc.next();
        }
        return "-1";
    }

    public int executeCommand(String command) throws SQLException {
        int commandResult = 0;
        if (command.equals("Add")) {
            commandResult =executeCommandAdd();
        }else if (command.equals("Show")) {
            commandResult = executeCommandShow();
        }else if (command.equals("Del")) {
            commandResult = executeCommandDel();
        }else if (command.equals("Exit")) {
            return -1;
        }else{
            showErrorMessage();
        }

        return commandResult;

    }

    private int executeCommandDel() throws SQLException{

        String email = readAttribute("Email");
        if (email == "Exit"){
            return -1;
        }
        if(employeeDao.readByArrtibute("Email", email)==null){
            Employee emp = new Employee();
            emp.setEmail(email);
            boolean result = employeeDao.delete(emp);
            System.out.println("User " + " with email " + email +" was deleted");
            if (result) {
                return 0;
            } else {
                return -1;
            }
        }
        return 0;
    }

    private int executeCommandShow() {
        Employee emp;
        int readId = readId();
        if (readId==-1){
            return -1;
        }

        try {
            emp = employeeDao.read(readId);
            StringBuilder stringBuilder = new StringBuilder("Employee ").append(emp.getLastName()).append("" +
                    "(").append(emp.getId()).append(")").append(" " +
                    "works in department " +
                    " ").append(emp.getDepartment()).append(" as ").append(emp.getJob());

            System.out.println(stringBuilder);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private int executeCommandAdd()  throws SQLException {
        String lastName = readAttribute("Last Name");
        String email = readAttribute("Email");
        if (email == "Exit" || lastName == "Exit"){
            return -1;
        }
        if(employeeDao.readByArrtibute("Email", email)==null){
            Employee emp = new Employee();
            emp.setEmail(email);
            emp.setLastName(lastName);
            employeeDao.add(emp);
            System.out.println("User " + lastName + " with email " + email +" was added");
            return 0;
        } else {
            System.out.println("User already exist");
        }

        return -1;

    }


    private void showErrorMessage(){
        System.out.println("Error");
    }

    private int readId(){
        int id;
        System.out.println("Enter id to show user:");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()) {
            id = sc.nextInt();
            if (id == -1) {
                return -1;
            }
            return id;
        }
        return 0;

    }
    private String readAttribute(String attributeName){
        String attribute;
        System.out.println("Enter " + attributeName  + " or Exit:");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNext()) {
            attribute = sc.next();
            return attribute;
        }
        return "Exit";
    }
}
