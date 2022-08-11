import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PreparedStatementDemo {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String cs = "jdbc:mysql://localhost:3306/hr";
		
		//Step 1
		Connection con = DriverManager.getConnection(cs,"root", "sql.root.V.07");
			
		String query = "insert into employees(employee_id, first_name, last_name, email, hire_date, job_id, salary)" + "values(?,?,?,?,sysdate(),?,?)";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		
		Scanner sc = new Scanner(System.in);
		int count =0;
		boolean flag = true;
		int eid;
		String fname, lname, email, jobid, choice;
		double salary;
		
		while(flag) {
			System.out.print("Enter the unique employee id:" );
			eid = sc.nextInt();
			
			System.out.print("Enter the first name: " );
			fname = sc.next();
			
			System.out.print("Enter the last name: " );
			lname = sc.next();
			
			System.out.print("Enter the employee email:" );
			email = sc.next();
			
			System.out.print("Enter the employee jobid:" );
			jobid = sc.next();
			
			System.out.print("Enter the salary:" );
			salary = sc.nextDouble();
			
			pstmt.setInt(1, eid);
			pstmt.setString(2, fname);
			pstmt.setString(3, lname);
			pstmt.setString(4, email);
			pstmt.setString(5, jobid);
			pstmt.setDouble(6, salary);
			
			count += pstmt.executeUpdate();
			System.out.println("Do you want to continue Y/N");
			choice = sc.next();
			
			if(choice.toUpperCase().charAt(0) == 'N')
				flag = false;
			
					
		}
		
		System.out.println("No of records inserted" + count);
		con.close();
		sc.close();
		
		
	}

}
