<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Searched NFTS page</title>
</head>
<body>
<%
session = request.getSession();

String uniqueName= request.getParameter("uniqueName");

String searchednfts = (String) session.getAttribute("searchednfts");
System.out.println("Searching: " + searchednfts);
System.out.println("On searchingNFTS.jsp");


%>
<div align = "center">
	

	<a href="activitypage.jsp"target ="_self" > back</a><br><br> 

    <div align="center">
        <table border="3" cellpadding="6">
            <caption><h2>List of NFTS</h2></caption>
            <tr>
                <th>Name</th>

            </tr>
            <c:forEach var="nfts" items="${listnfts}">
                <tr style="text-align:center">
       
                      <td><input type="hidden" name="uniqueName" value="${nfts.uniqueName}"<%session.setAttribute("nft",uniqueName);request.getAttribute("uniqueName"); ;%>>
        	<a href = "showNFT.jsp?uniqueName=${nfts.uniqueName}"target ="_self""><c:out value="${nfts.uniqueName}" /></a></td>

            </c:forEach>
        </table>
	</div>
	</div>

</body>
</html>