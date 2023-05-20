import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;


public class ControlServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private userDAO userDAO = new userDAO();
	    private NFTDAO NFTDAO = new NFTDAO();
	    private ownsDAO ownsDAO = new ownsDAO();
	    private Listings Listings = new Listings();
	    private Transactions Transactions = new Transactions();
	    private String currentUser;
	    private HttpSession session=null;
	    
	    public ControlServlet()
	    {
	    	
	    }
	    
	    public void init()
	    {
	    	userDAO = new userDAO();
	    	NFTDAO = new NFTDAO();
	    	ownsDAO = new ownsDAO();
	    	Listings = new Listings();
	    	Transactions = new Transactions();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getServletPath();
	        System.out.println(action);
	    try {
        	switch(action) {  
        	case "/login":
        		login(request,response);
        		break;
        	case "/register":
        		register(request, response);
        		break;
        	case "/initialize":
        		userDAO.init();
        		System.out.println("Database for User successfully initialized!");
        		NFTDAO.init();
        		System.out.println("Database for NFT successfully initialized!");
        		NFTDAO.fillDatabase();
        		System.out.println("NFTDAO filled!");
        		ownsDAO.init();
        		System.out.println("Database for ownsDAO successfully initialized!");
        		ownsDAO.fillDatabase();
        		System.out.println("ownsDatabase filled");
        		Listings.init();
        		System.out.println("Listings initialized");
        		Transactions.init();
        		Transactions.fillDatabase();
        		System.out.println("Transactions initialized");
        		rootPage(request,response,"");
        		break;
        	case "/bigCreators":
            	session = request.getSession();
        		getBigUsers(request,response,"creators");
        		Transactions.listBigCreators(request, response);
        		break;
        	case "/bigSellers":
            	session = request.getSession();
        		getBigUsers(request,response,"sellers");
        		Transactions.listBigSellers(request, response);
        		break;
        	case "/bigBuyers":
            	session = request.getSession();
        		getBigUsers(request,response,"buyers");
        		Transactions.listBigBuyers(request, response);
        		break;
          	case "/HotNFTs":
            	session = request.getSession();
        		hotNFTs(request,response,"");
        		Transactions.HotNFTs(request, response);
        		break;
          	case "/CommonNFTs":
          		System.out.println("Common called");
          		commonNFTS(request,response,"");
    	  	 	break;
           	case "/DiamondHands":
            	session = request.getSession();
        		DiamondHands(request,response,"");
        		break;
          	case "/PaperHands":
            	session = request.getSession();
        		PaperHands(request,response,"");
        		break;
           	case "/GoodBuyers":
            	session = request.getSession();
        		GoodBuyers(request,response,"");
        		break;
           	case "/InactiveUsers":
            	session = request.getSession();
            	InactiveUsers(request,response,"");
        		break;
          	case "/rootSearchUser":
            	session = request.getSession();
            	rootSearchUser(request,response,"");
        		break;
        	case "/Statistics":
            	session = request.getSession();
            	Statistics(request,response,"");
        		break;
        	case "/root":
        		rootPage(request,response, "");
        		break;
        	case "/mint":
        		mint(request,response, "");
        		System.out.println("Mint called");
        		break;
        	case "/search":
        		search(request,response, "");
        		System.out.println("search called");
        		break;
        	case "/searchUser":
        		searchUser(request,response, "");
        		System.out.println("search called");
        		break;
        	case "/rootSearchingUser":
        		rootSearchingUser(request,response, "");
        		System.out.println("search called");
        		break;
        	case "/Buy":
        		buy(request,response,  "");
        		System.out.println("Buy finished" );
        		break;	
        	case "/transfer":
        		transferTo(request,response, "");
        		System.out.println("Transfer called");
        		break;
        	case "/listNFT":
        		listNFT(request,response, "");
        		System.out.println("LISTNFT called");
        		break;
        	case "/logout":
        		logout(request,response);
        		break;
        	 case "/list": 
                 System.out.println("The action is: list");
                 listUser(request, response);           	
                 break;
	    	}
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
		private void listUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        System.out.println("listUser started: 00000000000000000000000000000000000");
	        List<user> listUser = userDAO.listAllUsers();
	        request.setAttribute("listUser", listUser);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");       
	        dispatcher.forward(request, response);
	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
	    }          
	    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
	    	System.out.println("root view");
			request.setAttribute("listUser", userDAO.listAllUsers());
	    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    }
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	 String email = request.getParameter("email");
	    	 String password = request.getParameter("password");
	    	 String name = request.getParameter("firstName");    	 
	    	 if (email.equals("root") && password.equals("pass1234")) {
				 System.out.println("Login Successful! Redirecting to root");
				 session = request.getSession();
				 session.setAttribute("username", email);
				 rootPage(request, response, "");
	    	 }
	    	 else if(userDAO.isValid(email, password)) 
	    	 {
	    		 user user1 = userDAO.getUser(email);
			 	 session = request.getSession();
			 	 session.setAttribute("currentUser", user1.getFirstName());
			 	 session.setAttribute("currentUserEmail", user1.getEmail());
			 	 session.setAttribute("currentUserEth", user1.getEth());
			 	 currentUser = email;
			 	 
				 System.out.println("Login Successful! Redirecting");
				 
				 request.getRequestDispatcher("activitypage.jsp").forward(request, response);
			 			 			 			 
	    	 }
	    	 else {
	    		 request.setAttribute("loginStr","Login Failed: Please check your credentials.");
	    		 request.getRequestDispatcher("login.jsp").forward(request, response);
	    		 request.setAttribute("listowns", ownsDAO.listAllNFTS());
	    	 }
	    }
	    private void search(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	      	String uniqueName = request.getParameter("uniqueName");
	      	session.setAttribute("searchednfts",  uniqueName);
	      	NFTDAO nftdao = new NFTDAO();
	      	nftdao.ListSearchedNFTS(uniqueName);
	     	request.setAttribute("listnfts", nftdao.ListSearchedNFTS(uniqueName));
	     	//change get dispatch to show nft files
	      	request.getRequestDispatcher("searchingNFTS.jsp").forward(request, response);

	 		}
	    private void hotNFTs(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	     	request.setAttribute("listnfts", Transactions.HotNFTs(request, response));
	     	//change get dispatch to show nft files
	      	request.getRequestDispatcher("HotNfts.jsp").forward(request, response);

	 		}
	    private void commonNFTS(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	    	//send to webpage where you choose 2 users to show common nfts
	  	 	response.sendRedirect("CommonNFTs.jsp");
	 		}
	    private void DiamondHands(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	      	request.getRequestDispatcher("DiamondHands.jsp").forward(request, response);
	 		}
	    private void PaperHands(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	      	request.getRequestDispatcher("PaperHands.jsp").forward(request, response);
	 		}
	    private void GoodBuyers(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	     	request.setAttribute("listUser", Transactions.GoodBuyers(request, response));

	      	request.getRequestDispatcher("GoodBuyers.jsp").forward(request, response);
	 		}
	    private void InactiveUsers(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	     	request.setAttribute("listUser", Transactions.InactiveUsers(request, response));

	      	request.getRequestDispatcher("InactiveUsers.jsp").forward(request, response);
	 		}
	    private void rootSearchUser(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	    	request.getRequestDispatcher("rootSearchUser.jsp").forward(request, response);

	 		}
	    private void Statistics(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	    	request.getRequestDispatcher("Statistics.jsp").forward(request, response);

	 		}
	    
	    private void searchUser(HttpServletRequest request, HttpServletResponse response, String userEmail) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	      	String otherUserEmail = request.getParameter("email");
	      	session.setAttribute("searchedusers", otherUserEmail);
	      	userDAO otheruser = new userDAO();
	      	otheruser.listSearchedUsers(otherUserEmail);
	      	user searchedUser = new user();
	      	request.setAttribute("listUser", userDAO.listSearchedUsers(otherUserEmail));
	      	request.getRequestDispatcher("listusers.jsp").forward(request, response);
	 		}
	    private void rootSearchingUser(HttpServletRequest request, HttpServletResponse response, String userEmail) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	      	String otherUserEmail = request.getParameter("email");
	      	session.setAttribute("searchedusers", otherUserEmail);
	      	userDAO otheruser = new userDAO();
	      	otheruser.listSearchedUsers(otherUserEmail);
	      	user searchedUser = new user();
	      	request.setAttribute("listUser", userDAO.listSearchedUsers(otherUserEmail));
	      	request.getRequestDispatcher("rootlistusers.jsp").forward(request, response);
	 		}
	    private void getBigUsers(HttpServletRequest request, HttpServletResponse response, String type) throws  ServletException, IOException, SQLException {
	    	session = request.getSession();
	    	if(type.equals("creators")){
		      	request.setAttribute("listUser", Transactions.listBigCreators(request, response));
		      	request.getRequestDispatcher("bigCreators.jsp").forward(request, response);
	    	}
	    	else if(type.equals("sellers")) {
	         	request.setAttribute("listUser", Transactions.listBigSellers(request, response));
		      	request.getRequestDispatcher("bigCreators.jsp").forward(request, response);
	    	}
	    	else if(type.equals("buyers")) {
	         	request.setAttribute("listUser", Transactions.listBigBuyers(request, response));
		      	request.getRequestDispatcher("bigCreators.jsp").forward(request, response);
	    	}
	    
	 		}
	    private void buy(HttpServletRequest request, HttpServletResponse response, String string) throws  ServletException, IOException, SQLException, ParseException {
	    	session = request.getSession();
	    	String uniquename = (String) session.getAttribute("nftName");
	    	double price = (double) session.getAttribute("price");
	    	double eth = (double) session.getAttribute("currentUserEth");
	    	if(eth<price) {
			 System.out.println("Not enough Ethereum to buy NFT");
		    	response.sendRedirect("SearchNFTs.jsp");
		 }
		 else {
	 		//get time stamps and see if its expired
			 int nftid = (int) session.getAttribute("NFTID");	 
			 String email = (String) session.getAttribute("currentUserEmail");
			 String owner = (String) session.getAttribute("Owner");
			 if(email.equals(owner)) {
				 System.out.println("You cannot buy your own NFT");
				 response.sendRedirect("activitypage.jsp");
			 }
			 else {
			 user Buyer = userDAO.getUser(email);
	 		 user Seller = userDAO.getUser(owner);
	 		 double amountLost = eth - price;
	 		 Buyer.setEth(amountLost);
	      	 session.setAttribute("currentUserEth", amountLost);
	 		 double sellersEther = Seller.getEth();
	 		 Seller.setEth(sellersEther + price);
	 		 //Update two users eth amount
	 		 userDAO.update(Buyer, email);
	 		 userDAO.update(Seller, owner);
	 		 //Update ownership 
	 		 ownsDAO.delete(uniquename);
	 		 ownsDAO.insert(email, uniquename);
	 		 //Update Listings database
	 		 Listings.delete(nftid);
	 		//Transactions.insert(email, owner,uniquename, "b", price); //from email bought to user
	 		Transactions.insert(owner, email,uniquename, "s", price); //from owner sold to email
	 		 System.out.println("Bought NFT");
	  	 	response.sendRedirect("activitypage.jsp");
	  	 	}
			 
		 }
		 
	    }
	    protected void transferTo(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException, ParseException {
	    session = request.getSession();
	    NFT nft = null;
	   	String email = (String) session.getAttribute("currentUserEmail");
	   	String uniqueName = request.getParameter("uniqueName");
	   	String giftuser = request.getParameter("email");
	   	boolean ans = false;
		//we own the NFT about to be transferred if ans returns true
    	ans = ownsDAO.doesOwn(request, response, email, uniqueName);
    	if(ans == true) {
    		ans = userDAO.checkEmail(giftuser);
    		//if ans returns true again that means there us a user with that gift user email
    		if(ans == true) {
    		  	nft = NFTDAO.getNFT(uniqueName);
	         	int nftid = nft.getNFTID();
    			 System.out.println("ans returned true");
    			//gift to that user, delete old uniquename and insert uniquename to giftuser
    			 ownsDAO.delete(uniqueName);
    			 Listings.delete(nftid);
    			 ownsDAO.insert(giftuser, uniqueName);
    			 Transactions.insert(email, giftuser,uniqueName, "t", 0);

    		}
    		else {
    			 System.out.println("user not in database");
    		}
    	}
  	 	response.sendRedirect("activitypage.jsp");
	    }
	    

	    protected void listNFT(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	    	
	    	session = request.getSession();
	    	NFT nft = null;
			 String email = (String) session.getAttribute("currentUserEmail");
	    	 System.out.println(email);
	    	System.out.println("list NFT Method called");
	    	String uniqueName = request.getParameter("uniqueName");
	  	 	String fprice = request.getParameter("price");
	   	 	double price = Double.parseDouble(fprice);
	   	 	String duration = request.getParameter("duration");
	   	   System.out.println(uniqueName);
	    	boolean ans = false;
	    	ans = ownsDAO.doesOwn(request, response, email, uniqueName);
	    	if(ans == true) {
	            System.out.println("ownsDao does own returned True");
	            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	 			Date date;
				try {
					date = formatter.parse(duration);
	 			Timestamp timestamp = new Timestamp(date.getTime());
	 			System.out.println(timestamp);
	         	nft = NFTDAO.getNFT(uniqueName);
	         	nft.setPrice(price);
	         	int nftid = nft.getNFTID();
	         	LocalDate dateObj = LocalDate.now();
	         	DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	            String today = dateObj.format(formatter2);
	            Date newdate = formatter.parse(today);
	            Timestamp begin = new Timestamp(newdate.getTime());
	            Listings.insert(email,nftid, begin,timestamp , price);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 	response.sendRedirect("activitypage.jsp");
				}
	            //get timestamps here
	            
	    	}
	    	else {
	            System.out.println("RETURNED FALSE DOES NOT OWN NFT");
	    	}
	  	 	response.sendRedirect("activitypage.jsp");
	    }

	    protected void mint(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException, ParseException {
	    	session = request.getSession();
	    	String creator = (String) session.getAttribute("currentUserEmail");
	    	System.out.println("Mint Method called");
	   	 	String uniqueName = request.getParameter("uniqueName");
	   	 	String description = request.getParameter("description");
	  	 	String imageURL = request.getParameter("imageURL");
	  	 	NFT nft = new NFT(uniqueName,description, imageURL, creator);
	  	 	NFTDAO.insert(nft);
	  	 	ownsDAO.insert(currentUser, uniqueName);
	  	 	Transactions.insert(creator, creator,uniqueName, "m", 0);
	  	 	response.sendRedirect("activitypage.jsp");
	    }
	    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String email = request.getParameter("email");
	    	System.out.println("email private void register finished: ");
	   	 	String firstName = request.getParameter("firstName");
	   	 	String lastName = request.getParameter("lastName");
	   	 	String password = request.getParameter("password");
	   	 	String birthday = request.getParameter("birthday");
	   	 	String adress_street_num = request.getParameter("adress_street_num"); 
	   	 	String adress_street = request.getParameter("adress_street"); 
	   	 	String adress_city = request.getParameter("adress_city"); 
	   	 	String adress_state = request.getParameter("adress_state"); 
	   	 	String adress_zip_code = request.getParameter("adress_zip_code");
	   	 	String confirm = request.getParameter("confirmation");
	   	 	
	   	 	
	   	 	if (password.equals(confirm)) {
	   	 		if (!userDAO.checkEmail(email)) {
		   	 		System.out.println("Registration Successful! Added to database");
		            user users = new user(email,firstName, lastName, password, birthday, adress_street_num,  adress_street,  adress_city,  adress_state,adress_zip_code, 100);
		   	 		userDAO.insert(users);
		   	 		response.sendRedirect("login.jsp");
	   	 		}
		   	 	else {
		   	 		System.out.println("Username taken, please enter new username");
		    		 request.setAttribute("errorOne","Registration failed: Username taken, please enter a new username.");
		    		 request.getRequestDispatcher("register.jsp").forward(request, response);
		   	 	}
	   	 	}
	   	 	else {
	   	 		System.out.println("Password and Password Confirmation do not match");
	   		 request.setAttribute("errorTwo","Registration failed: Password and Password Confirmation do not match.");
	   		 request.getRequestDispatcher("register.jsp").forward(request, response);
	   	 	}
	    }    
	    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	currentUser = "";
        		response.sendRedirect("login.jsp");
        	}
	
	    

	     
        
	    
	    
	    
	    
	    
}
	        
	        
	    
	        
	        
	        
	    


