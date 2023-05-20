<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<% 
 session = request.getSession();
 String email= request.getParameter("email");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Paper Hands</title>
</head>
<body>

<div align = "center">
	
<%
Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
%>
	<a href="rootView.jsp"target ="_self" > return</a><br><br> 

<h1>Paper Hands</h1>
    <div align="center">
    	<form method="post" action="User.jsp"> 
          <table align="center" cellpadding="5" cellspacing="5" border="1">
     
	<tr bgcolor="#A52A2A">
	<td>Email</td>
	<td><b>Unique Name</b></td>
	</tr>
	
       <%
		try{ 
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
		statement=connection.createStatement();
        String sql = (" select  fromuser, UniqueName from Transactions T1 where TransType = 's' and fromuser in (select  touser from Transactions T2 where TransType = 's' and T1.UniqueName = T2.UniqueName group by touser)group by T1.fromuser");
		resultSet = statement.executeQuery(sql);
		while(resultSet.next()){
			
		
		%>

	<tr bgcolor="#DEB887">
<td><%=resultSet.getString("fromuser") %></td>
<td><%=resultSet.getString("UniqueName") %></td>
</tr>
<% 
}

} catch (Exception e) {
e.printStackTrace();
}
%>
</table>
      </form>
	</div>
	</div>

</body>
</html>