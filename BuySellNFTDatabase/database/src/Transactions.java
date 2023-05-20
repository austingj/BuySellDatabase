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
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.desktop.UserSessionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/Transactions")

class Transactions {
	
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	 private HttpSession session=null;
	
	public Transactions() {
		
		
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
	    public int getMaxOwners() throws SQLException{
			// TODO Auto-generated method stub
	    	int max = 0;
	    	int current;
	 
	    		String sql = ("SELECT UniqueName, MAX(touser), COUNT(DISTINCT(touser)) from Transactions  GROUP BY UniqueName");
		        connect_func();      
		        statement = (Statement) connect.createStatement();
		        ResultSet resultSet = statement.executeQuery(sql);
		        while (resultSet.next()) {
		            current = resultSet.getInt(3);
		            if (current>max) {
		            	max = current;
		            }
		        }        
		        resultSet.close();
		        disconnect();        
		    	return max;
	    	
	    }
	    public List<NFT> HotNFTs(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		    
	        List<NFT> listnfts = new ArrayList<NFT>();   
	        session = request.getSession();
	        NFTDAO NFTDAO = new NFTDAO();
	        NFT nft = new NFT();
	        int max =  getMaxOwners(); //this will return max value of owners
	        int current = 0;
	    	String sql = ("SELECT UniqueName, MAX(touser), COUNT(DISTINCT(touser)) from Transactions  GROUP BY UniqueName");
	    	connect_func();      
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        while (resultSet.next()) {
	            String uniqueName = resultSet.getString("UniqueName");
	            current = resultSet.getInt(3);
	            if (current>=max) {	//since we know max, this checks if there is no tie
	            	nft = NFTDAO.getNFT(uniqueName);
	            	listnfts.add(nft);
	            }
	        }        
	        resultSet.close();
	        disconnect();       
	        String type = "Hot NFTs";
	        session.setAttribute("max", max);
	        session.setAttribute("TType", type);
	        return listnfts;
	    }
	    public List<Own> listDiamondHands(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		    
	        List<Own> listUser = new ArrayList<Own>();   
	        session = request.getSession();
	        ownsDAO ownsDAO = new ownsDAO();
	        Own user = new Own();
	        //select touser, UniqueName from Transactions T where TransType ='s'and T.UniqueName in(select UniqueName from Transactions T2 where TransType='s') group by fromuser; #Diamond Hands

	        String sql = ("select * from ownsDatabase where UniqueName in (select UniqueName from Transactions where TransType = 's' group by UniqueName having count(distinct UniqueName)=1)");
	        connect_func();      
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        while (resultSet.next()) {
	            String nftname = resultSet.getString("uniqueName");
            	user = ownsDAO.getOwn(nftname);
            	listUser.add(user);
	        }        
	        resultSet.close();
	        disconnect();       
	        return listUser;
	    }
 public List<user> listPaperHands(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		    
	        List<user> listUser = new ArrayList<user>();   
	        session = request.getSession();
	        userDAO userDAO = new userDAO();
	        user user = new user();
	        String sql = (" select  fromuser, UniqueName from Transactions T1 where TransType = 's' and fromuser in (select  touser from Transactions T2 where TransType = 's' and T1.UniqueName = T2.UniqueName group by touser)group by T1.fromuser");
	        connect_func();      
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        while (resultSet.next()) {
	            String email = resultSet.getString("fromuser");
            	user = userDAO.getUser(email);
            	listUser.add(user);
	        }        
	        resultSet.close();
	        disconnect();       
	        return listUser;
	    }
	   
 public List<user> InactiveUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	   
     List<user> listUser = new ArrayList<user>();   
     session = request.getSession();
     userDAO userDAO = new userDAO();
     user user = new user();
// select email from user where email not in(select fromuser from Transactions); 

     String sql = (" select email from user where email not in(select fromuser from Transactions)");
     connect_func();      
     statement = (Statement) connect.createStatement();
     ResultSet resultSet = statement.executeQuery(sql);
     while (resultSet.next()) {
         String email = resultSet.getString("email");
     	user = userDAO.getUser(email);
     	listUser.add(user);
     }        
     resultSet.close();
     disconnect();       
     return listUser;
 }
 
