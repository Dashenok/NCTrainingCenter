package jl10;

import jl10.controllers.DaoFactory;
import jl10.controllers.EmployeeDao;
import jl10.controllers.OracleDaoFactory;
import jl10.view.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        DaoFactory daoFactory = new OracleDaoFactory();
        Connection con = daoFactory.getConnection();
        EmployeeDao employeeDao = daoFactory.getEmployeeDao(con);

        ConsoleView consoleView = new ConsoleView(con, employeeDao);
        while (true) {
            ConsoleView.showMenu();
            String readCommand = consoleView.readCommand();
            if (consoleView.executeCommand(readCommand)==-1){
                break;
            }
        }
        con.commit();
        con.close();
        /*Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Введите id работника");

            if(sc.hasNextInt()) {

                id = sc.nextInt();
                if (id == -1){
                    return;
                }
                EmployeeDao employeeDao = daoFactory.getEmployeeDao(con);
                try {
                    emp = employeeDao.read(id);
                    StringBuilder stringBuilder = new StringBuilder("Employee ").append(emp.getLastName()).append("" +
                            "(").append(emp.getId()).append(")").append(" " +
                            "works in department " +
                            " ").append(emp.getDepartment()).append(" as ").append(emp.getJob());

                    System.out.println(stringBuilder);
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }

            }else {
                System.out.println("Вы ввели не целое число");
                return;
            }
        }*/

}


}
