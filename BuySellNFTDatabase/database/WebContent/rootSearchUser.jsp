<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search for an User</title>
</head>
<body>
<div align="center">
		<p> ${errorOne } </p>
		<p> ${errorTwo } </p>
		
		<form action=rootSearchingUser>
			<table border="3" cellpadding="5">
				<tr>
					<th>User Email: </th>
					<td align="center" colspan="3">
						<input type="text" name="email" size="35"  value="example@gmail.com" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="5">
						<input type="submit" value="search"/>
					</td>
				</tr>
			</table>

			<a href="rootView.jsp" target="_self">Return</a>
		</form>
	</div>
</body>
</html>