<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Timestamp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>NFT Page</title>
<body>
          	 <div align="center">
        <table border="3" cellpadding="10">
            <caption><h2>Listed NFT </h2></caption>
            <tr>
				<th>NFTID</th>
                <th>Unique name</th>
                <th>Description</th>
                <th>URL</th>
                <th>Creator</th>
  			</tr>
          
 <%
 session = request.getSession();
 Connection connection = null;
 Statement statement = null;
 ResultSet resultSet = null;
		try{ 
			
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
		statement=connection.createStatement();
		String uniqueName= request.getParameter("uniqueName");
		System.out.println(uniqueName);
		session.setAttribute("nftName", uniqueName);
		
		
	    String sql = ("SELECT * FROM NFTDatabase WHERE UniqueName =" + "'" + uniqueName + "'");   
		resultSet = statement.executeQuery(sql);
	      while (resultSet.next()) {
	        	int id = resultSet.getInt("NFTID");
	        	session.setAttribute("NFTID",id);
	            String name = resultSet.getString("UniqueName");
	            session.setAttribute("UniqueName",name);
	            String desc = resultSet.getString("Description");
	            String url = resultSet.getString("ImageURL");
	            String creatorEmail = resultSet.getString("Creator");
		%>
                <tr style="text-align:center">
					<td><%=resultSet.getInt("NFTID") %></td>
                   <td><%=resultSet.getString("UniqueName") %></td>
                	<td><%=resultSet.getString("Description") %></td>
                	<td><%=resultSet.getString("ImageURL") %></td>
                	<td><%=resultSet.getString("Creator") %></td>
                </tr>
                	
   </div>


<% 
}

} catch (Exception e) {
e.printStackTrace();
}
connection.close();
%>
   
      
   
      
 <%     
      //Check to see if NFT is in listings, if it is add a buy button
      int nftid = (int) session.getAttribute("NFTID");
 	  boolean islisted = false;
 	  boolean expired = false;
 	  String g="!";
 try{ 
	 connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
    statement=connection.createStatement();
    String sql = ("SELECT * FROM Listings where NFTID = '" + nftid + "'");      
	resultSet = statement.executeQuery(sql);
	while (resultSet.next()) {
			  islisted =true;
			  System.out.println("nft is in listings............");
	    	  int id = resultSet.getInt("NFTID");
	    	  String owner = resultSet.getString("owner");
	    	  Timestamp strt = resultSet.getTimestamp("start");
	    	  Timestamp end = resultSet.getTimestamp("end");
	    	  double price = resultSet.getDouble("price");
	    	  out.print("This NFT is listed as: " + price);           
	    	  session.setAttribute("price", price);
		      session.setAttribute("Owner", owner);
	    	  if(strt.compareTo(end) > 0) {
		      		expired = true;
		      	}
	    }
	if(islisted==true){
		islisted=true;
		if(expired==false){
		%>
		<form action = "Buy">
			<button id="button" class="Buy">Buy</button>
	</form>
		<%
		}
	}
	else {
		islisted=false;
	 %>  
	 <button id="button" hidden > </button>
	             <% 
	}
	}
     catch (Exception e) {
    e.printStackTrace();
    }
 connection.close();
      %>
    </button>
      
      
      
      
      

   	 <div align="center">
 <table border="3" cellpadding="10">
     <caption><h2>NFT Activity </h2></caption>
    
     <tr>
			<th>Transaction Type</th>
         <th>Price</th>
         <th>From</th>
         <th>To</th>
         <th>Date</th>
		</tr>
   
<%
session = request.getSession();
	try{ 
		
	connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
	statement=connection.createStatement();
	String uniqueName= request.getParameter("uniqueName");
	System.out.println(uniqueName);
	session.setAttribute("nftName", uniqueName);
	//uniqueName = uniqueName + "%";
 String sql = ("SELECT * FROM Transactions WHERE UniqueName =" + "'" + uniqueName + "'");   
	resultSet = statement.executeQuery(sql);
   while (resultSet.next()) {
     	double price= resultSet.getDouble("price");
         String fromuser = resultSet.getString("fromuser");
         session.setAttribute("UniqueName",fromuser);
         String touser = resultSet.getString("touser");
         String type = resultSet.getString("TransType");
         if(type.equals("s")){
        	 type = "Sold";
         }else if(type.equals("m")){
        	 type = "Minted";
         }else if(type.equals("t")){
        	 type ="Transferred";
         }
         String timestamp = resultSet.getString("timestamp");
	%>
         <tr style="text-align:center">
         <td><%=type %></td>
			<td><%=resultSet.getDouble("price") %></td>
            <td><%=resultSet.getString("fromuser") %></td>
         	<td><%=resultSet.getString("touser") %></td>
         	<td><%=resultSet.getString("timestamp") %></td>
         </tr>
         	
</div>


<% 
}

} catch (Exception e) {
e.printStackTrace();
}
connection.close();
%>

      
      
      
    </div>   
 <div align="center">
 <br></br>

    <br></br>
	<a href="activitypage.jsp" target="_self">Return</a>
</div>
</body>
</html>