package jdbcsprocs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class SprocTetst {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		String department = "HR";

		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "shreyasraoc", "Password@123");

			System.out.println("Database connection successful!\n");

			// Call the Sproc with IN Param
			sprocIncrementSalary(myConn, department, 25000);
			sprocIncrementSalary(myConn, department, -25000);

			// Call Sproc with INOUT Param
			sprocGreetDepartment(myConn, department);

			// Call Sproc with INOUT Param
			sprocDepartmentCount(myConn, department);
			
			//Call Sproc with Result Set returned
			sprocDepartmentList(myConn, department);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void showSalaries(Connection myConn, String department) {

		try {
			PreparedStatement prepStmt = myConn.prepareStatement("SELECT * FROM employees WHERE department = ?");
			prepStmt.setString(1, department);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("first_name") + ", " + rs.getString("salary"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sprocIncrementSalary(Connection myConn, String department, double number) {
		// Use CallableStatement to call the Stored Procedure and execute the statement

		// Using SPROC to demo IN Parameter
		try {
			// Just display values before the update
			System.out.println("Before Increment:");
			showSalaries(myConn, department);

			CallableStatement call = myConn.prepareCall("{ call increase_salaries_for_department(?, ?) }");
			call.setString(1, department);
			call.setDouble(2, 25000);

			call.execute();

			System.out.println();
			// Print the values after the increment
			System.out.println("After Increment:");
			showSalaries(myConn, department);
			System.out.println();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sprocGreetDepartment(Connection myConn, String department) {

		try {
			CallableStatement call = myConn.prepareCall("{ call greet_the_department( ? ) }");
			call.registerOutParameter(1, Types.VARCHAR);
			call.setString(1, department);

			call.execute();

			System.out.println(call.getString(1));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sprocDepartmentCount(Connection myConn, String department) {

		try {
			CallableStatement call = myConn.prepareCall("{ call get_count_for_department(?, ?) }");
			call.registerOutParameter(2, Types.INTEGER);
			call.setString(1, department);

			call.execute();

			System.out.println("Department Head Count: " + call.getInt(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sprocDepartmentList(Connection myConn, String department) {

		try {
			CallableStatement call = myConn.prepareCall("{ call get_employees_for_department(?) }");
			call.setString(1, department);

			call.execute();

			ResultSet rs = call.getResultSet();
			
			while(rs.next()) {
				System.out.println(rs.getString("first_name") + ", " + rs.getString("department"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
