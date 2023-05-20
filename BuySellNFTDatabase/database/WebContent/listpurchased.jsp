<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Purchased NFTS</title>
</head>

	<body>
	
	    <div align = "center">
	 
		 <a href="login.jsp"target ="_self" > logout</a><br><br> 
		  <div align="center">
	<a href="activitypage.jsp" target="_self">Return</a>
</div>
		 <%
		 session = request.getSession();
		 if(session != null){
			 if(session.getAttribute("currentUser") != null){
				 String name = (String) session.getAttribute("currentUser");
				 //out.print(name);
			 }
		 }
		 String fname = (String) session.getAttribute("currentUser");
		 String email = (String) session.getAttribute("currentUserEmail");
		 
		 Connection connection = null;
		 Statement statement = null;
		 ResultSet resultSet = null;

		 %>
        </div>
            <table align="center" cellpadding="5" cellspacing="5" border="1">
     
	<tr bgcolor="#A52A2A">
	<td><b>unique Name</b></td>
	</tr>
	
       <%
		try{ 
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
		statement=connection.createStatement();
		String ID = (String) session.getAttribute("currentUserEmail");
		String sql =("SELECT * FROM Transactions where touser = '" + ID + "' AND TransType = 's'");
		resultSet = statement.executeQuery(sql);
		while(resultSet.next()){
			
		
		%>

	<tr bgcolor="#DEB887">
<td><%=resultSet.getString("UniqueName") %></td>
</tr>
<% 
}

} catch (Exception e) {
e.printStackTrace();
}
%>
</table>
       <div align = "center">
       <tr>
		 	<td><a href="mint.jsp" target="_self">Mint NFT Here</a></td>
		 	<td><a href="list.jsp" target="_self">List NFT for sale</a></td>
		    <td><a href="transfer.jsp" target="_self">Transfer NFT to User</a></td>
		    <td><a href="SearchNFTs.jsp" target="_self">Search for NFT</a></td>
		</tr>
		<tr>
		     <td><a href="SearchUser.jsp" target="_self">Search for User</a></td>
		     <td><a href="listminted.jsp" target="_self">List NFTS that You Minted</a>
		     <td><a href="listpurchased.jsp" target="_self">List NFTS that You Purchased</a>
		     <td><a href="listsold.jsp" target="_self">List NFTS that You Sold</a>
		</tr>
		 	</div>
		 	
	</body>
</html>