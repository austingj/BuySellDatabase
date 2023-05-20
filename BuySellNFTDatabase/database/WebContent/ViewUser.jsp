<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>View Users</title>
<body>

          	 <div align="center">
        <table id="table" border="3" cellpadding="10">
            <caption><h2>List of People</h2></caption>
           
            <tr>
				<th>Email</th>
                <th>First name</th>
  			</tr>
          
 <%
 session = request.getSession();
 Connection connection = null;
 Statement statement = null;
 ResultSet resultSet = null;

 
 
		try{ 
			
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
		statement=connection.createStatement();
		//String otherUser = (String) session.getAttribute("searchedusers");
		String otherUser = (String) session.getAttribute("userId");
		otherUser = otherUser + "%";
		int i = 0;
	    String sql = ("SELECT * FROM User WHERE email LIKE" + "'" + otherUser + "'");   
		resultSet = statement.executeQuery(sql);
		String[] names = new String[50];

		 while (resultSet.next()) {
			 String email = new String("");
			    email = new String(resultSet.getString("email"));
	            String firstName = resultSet.getString("firstName");
				
	       
	            
			//Create Examine profile page which then shows users information, eth, and nfts that they own
		
		%>

	
  			
             	   <tr style="text-align:center">
                   <td><a href = "User.jsp" target ="_self" class 'alert' onClick ="function();"<%System.out.println(email); %>> <%= email = new String(resultSet.getString("email")) %></a> </td>
          
                
                
                </tr>
     
   </div>


<% 
}

} catch (Exception e) {
e.printStackTrace();
}
%>

     </table>


	
		<div align="center">
		<a href="activitypage.jsp" target="_self">Return</a>
		</div>

</body>

</html>