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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choose 2 users from Drop Downlist</title>
</head>
<body>

	<a href="rootView.jsp"target ="_self" > return</a><br><br> 
	

<%
session = request.getSession();
Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
String fname ="";
String name2 ="";
try{
connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
statement=connection.createStatement();
String ID = (String) session.getAttribute("currentUserEmail");
String sql =("SELECT * FROM User");
resultSet = statement.executeQuery(sql);
%>
<form action="#">
<%
%>
<p>Select User 1 :
<select id="user1" onchange="getOption()">
<%
while(resultSet.next()){
fname = resultSet.getString("email");
%>
<option value="<%=fname %>"<% session.setAttribute("fname",fname);%>> <%=fname %> </option>
<%
}
%>
</select>
</p>



    <script type="text/javascript">
    function getOption() {
        selectElement = document.querySelector('#user1');
        selectElement2 = document.querySelector('#user2');
        output = selectElement.options[selectElement.selectedIndex].value;
        output2 = selectElement2.options[selectElement2.selectedIndex].value;
        document.querySelector('.output').textContent = output;
        <% 
        String first = request.getParameter("fname");
        String second = request.getParameter("secondname");
        session.setAttribute("fname",first);
        session.setAttribute("secondname",second);
        %>
    }

    </script>
<%
}catch (Exception e) {
e.printStackTrace();
}
%>
</form>
<br></br>
<form action="#">
<p>Select User 2 :
<select id="user2" onchange="getOption()">
<%
try{
String sql =("SELECT * FROM User");
resultSet = statement.executeQuery(sql);
while(resultSet.next()){
name2 = resultSet.getString("email");
%>
<option value="<%=name2 %>"><%=name2 %></option>
<%
}
%>
</select>
</p>
<%

}catch (Exception e) {
e.printStackTrace();
}
%>
</form>
 <button onclick="getfinal()"> Check option </button>
 <script>
 function getfinal() {
     <% 
      String first = request.getParameter("fname");
      String second = request.getParameter("secondname");
      %>
  window.location="ShowCommonNFTs.jsp?fname="+output+","+output2;
  }
 </script>
</body>
</html>