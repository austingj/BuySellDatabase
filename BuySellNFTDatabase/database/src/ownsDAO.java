import java.awt.desktop.UserSessionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet("/ownsDAO")

class ownsDAO {
	
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	 private HttpSession session=null;
	
	public ownsDAO() {
		
		
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

	    public boolean delete(String uniqueName) throws SQLException {
	        String sql = "DELETE FROM ownsDatabase WHERE uniqueName = ?";        
	        connect_func();
	         
	        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
	        preparedStatement.setString(1, uniqueName);
	         
	        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
	        preparedStatement.close();
	        return rowDeleted;     
	    }
	    
	    public void insert(String email, String uniquename)throws SQLException, FileNotFoundException, IOException {
	    	try {
	    	connect_func();
    		statement = (Statement) connect.createStatement();
    		
    		statement = connect.createStatement();
    		preparedStatement = connect.prepareStatement("insert into ownsDatabase(email,UniqueName) values (?, ?)");
    		
    		preparedStatement.setString(2, uniquename);
    		preparedStatement.setString(1, email);
    		preparedStatement.executeUpdate();
	        
	    			
	        preparedStatement.close();
	    	}catch(Exception e) {
	    		System.out.println(e);
	    	}
	    	finally {
	    		disconnect();
	    	}
	    }
	    
	    public Boolean doesOwn(HttpServletRequest request, HttpServletResponse response, String email, String Name)throws SQLException, FileNotFoundException, IOException {
	    	try {
	    	connect_func();
    		statement = (Statement) connect.createStatement();
    		session = request.getSession();
    		String ID = (String) session.getAttribute("currentUserEmail");
    		String sql =("SELECT * FROM ownsDatabase where email = '" + ID + "'");
    		statement = (Statement) connect.createStatement();
 	        ResultSet resultSet = statement.executeQuery(sql);
 	       while (resultSet.next()) {
 	    	  String unique = resultSet.getString("uniqueName");
	           if(email.equals(ID)) {
	        	   if(unique.equals(Name)) {
	        		   return true;
	        	   }	   
	           } 
	           
 	       }
	    	}catch(Exception e) {
	    		System.out.println(e);
	    	}
	    	finally {
	    		disconnect();
	    	}
			return false;
	    }
	    public Own getOwn(String name) throws SQLException {
	    	Own user = null;
	        String sql = "SELECT * FROM ownsDatabase WHERE uniqueName = '"+ name + "'";
	         
	        connect_func();
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        if (resultSet.next()) {
	            name = resultSet.getString("email");
	            String nftname = resultSet.getString("uniqueName");

	            user = new Own(nftname, name);
	        }
	        resultSet.close();
	        //statement.close()
	        return user;
	    }
	    public List<Own> listAllNFTS() throws SQLException {
	        List<Own> listowns = new ArrayList<Own>();        
	        String sql = "SELECT * FROM ownsDatabase";      
	        connect_func();      
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	         
	        while (resultSet.next()) {
	            String email = resultSet.getString("email");
	            String uniqueName = resultSet.getString("uniqueName");
	      
	        

	             
	            Own owns = new Own(uniqueName,email);
	            listowns.add(owns);
	        }        
	        resultSet.close();
	        disconnect();        
	        return listowns;
	    }
	    
	    public void fillDatabase()throws SQLException, FileNotFoundException, IOException {
	    	try {
	    		connect_func();
	    		statement = (Statement) connect.createStatement();
	    		
	    		statement = connect.createStatement();
	    		
	    		preparedStatement = connect.prepareStatement("insert into ownsDatabase(email,UniqueName) values (?, ?)");
	    		
	    		preparedStatement.setString(2, "UniqueNameAMELIA");
	    		preparedStatement.setString(1, "amelia@gmail.com");
	    		preparedStatement.executeUpdate();
	    		
	    	
	    		
	    		preparedStatement.setString(2, "UniqueNameANGELO");
	    		preparedStatement.setString(1, "angelo@gmail.com");
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(2, "DONdons");
	    		preparedStatement.setString(1, "don@gmail.com");
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(2, "Jeannnzzz");
	    		preparedStatement.setString(1, "jeannette@gmail.com");

	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(2, "JojojojoJoJO");
	    		preparedStatement.setString(1, "jo@gmail.com");
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(2, "Margarar");
	    		preparedStatement.setString(1, "margarita@gmail.com");
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(2, "UniqueName");
	    		preparedStatement.setString(1, "root");
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(2, "RudyRude");
	    		preparedStatement.setString(1, "rudy@gmail.com");
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(2, "Sophs");
	    		preparedStatement.setString(1, "sophie@gmail.com");
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(2, "SUE");
	    		preparedStatement.setString(1, "susie@gmail.com");
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(2, "WoahWall");
	    		preparedStatement.setString(1, "wallace@gmail.com");
	    		preparedStatement.executeUpdate();
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
	        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
	        statement.executeUpdate("DROP TABLE IF EXISTS ownsDatabase");
	        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
	        statement.executeUpdate("use testdb");
	        
	        String sqlstmt ="CREATE TABLE IF NOT EXISTS ownsDatabase " +
	        				"(email VARCHAR(50) NOT NULL, " +
	        				"UniqueName VARCHAR(20) NOT NULL, " +
	        				"PRIMARY KEY (UniqueName), " +
	        				"FOREIGN KEY ( email ) REFERENCES User(email), " +
	        				"FOREIGN KEY ( UniqueName) REFERENCES NFTDatabase(UniqueName))";
	        
	        
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
