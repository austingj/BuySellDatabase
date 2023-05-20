<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div align="center">
		<p> ${errorOne } </p>
		<p> ${errorTwo } </p>
		 <%
		 session = request.getSession();
		 if(session != null){
			 if(session.getAttribute("currentUser") != null){
				 String name = (String) session.getAttribute("currentUser");
				 //out.print(name);
			 }
		 }
		 double eth = (double) session.getAttribute("currentUserEth");
		 String fname = (String) session.getAttribute("currentUser");
		 String email = (String) session.getAttribute("currentUserEmail");
		
		 //String pass = request.getParameter("password");
		 out.print("Welcome "  + " " + fname + "<br>" + " Email :" + email  + "<br>" + " eth: " + eth);
		 //String unique = (String) session.getAttribute("uniqueName");
		 
		 Connection connection = null;
		 Statement statement = null;
		 ResultSet resultSet = null;

		 %>
		<form action="listNFT">
			<table border="3" cellpadding="5">
			<tr>
					<th>Unique Name: </th>
					<td align="center" colspan="3">
						<input type="text" name="uniqueName" size="35"  value="unique name" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Price: </th>
					<td align="center" colspan="3">
						<input type="text" name="price" size="35"  value="99" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Duration: </th>
					<td align="center" colspan="3">
						<input type="text" name="duration" size="35" value="MM/DD/YYYY  11/7/2022" onfocus="this.value=''">
					</td>
				</tr>
					<td align="center" colspan="5">
						<input type="submit" value="listNFT"/>
					</td>
				</tr>
			</table>

			<a href="activitypage.jsp" target="_self">Return</a>
		</form>
	</div>
</body>
</html>