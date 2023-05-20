<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Searched Users</title>
</head>
<body>
<% 
 session = request.getSession();
 String email= request.getParameter("email");
 String otherUser = (String) session.getAttribute("searchedusers");
 System.out.println(otherUser);
 System.out.println("On rootlistusers.jsp");

%>
<div align = "center">
	

	<a href="rootView.jsp"target ="_self" > return</a><br><br> 

<h1>List all users</h1>
    <div align="center">
    	<form method="post" action="User.jsp"> 
        <table border="3" cellpadding="6">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>Email</th>
                <th>First name</th>
   
            </tr>
            <c:forEach var="users" items="${listUser}">
                <tr style="text-align:center">
                    <td><input type="hidden" name="email" value="${users.email}"<%session.setAttribute("userId",email);request.getAttribute("email"); ;%>>
        	<a href = "Statistics.jsp?email=${users.email}"target ="_self""><c:out value="${users.email}" /></a></td>
                    <td><c:out value="${users.firstName}" /></td>
			
            </c:forEach>
        </table>
      </form>
	</div>
	</div>

</body>
</html>