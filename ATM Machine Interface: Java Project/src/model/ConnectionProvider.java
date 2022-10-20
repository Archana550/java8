package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {

private ConnectionProvider() {
throw new IllegalStateException("Utility class");
}

static Connection con;

public static Connection createConnection() {
try {
// load the driver
Class.forName("com.mysql.cj.jdbc.Driver");

// create the connection
String user = "root";
String password = "sql.root.V.07";
String url = "jdbc:mysql://localhost:3306/myprojectbank";

con = DriverManager.getConnection(url, user, password);

} catch (Exception e) {
e.printStackTrace();
}

return con;
}

}

