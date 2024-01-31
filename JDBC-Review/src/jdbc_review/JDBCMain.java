package jdbc_review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCMain {

	public static void main(String[] args) {
		String address = "jdbc:mysql://localhost:3306/books";
		String user = "cst8277";
		String pass = "8277";
		String script = "select * from books.authors";
		
		try (Connection con = DriverManager.getConnection(address, user, pass);
		PreparedStatement statement = con.prepareStatement(script);
		ResultSet result = statement.executeQuery()) {
			
			System.out.printf("%-10s%-15s%-15s%n",
			("AuthorID"),
			("FirstName"),
			("LastName"));
			
			while( result.next()) {
				System.out.printf("%-10d%-15s%-15s%n",
				result.getInt("AuthorID"),
				result.getString("FirstName"),
				result.getString("LastName"));
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
