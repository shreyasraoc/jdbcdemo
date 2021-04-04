import java.sql.*;

/**
 * 
 * @author www.luv2code.com
 *
 */
public class JdbcTest {

	public static void main(String[] args) throws SQLException {

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "shreyasraoc", "Password@123");

			System.out.println("Database connection successful!\n");

			// 2. Create a statement
			myStmt = myConn.createStatement();

			//Inserting a new Row
//			int rowInserted = myStmt
//					.executeUpdate("INSERT INTO employees (last_name, first_name, email, department, salary)"
//							+ "VALUES ('Wright', 'Eric', 'eric.wright@foo.com', 'HR', 35000)");
//			System.out.println(rowInserted + " row inserted");

			// Updating a Row
//			int rowsUpdated = myStmt.executeUpdate(
//					"UPDATE employees SET email = 'john.doe@jdbc.com'" + "WHERE id = 1 AND first_name = 'John'");
//			
//			System.out.println(rowsUpdated + " rows updated!");

			// Deleting a Row
//			int rowsDeleted = myStmt
//					.executeUpdate("DELETE FROM employees" + " WHERE last_name = 'Wright' and first_name = 'Eric'");
//			
//			System.out.println(rowsDeleted + " rows deleted");

			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from employees");
			
			//Prepared Statement
//			PreparedStatement prepStmt = myConn.prepareStatement("SELECT * FROM employees WHERE salary > ? and department = ?");
//			prepStmt.setDouble(1, 50000);
//			prepStmt.setString(2, "HR");
//			
//			myRs = prepStmt.executeQuery();
						
			// 4. Process the result set
			while (myRs.next()) {
				System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close();
			}
		}
	}

}
