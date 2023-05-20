import java.awt.desktop.UserSessionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/NFTDAO")

public class NFTDAO {

	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public NFTDAO() {
		
		
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
	    public void insert(NFT NFTDatabase)throws SQLException, FileNotFoundException, IOException {
	    	try {
	 		connect_func();
    		statement = (Statement) connect.createStatement();
    		
    		statement = connect.createStatement();
    		
    		preparedStatement = connect.prepareStatement("insert into NFTDatabase(UniqueName, Description, ImageURL, Creator) values (?, ?, ?,?)");
	    	preparedStatement.setString(1,  NFTDatabase.getUniqueName());
	    	preparedStatement.setString(2,  NFTDatabase.getDescription());
	    	preparedStatement.setString(3,  NFTDatabase.getImageURL());

	    	preparedStatement.setString(4,  NFTDatabase.getCreator());
	    			
	    	preparedStatement.executeUpdate();
	    	
	    }catch(Exception e) {
    		System.out.println(e);
    	}
    	finally {
    		disconnect();
    	}
	        preparedStatement.close();
	        
	    }
	    
	    public NFT getNFT(String uniqueName) throws SQLException{
	    	NFT nft = null;
	    	//String sql="SELECT * FROM NFTDatabase WHERE uniqueName =?";
	    	uniqueName = uniqueName + "%";
	    	//String sql="SELECT * FROM NFTDatabase WHERE uniqueName LIKE "+ "'" + uniqueName + "%'";
	        String sql = ("SELECT * FROM NFTDatabase WHERE UniqueName LIKE" + "'" + uniqueName + "'"); 
	    	connect_func();
	    	
	    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
	       
	    	// preparedStatement.setString(1, uniqueName);
	        
	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        if(resultSet.next()) {
	        	int nftid = resultSet.getInt("NFTID");
	        	String uniquename = resultSet.getString("UniqueName");
	        	String description = resultSet.getString("Description");
	        	String imageurl = resultSet.getString("ImageURL");
	        	String creator = resultSet.getString("Creator");
	
	        	nft = new NFT(uniquename,description,imageurl, nftid,creator);
	        	
	        	
	        }
	        resultSet.close();
	       // statement.close();
	        
	    	return nft;
	    }
	    
	    public NFT getNFT(int NFTID) throws SQLException{
	    	NFT nft = null;
	    	String sql="SELECT * FROM NFTDatabase WHERE NFTID = ?";
	    	connect_func();
	    	
	    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
	        preparedStatement.setInt(1, NFTID);
	        
	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        if(resultSet.next()) {
	        	int nftid = resultSet.getInt(NFTID);
	        	String uniquename = resultSet.getString("UniqueName");
	        	String description = resultSet.getString("Description");
	        	String imageurl = resultSet.getString("ImageURL");
	        	//double price = resultSet.getInt("price");
	        	
	        	nft = new NFT(uniquename,description,nftid);
	        	
	        }
	        resultSet.close();
	        statement.close();
	        
	    	return nft;
	    }
	    
	    public List<NFT> ListSearchedNFTS(String uniqueName) throws SQLException {
	        List<NFT> listnfts = new ArrayList<NFT>();    
	        uniqueName = uniqueName + "%";
	        String sql = ("SELECT * FROM NFTDatabase WHERE UniqueName LIKE" + "'" + uniqueName + "'");      
	        connect_func();      
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	         
	        while (resultSet.next()) {
	        	int id = resultSet.getInt("NFTID");
	            String name = resultSet.getString("UniqueName");
	            String desc = resultSet.getString("Description");
	            String url = resultSet.getString("ImageURL");
	            String creatorEmail = resultSet.getString("Creator");
	           NFT nfts = new NFT(name, desc, url, creatorEmail);
	            listnfts.add(nfts);
	        }        
	        resultSet.close();
	        disconnect();        
	        return listnfts;
	    }
	    
	    
	    public void fillDatabase()throws SQLException, FileNotFoundException, IOException {
	    	try {
	    		connect_func();
	    		statement = (Statement) connect.createStatement();
	    		
	    		statement = connect.createStatement();
	    		
	    		preparedStatement = connect.prepareStatement("insert into NFTDatabase(UniqueName, Description, ImageURL, Creator) values (?, ?, ?, ?)");
	    		
	    		preparedStatement.setString(1, "UniqueNameAMELIA");
	    		preparedStatement.setString(4, "amelia@gmail.com");
	    		preparedStatement.setString(2, "Descriptions");
	    		preparedStatement.setString(3, "https://images.unsplash.com/photo-1501183007986-d0d080b147f9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8ZnJlZXxlbnwwfHwwfHw%3D&w=1000&q=80");

	    		preparedStatement.executeUpdate();
	    		
	    	
	    		
	    		preparedStatement.setString(1, "UniqueNameANGELO");
	    		preparedStatement.setString(4, "angelo@gmail.com");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "https://static-cse.canva.com/blob/666314/bestfreestockphotos.jpg");

	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(1, "DONdons");
	    		preparedStatement.setString(4, "don@gmail.com");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "https://images.ctfassets.net/hrltx12pl8hq/4f6DfV5DbqaQUSw0uo0mWi/6fbcf889bdef65c5b92ffee86b13fc44/shutterstock_376532611.jpg?fit=fill&w=800&h=300");
	
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(1, "Jeannnzzz");
	    		preparedStatement.setString(4, "jeannette@gmail.com");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "https://img.freepik.com/free-photo/abstract-flowing-neon-wave-background_53876-101942.jpg?w=2000");
	
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(1, "JojojojoJoJO");
	    		preparedStatement.setString(4, "jo@gmail.com");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "https://images.ctfassets.net/hrltx12pl8hq/7JnR6tVVwDyUM8Cbci3GtJ/bf74366cff2ba271471725d0b0ef418c/shutterstock_376532611-og.jpg");

	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(1, "Margarar");
	    		preparedStatement.setString(4, "margarita@gmail.com");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "https://sp-ao.shortpixel.ai/client/to_webp,q_glossy,ret_img,w_800,h_538/https://blog.snappa.com/wp-content/uploads/2022/02/Freestocks.jpg");
	
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(1, "UniqueName");
	    		preparedStatement.setString(4, "root");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "Image/URL");
	
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(1, "RudyRude");
	    		preparedStatement.setString(4, "rudy@gmail.com");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "https://thumbs.dreamstime.com/b/woman-praying-free-birds-to-nature-sunset-background-woman-praying-free-birds-enjoying-nature-sunset-99680945.jpg");

	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(1, "Sophs");
	    		preparedStatement.setString(4, "sophie@gmail.com");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "https://www.istockphoto.com/resources/images/RoyaltyFree/Hero-813062534_2000x650.jpg");
	
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(1, "SUE");
	    		preparedStatement.setString(4, "susie@gmail.com");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "https://99designs-blog.imgix.net/blog/wp-content/uploads/2013/12/snapwire.jpg?auto=format&q=60&fit=max&w=930");

	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(1, "WoahWall");
	    		preparedStatement.setString(4, "wallace@gmail.com");
	    		preparedStatement.setString(2, "Desc");
	    		preparedStatement.setString(3, "https://static1.makeuseofimages.com/wordpress/wp-content/uploads/2016/10/camera-photo-lens-stock-images.jpg");
	  
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
	        statement.executeUpdate("DROP TABLE IF EXISTS NFTDatabase");
	        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
	        statement.executeUpdate("use testdb");
	        
	        String sqlstmt ="CREATE TABLE IF NOT EXISTS NFTDatabase " +
	        				"(NFTID INTEGER not NULL AUTO_INCREMENT, " + 
	        				"UniqueName VARCHAR(20) NOT NULL, " +
	        				"Creator VARCHAR(50), " +
	        				"minttime DATETIME," + 
	        				"Description VARCHAR(50), " + 
	        				"ImageURL VARCHAR(500), " +
	        				"unique(UniqueName), " + 
	        				"PRIMARY KEY (NFTID, UniqueName)," +
	        				"FOREIGN KEY ( Creator ) REFERENCES User(email))";
	        
	        
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
