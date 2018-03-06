import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBconn {
	private String host = "localhost";
	private String port = "1521";
	private String dbName = "XE";
	private String userName = "Howard4";
	private String password = "Howard12345";
	private String oracleURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
	private Connection conn = null;
	private Statement stat = null;
	
	
	public DBconn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(oracleURL, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String sql) {
		ResultSet res = null;
		
		try {
			stat = conn.createStatement();
			
			res = stat.executeQuery(sql);
			//resultSet.next() =? getString(1)/...
			//ResultSetmetadata 
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			e1.printStackTrace();
		}
		
		return res;
	}

	public void closeDB() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
