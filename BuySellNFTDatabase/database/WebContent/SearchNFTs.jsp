<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search for an NFT by using its Unique Name</title>
</head>
<body>
<div align="center">
		<p> ${errorOne } </p>
		<p> ${errorTwo } </p>
		
		<form action="search">
			<table border="3" cellpadding="5">
				<tr>
					<th>NFT name: </th>
					<td align="center" colspan="3">
						<input type="text" name="uniqueName" size="35"  value="Unique Name" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="5">
						<input type="submit" value="search"/>
					</td>
				</tr>
			</table>

			<a href="activitypage.jsp" target="_self">Return</a>
		</form>
	</div>
</body>
</html>