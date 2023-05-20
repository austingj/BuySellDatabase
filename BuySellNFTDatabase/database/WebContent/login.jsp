<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login to Database</title>
</head>
<body>
 <center>	<h1> Welcome to NFT Options Login page </h1> </center>
	<div align="center">
		<p> ${loginFailedStr} </p>
		<form action="login" method="post">
			<table border="3" cellpadding="10">
				<tr>
					<th>User: </th>
					<td>
						<input type="text" name="email" size="55" autofocus>
					</td>
				</tr>
				<tr>
					<th>Password: </th>
					<td> 
						<input type="password" name="password" size="55">
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center">
						<input type="submit" value="Login"/>
					</td>
				</tr>
			</table>
			<a href="register.jsp" target="_self">Register Here</a>
		</form>
	</div>
</body>
</html>