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
<title>Showing Common NFTs</title>
</head>
<body>
<%
Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;

String param = request.getParameter("fname");
System.out.println(param);
String parts[] = param.split(",");
for(String part: parts){
	System.out.println(part);
}
String first = parts[0];
String second = parts[1];
System.out.println("first: " +first +"  second: ");
System.out.println(second + "");
%>
 <table align="center" cellpadding="5" cellspacing="5" border="1">
     
	<tr bgcolor="#A52A2A">
	<td><b>Listing Common NFTs</b></td>
	</tr>
	
       <%
		try{ 
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
		statement=connection.createStatement();
		String ID = (String) session.getAttribute("currentUserEmail");
		String sql =("SELECT UniqueName FROM Transactions where touser in('" + first + "','" + second + "') group by UniqueName having count(distinct touser)=2");
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



	<a href="CommonNFTs.jsp" target="_self">Common NFTs</a>
</body>
</html>