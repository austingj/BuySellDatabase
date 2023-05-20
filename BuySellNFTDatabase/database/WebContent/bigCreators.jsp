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
 String Type = (String) session.getAttribute("TType");
int max = (int) session.getAttribute("max");


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Big <%=Type %></title>
</head>
<body>

<div align = "center">
	

	<a href="rootView.jsp"target ="_self" > return</a><br><br> 

<h1>List Big <%=Type%></h1>
    <div align="center">
    	<form method="post" action="User.jsp"> 
        <table border="3" cellpadding="6">
            <caption><h2>Big <%=Type %> </h2></caption>
            <tr>
                <th>Email</th>
                <th>First name</th>
                <th>Amount</th>
   
            </tr>
            <c:forEach var="users" items="${listUser}">
                <tr style="text-align:center">
                    <td><input type="hidden" name="email" value="${users.email}"<%session.setAttribute("userId",email);request.getAttribute("email"); ;%>>
        	<a href = "rootShowUser.jsp?email=${users.email}"target ="_self""><c:out value="${users.email}" /></a></td>
                    <td><c:out value="${users.firstName}" /></td>
                    <td><%=max%></td>
			
            </c:forEach>
        </table>
      </form>
	</div>
	</div>

</body>
</html>