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
<title>Root page</title>
</head>
<body>
<% 

session = request.getSession();
if(session != null){
	 if(session.getAttribute("currentUser") != null){
		 String name = (String) session.getAttribute("currentUser");
		 //out.print(name);
	 }
}
double bal = 0.0;
session.setAttribute("currentUserEth",bal);
session.setAttribute("currentUser","root");
session.setAttribute("currentUserEmail","root");


double eth = (double) session.getAttribute("currentUserEth");
String fname = (String) session.getAttribute("currentUser");
String email = (String) session.getAttribute("currentUserEmail");

Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;

session = request.getSession();


%>
<div align = "center">
	
	<form action = "initialize">
		<input type = "submit" value = "Initialize the Database"/>
	</form>
	<a href="login.jsp"target ="_self" > logout</a><br><br> 
	<table>
	<tr>
	<td>
	<form action = "bigCreators">
	<input type = "submit" value = "Big Creators"/>	
	</form>
	</td>
	<td>
	<form action = "bigSellers">
		<input type = "submit" value = "Big Sellers"/>
	</form>
	</td>
	<td>
	<form action = "bigBuyers">
		<input type = "submit" value = "Big Buyers"/>
	</form>
	</td>
	</tr>
	
	<tr>
	<td><form action = "HotNFTs">
		<input type = "submit" value = "Hot NFTs"/>
	</form></td>
	<td><form action = "CommonNFTs">
		<input type = "submit" value = "Common NFTs"/>
	</form></td>
		<td><form action = "DiamondHands">
		<input type = "submit" value = "Diamond Hands"/>
	</form></td>
	</tr>
	<tr>
		<td><form action = "PaperHands">
		<input type = "submit" value = "Paper Hands"/>
	</form></td>
		<td><form action = "GoodBuyers">
		<input type = "submit" value = "Good Buyers"/>
	</form></td>
		<td><form action = "InactiveUsers">
		<input type = "submit" value = "Inactive Users"/>
	</form></td>
	</tr>
	
	<tr>
	<td><form action = "rootSearchUser">
		<input type = "submit" value = "Statistics"/>
	</form></td>
	</tr>
	</table>
</div>

<div align = "center">
<h1>List all users</h1>
    <div align="center">
        <table border="3" cellpadding="6">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>Email</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Address</th>
                <th>Password</th>
                <th>Birthday</th>
                <th>Ethereum</th>
            </tr>
            <c:forEach var="users" items="${listUser}">
                <tr style="text-align:center">
                    <td><c:out value="${users.email}" /></td>
                    <td><c:out value="${users.firstName}" /></td>
                    <td><c:out value="${users.lastName}" /></td>
                    <td><c:out value= "${users.adress_street_num} ${users.adress_street} ${users.adress_city} ${users.adress_state} ${users.adress_zip_code}" /></td>
                    <td><c:out value="${users.password}" /></td>
                    <td><c:out value="${users.birthday}" /></td>
                    <td><c:out value="${users.eth}"/></td>
            </c:forEach>
        </table>
	</div>
	</div>

</body>
</html>