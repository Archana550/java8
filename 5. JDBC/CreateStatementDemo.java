  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateStatement {

	
	public static void main(String args[]) throws SQLException{
		String cs = "jdbc:mysql://localhost:3306/hr";
		
		//Step 1
		Connection con = DriverManager.getConnection(cs,"root", "sql.root.V.07");
		
		//Step 2
		Statement stmt = con.createStatement();
		
		
		//Step 3
		String query = "select employee_id, first_name, last_name from employees";
		ResultSet rs = stmt.executeQuery(query);
		
		System.out.println("Emp ID\t\t" + "first_name\t" + "Last Name");
		
		//Step 3A
		while(rs.next()) {
			System.out.println(rs.getInt(1) + "\t\t" + rs.getString("first_name") + "\t\t" + rs.getString(3));
		}
		//Step 4
		con.close();
		
	}
}
