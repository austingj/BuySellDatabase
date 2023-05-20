import java.awt.desktop.UserSessionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/Listings")
public class Listings 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	 private HttpSession session=null;
	
	public Listings(){
		
	}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
            System.out.println(connect);
        }
    }
    
    public boolean database_login(String email, String password) throws SQLException{
    	try {
    		connect_func("root","pass1234");
    		String sql = "select * from user where email = ?";
    		preparedStatement = connect.prepareStatement(sql);
    		preparedStatement.setString(1, email);
    		ResultSet rs = preparedStatement.executeQuery();
    		return rs.next();
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
    	}
    }
	//connect to the database 
    public void connect_func(String username, String password) throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/userdb?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
  
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public boolean delete(int NFTID) throws SQLException {
        String sql = "DELETE FROM Listings WHERE NFTID = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, NFTID);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
  
    
    public boolean checkTime(HttpServletRequest request, HttpServletResponse response,int NFTID) throws SQLException, FileNotFoundException, IOException  {
    	try {
    		connect_func();
    		statement = (Statement) connect.createStatement();
    		session = request.getSession();
    	int ID = (int) session.getAttribute("NFTID");
        String sql = ("SELECT * FROM Listings where NFTID = '" + ID + "'");      

	    ResultSet resultSet = statement.executeQuery(sql);
	    while (resultSet.next()) {
	    	  int id = resultSet.getInt("NFTID");
	    	  String owner = resultSet.getString("owner");
	    	  Timestamp strt = resultSet.getTimestamp("start");
	    	  Timestamp end = resultSet.getTimestamp("end");
	    	  if(id==NFTID) {
	    		  double price = resultSet.getDouble("price");
			      	session.setAttribute("price", price);
			      	session.setAttribute("Owner", owner);
			      	if(strt.compareTo(end) > 0) {
			      		return false;
			      	}
	    		  return true;
	    	  }
	    }
        preparedStatement.close();
        
    }catch(Exception e) {
		System.out.println(e);
		return false;
	}
	finally {
		disconnect();
	}
        
      return false;
    }
    
    public boolean inListings(HttpServletRequest request, HttpServletResponse response,int NFTID) throws SQLException, FileNotFoundException, IOException  {
    	try {
    		connect_func();
    		statement = (Statement) connect.createStatement();
    		session = request.getSession();
    	int ID = (int) session.getAttribute("NFTID");
        String sql = ("SELECT * FROM Listings where NFTID = '" + ID + "'");      

	    ResultSet resultSet = statement.executeQuery(sql);
	    while (resultSet.next()) {
	    	  int id = resultSet.getInt("NFTID");
	    	  String owner = resultSet.getString("owner");
	    	  Timestamp strt = resultSet.getTimestamp("start");
	    	  Timestamp end = resultSet.getTimestamp("end");
	    	  if(id==NFTID) {
	    		  double price = resultSet.getDouble("price");
			      	session.setAttribute("price", price);
			      	session.setAttribute("Owner", owner);
			      	session.setAttribute("start", strt);
			     	session.setAttribute("end", end);
	    		  return true;
	    	  }
	    }
        preparedStatement.close();
        
    }catch(Exception e) {
		System.out.println(e);
		return false;
	}
	finally {
		disconnect();
	}
        
      return false;
    }


    public void insert(String email,int NFTID, Timestamp start, Timestamp end, double price)throws SQLException, FileNotFoundException, IOException {
    	try {
	    	connect_func();
    		statement = (Statement) connect.createStatement();
    		
    		statement = connect.createStatement();
    		preparedStatement = connect.prepareStatement("insert into Listings(owner,NFTID,start,end,price) values (?, ?,?,?,?)");
    		
    		preparedStatement.setString(1, email);

    		preparedStatement.setInt(2, NFTID);
    		preparedStatement.setTimestamp(3, start);
    		preparedStatement.setTimestamp(4, end);
    		preparedStatement.setDouble(5, price);
    		preparedStatement.executeUpdate();
	        
	    			
	        preparedStatement.close();
	    	}catch(Exception e) {
	    		System.out.println(e);
	    	}
	    	finally {
	    		disconnect();
	    	}
    }
    public void init() throws SQLException, FileNotFoundException, IOException{
    	try {
    	connect_func();
        statement =  (Statement) connect.createStatement();
        statement.executeUpdate("use testdb");
        statement.executeUpdate("DROP TABLE IF EXISTS Listings");
        String sqlstmt = 		"CREATE TABLE if not exists Listings " +
					        	"(ListID INTEGER not NULL AUTO_INCREMENT," +
					            "owner VARCHAR(50)," + 
					        	"NFTID INTEGER," +
					            "start TIMESTAMP , " +
					            "end TIMESTAMP , "+ 
					            "price DECIMAL(13,2) DEFAULT 100,"+ 
					            "PRIMARY KEY (ListID), "+
					            "FOREIGN KEY(owner) REFERENCES User(email)," +
					            "FOREIGN KEY(NFTID) REFERENCES NFTDatabase(NFTID))";    
        					
        statement.executeUpdate(sqlstmt);
    	}
    	catch(Exception e) {
    		System.out.print(e);
    		
    	}
    	finally {
    		disconnect();
    	}
    }

      

}
