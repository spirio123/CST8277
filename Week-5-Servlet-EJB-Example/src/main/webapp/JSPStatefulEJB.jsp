<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.algonquincollege.cst8277.ejb.ListElementsRemote"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="com.algonquincollege.cst8277.ejb.StatefulEJB"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>

<!-- The following is a scriplet declaration. -->
<%! 
	private static ListElementsRemote values; // interface, @Remote interface

	public void jspInit() { // can be overrideen to do whatever needed during the beginning stage
		try { // try catch for error reasons
			InitialContext ic = new InitialContext();
			values = (ListElementsRemote) ic.lookup("java:global/Servlet-EJB-Example/StatefulEJB"); //the lookup with project name and class again like with the stateless
		} // values are stateful ejb
		catch (Exception e) {
			System.out.println(e);
		}
	}
%>

<%
	if (request.getParameter("addUserInputString") != null) { // first button, stores the value typed in the list
		String e = request.getParameter("userInput"); // extract text
		values.addElement(e); // add text
	}
	else if (request.getParameter("removeUserInputString") != null) { // second button, removes the stored value that was previously typed in the list
		String e = request.getParameter("userInput");
		values.removeElement(e);
	}
	else if (request.getParameter("removeAllString") != null) { // third button, takes all the elements from th elist out
		values.remove(); // remove all
	}
%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>JSP Stateful EJB Page</title>
</head>
<body>
	<h1>JSP Stateful EJB Page</h1>
	<form name="inputForm" method="POST">
		<input type="text" name="userInput"/> <br/>
		<input type="submit" value="Add User Input" name="addUserInputString"/> <!-- these are the buttons on the page --> 
		<input type="submit" value="Remove User Input" name="removeUserInputString"/>
		<input type="submit" value="Remove All" name="removeAllString"/>
		
		<%
			if (values != null) {
				List<String> strings = values.getElements();
				for (String str : strings) {
					out.println("<br/>" + str); // print amount with a break in between
				}
				out.println("<br/> Size = " + strings.size()); // print size
			}
		%>
		
	</form>
</body>
</html>