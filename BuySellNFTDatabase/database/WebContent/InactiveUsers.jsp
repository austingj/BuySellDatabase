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
<title>Inactive Users</title>
</head>
<body>

<div align = "center">
	

	<a href="rootView.jsp"target ="_self" > return</a><br><br> 

<h1>Inactive Users</h1>
    <div align="center">
    	<form method="post" action="User.jsp"> 
        <table border="3" cellpadding="6">
            <caption><h2>Users who have not minted,sold,bought,transferred any NFTs </h2></caption>
            <tr>
                <th>Email</th>
                <th>First name</th>
   
   
            </tr>
            <c:forEach var="users" items="${listUser}">
                <tr style="text-align:center">
                    <td><input type="hidden" name="email" value="${users.email}"<%session.setAttribute("userId",email);request.getAttribute("email"); ;%>>
        	<a href = "rootShowUser.jsp?email=${users.email}"target ="_self""><c:out value="${users.email}" /></a></td>
                    <td><c:out value="${users.firstName}" /></td>
       
			
            </c:forEach>
        </table>
      </form>
	</div>
	</div>

</body>
</html>