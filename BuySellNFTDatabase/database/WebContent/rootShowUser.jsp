<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Profile Page</title>
<body>

          	 <div align="center">
        <table border="3" cellpadding="10">
            <caption><h2>Listed User </h2></caption>
           
            <tr>
				<th>Email</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Birthday</th>
                <th>State</th>
                <th>Ethereum</th>
  			</tr>
          
 <%
 session = request.getSession();
 Connection connection = null;
 Statement statement = null;
 ResultSet resultSet = null;

 
 
		try{ 
			
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
		statement=connection.createStatement();
		String userId= request.getParameter("email");
		System.out.println(userId);
		session.setAttribute("userId", userId);
		String otherUser = userId;
		otherUser = otherUser + "%";
		
	    String sql = ("SELECT * FROM User WHERE email LIKE" + "'" + otherUser + "'");   
		resultSet = statement.executeQuery(sql);
		 while (resultSet.next()) {
	            String email = resultSet.getString("email");
	            String firstName = resultSet.getString("firstName");
	            String lastName = resultSet.getString("lastName");
	            String password = resultSet.getString("password");
	            String birthday = resultSet.getString("birthday");
	            String adress_street_num = resultSet.getString("adress_street_num"); 
	            String adress_street = resultSet.getString("adress_street"); 
	            String adress_city = resultSet.getString("adress_city"); 
	            String adress_state = resultSet.getString("adress_state"); 
	            String adress_zip_code = resultSet.getString("adress_zip_code"); 
	            double eth = resultSet.getDouble("eth");
			//Create Examine profile page which then shows users information, eth, and nfts that they own
		%>
                <tr style="text-align:center">
					<td><%=resultSet.getString("email") %></td>
                   <td><%=resultSet.getString("firstname") %></td>
                	<td><%=resultSet.getString("lastname") %></td>
                	<td><%=resultSet.getString("birthday") %></td>
                	<td><%=resultSet.getString("adress_state") %></td>
                	<td><%=resultSet.getDouble("eth") %></td>
                </tr>
                	
   </div>


<% 
}

} catch (Exception e) {
e.printStackTrace();
}
%>
     </table>
      <table align="center" cellpadding="5" cellspacing="5" border="1">
      <tr bgcolor="#A52A2A">
      <td><b>unique Name</b></td>
      </tr>
      
        <%
		try{ 
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
		statement=connection.createStatement();
		String ID = (String) session.getAttribute("userId");
		String sql =("SELECT * FROM ownsDatabase where email = '" + ID + "'");
		resultSet = statement.executeQuery(sql);
		while(resultSet.next()){
		%>
	<tr bgcolor="#DEB887">
<td><%=resultSet.getString("uniqueName") %></td>
</tr>
<% 
}

} catch (Exception e) {
e.printStackTrace();
}
%>
</table>
      
      
    </div>   
 <div align="center">
	<a href="rootView.jsp" target="_self">Return</a>
</div>
</body>
</html>