 public List<user> GoodBuyers(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	    
     List<user> listUser = new ArrayList<user>();   
     session = request.getSession();
     String type = "s";
     userDAO userDAO = new userDAO();
     user user = new user();
     int current = 0;
     String useremail ="";
// select  touser from Transactions T1 where TransType = 's' group by touser having count( touser) = 3;

     String sql = ("select  touser from Transactions T1 where TransType = 's' group by touser having count( touser) = 3");
     connect_func();      
     statement = (Statement) connect.createStatement();
     ResultSet resultSet = statement.executeQuery(sql);
     while (resultSet.next()) {
         String email = resultSet.getString("touser");
     	user = userDAO.getUser(email);
     	listUser.add(user);
     }        
     resultSet.close();
     disconnect();       
     return listUser;
 }
 
 
	    public List<user> listBigCreators(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	
	        List<user> listUser = new ArrayList<user>();   
	        session = request.getSession();
	        String type = "m";
	        userDAO userDAO = new userDAO();
	        user user = new user();
	        int max =  getmaxTransType(type); //this will return max value of minted type
	        int current = 0;
	        String useremail ="";
	        String sql = ("SELECT fromuser, MAX(TransType), COUNT(TransType) from Transactions where TransType = 'm' GROUP BY fromuser");
	        connect_func();      
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        while (resultSet.next()) {
	            String email = resultSet.getString("fromuser");
	            current = resultSet.getInt(3);
	            if (current>=max) {	//since we know max, this checks if there is no tie
	            	useremail = email;
	            	//max = current;
	            	user = userDAO.getUser(useremail);
	            	listUser.add(user);
	            }
	        }        
	        resultSet.close();
	        disconnect();       
	        type = "Creators";
	        session.setAttribute("max", max);
	        session.setAttribute("TType", type);
	        return listUser;
	    }
	    public List<user> listBigSellers(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		    
	        List<user> listUser = new ArrayList<user>();   
	        session = request.getSession();
	        String type = "s";
	        userDAO userDAO = new userDAO();
	        user user = new user();
	        int max =  getmaxTransType(type); //this will return max value of minted type
	        int current = 0;
	        String useremail ="";
	        String sql = ("SELECT fromuser, MAX(TransType), COUNT(TransType) from Transactions where TransType = 's' GROUP BY fromuser");
	        connect_func();      
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        while (resultSet.next()) {
	            String email = resultSet.getString("fromuser");
	            current = resultSet.getInt(3);
	            if (current>=max) {	//since we know max, this checks if there is no tie
	            	useremail = email;
	            	//max = current;
	            	user = userDAO.getUser(useremail);
	            	listUser.add(user);
	            }
	        }        
	        resultSet.close();
	        disconnect();       
	        type = "Sellers";
	        session.setAttribute("max", max);
	        session.setAttribute("TType", type);
	        return listUser;
	    }
	    public List<user> listBigBuyers(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		    
	        List<user> listUser = new ArrayList<user>();   
	        session = request.getSession();
	        String type = "b";
	        userDAO userDAO = new userDAO();
	        user user = new user();
	        int max =  getmaxTransType(type); //this will return max value of minted type
	        int current = 0;
	        String useremail ="";
	        type = "s";
	        String sql = ("SELECT touser, MAX(TransType), COUNT(TransType) from Transactions where TransType = 's' GROUP BY touser");
	        connect_func();      
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        while (resultSet.next()) {
	            String email = resultSet.getString("touser");
	            current = resultSet.getInt(3);
	            if (current>=max) {	//since we know max, this checks if there is no tie
	            	useremail = email;
	            	user = userDAO.getUser(useremail);
	            	listUser.add(user);
	            }
	        }        
	        resultSet.close();
	        disconnect();       
	        type = "Buyers";
	        session.setAttribute("max", max);
	        session.setAttribute("TType", type);
	        return listUser;
	    }
	    public int getmaxTransType(String type) throws SQLException{
			// TODO Auto-generated method stub
	    	int max = 0;
	    	int current;
	    	if(type.equals("b")) {
	    		type = "s";
	    		String sql = ("SELECT touser, MAX(TransType), COUNT(TransType) from Transactions where TransType = '" + type + "' GROUP BY touser");
		        connect_func();      
		        statement = (Statement) connect.createStatement();
		        ResultSet resultSet = statement.executeQuery(sql);
		        while (resultSet.next()) {
		            current = resultSet.getInt(3);
		            if (current>max) {
		            	max = current;
		            }
		        }        
		        resultSet.close();
		        disconnect();        
		    	return max;
	    	}
	    	else {
	    	String sql = ("SELECT fromuser, MAX(TransType), COUNT(TransType) from Transactions where TransType = '" + type + "' GROUP BY fromuser");
	        connect_func();      
	        statement = (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        while (resultSet.next()) {
	            current = resultSet.getInt(3);
	            if (current>max) {
	            	max = current;
	            }
	        }        
	        resultSet.close();
	        disconnect();        
	    	return max;
	    	}
		}
		public void insert(String from,String to, String UniqueName,String Type,  double price)throws SQLException, FileNotFoundException, IOException, ParseException {
	    	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    	LocalDate dateObj = LocalDate.now();
         	DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String today = dateObj.format(formatter2);
            Date newdate = formatter.parse(today);
            
            Timestamp begin = new Timestamp(newdate.getTime());
	    	try {
	    	connect_func();
    		statement = (Statement) connect.createStatement();
    		
    		statement = connect.createStatement();
    		preparedStatement = connect.prepareStatement("insert into Transactions(fromuser,touser,UniqueName, Transtype,timestamp,price) values (?,?,?,?,?,?)");
    		
    		preparedStatement.setString(1, from);
    		preparedStatement.setString(2, to);
    		preparedStatement.setString(3, UniqueName);
    		preparedStatement.setString(4, Type);
    		preparedStatement.setTimestamp(5, begin);
    		preparedStatement.setDouble(6, price);
    		preparedStatement.executeUpdate();
	        
	    			
	        preparedStatement.close();
	    	}catch(Exception e) {
	    		System.out.println(e);
	    	}
	    	finally {
	    		disconnect();
	    	}
	    }
	  
	    public void fillDatabase()throws SQLException, FileNotFoundException, IOException, ParseException {
	    	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    	LocalDate dateObj = LocalDate.now();
         	DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String today = dateObj.format(formatter2);
            Date newdate = formatter.parse(today);
            
            Timestamp begin = new Timestamp(newdate.getTime());
	    	try {
	    		connect_func();
	    		statement = (Statement) connect.createStatement();
	    		
	    		statement = connect.createStatement();
	    		
	    		preparedStatement = connect.prepareStatement("insert into Transactions(fromuser,touser,UniqueName, Transtype,timestamp) values (?,?,?,?,?)");
	    		
	    		preparedStatement.setString(3, "UniqueNameAMELIA");
	    		preparedStatement.setString(1, "amelia@gmail.com");
	    		preparedStatement.setString(2, "amelia@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);    
	    		preparedStatement.executeUpdate();
	    		
	    	
	    		
	    		preparedStatement.setString(3, "UniqueNameANGELO");
	    		preparedStatement.setString(1, "angelo@gmail.com");
	    		preparedStatement.setString(2, "angelo@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(3, "DONdons");
	    		preparedStatement.setString(1, "don@gmail.com");
	    		preparedStatement.setString(2, "don@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(3, "Jeannnzzz");
	    		preparedStatement.setString(1, "jeannette@gmail.com");
	    		preparedStatement.setString(2, "jeannette@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(3, "JojojojoJoJO");
	    		preparedStatement.setString(1, "jo@gmail.com");
	    		preparedStatement.setString(2, "jo@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(3, "Margarar");
	    		preparedStatement.setString(1, "margarita@gmail.com");
	    		preparedStatement.setString(2, "margarita@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(3, "UniqueName");
	    		preparedStatement.setString(1, "root");
	    		preparedStatement.setString(2, "root");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(3, "RudyRude");
	    		preparedStatement.setString(1, "rudy@gmail.com");
	    		preparedStatement.setString(2, "rudy@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(3, "Sophs");
	    		preparedStatement.setString(1, "sophie@gmail.com");
	    		preparedStatement.setString(2, "sophie@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(3, "SUE");
	    		preparedStatement.setString(1, "susie@gmail.com");
	    		preparedStatement.setString(2, "susie@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
	    		preparedStatement.executeUpdate();
	    		
	    		preparedStatement.setString(3, "WoahWall");
	    		preparedStatement.setString(1, "wallace@gmail.com");
	    		preparedStatement.setString(2, "wallace@gmail.com");
	    		preparedStatement.setString(4, "m");
	    		preparedStatement.setTimestamp(5, begin);
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
	        statement.executeUpdate("DROP TABLE IF EXISTS Transactions");
	        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
	        statement.executeUpdate("use testdb");
	        
	        String sqlstmt ="CREATE TABLE IF NOT EXISTS Transactions " +
	        				"(transID INTEGER not NULL AUTO_INCREMENT," +
	        				"UniqueName VARCHAR(50) NOT NULL," +
	        				"fromuser VARCHAR(50)," + 
	        				"touser VARCHAR(50)," +
	        				"Transtype CHAR(1)," +  //t: transfer , s=sale, m= minted
	        				"timestamp TIMESTAMP," +
	        				"price DECIMAL(13,2) DEFAULT 0,"+
	        				"PRIMARY KEY (transID), " + 
	        				"FOREIGN KEY (fromuser) REFERENCES User(email)," +
	        				"FOREIGN KEY(UniqueName) REFERENCES NFTDatabase(UniqueName)," +
	        				"FOREIGN KEY (touser) REFERENCES User(email))";
	        
	        
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
