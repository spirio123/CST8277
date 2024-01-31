package com.algonquincollege.cst8277.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algonquincollege.cst8277.ejb.ListElementsRemote;


/**
 * Servlet implementation class TestStatefulEJBServlet
 */
@WebServlet("/TestStatefulEJBServlet")
public class TestStatefulEJBServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected static final String TEST_STATEFUL_EJB_SERVLET_ENDPOINT = "TestStatefulEJBServlet";
	
	@EJB(lookup="java:global/Servlet-EJB-Example/StatefulEJB")
	ListElementsRemote myStatefulEJB;
	
	// Yeah(!) for new text blocks – just like Python
	private static final String HTML_PAGE_OPENING = """
			<!DOCTYPE html>
			<html>
				<head>
					<title>Servlet Stateful EJB Page</title>
				</head>
				<body>
					<h1>Servlet Stateful EJB Page</h1>
					<form action=
			""";
	
	private static final String HTML_PAGE_MIDDLE = """
					method="POST">
						<input type="text" name="userInput"/> <br/>
						<input type="submit" value="Add" name="addString"/> <br/>
						<input type="submit" value="Remove" name="removeString"/> <br/>
			""";

	private static final String HTML_PAGE_CLOSING = """
					</form> 
				</body>
			</html>
			""";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		context.log( "TestStatefulEJBServlet doGet");
		
		// set response headers
		response.setContentType( "text/html");
		response.setCharacterEncoding( "UTF-8");

		PrintWriter writer = response.getWriter();
		/* No need to do this anymore, thanks to text blocks!
		writer.append( "<!DOCTYPE html>\n").append( "<html>\n").append( "  <head>\n")
			.append( "    <title>Servlet Stateful EJB Page</title>\n").append( "  </head>\n").append( "  <body>\n")
			.append( "    <h1>Servlet Stateful EJB Page</h1>\n")
			.append( "    <form action=\"" + TEST_STATEFUL_EJB_SERVLET_ENDPOINT + "\" method=\"POST\">\n")
			.append( "      <input type=\"text\" name=\"userInput\"/> <br/>\n")
			.append( "      <input type=\"submit\" value=\"Add\" name=\"addString\"/> <br/>\n")
			.append( "      <input type=\"submit\" value=\"Remove\" name=\"removeString\"/> <br/>\n");
		*/
		
		writer.append(HTML_PAGE_OPENING);
		writer.append("\"").append(TEST_STATEFUL_EJB_SERVLET_ENDPOINT).append("\"");
		writer.append(HTML_PAGE_MIDDLE);
		
		if (myStatefulEJB != null) {
			List<String> strings = myStatefulEJB.getElements();
			for (String str : strings) {
				writer.append("<br/>" + str + "\n");
			}
			writer.append("<br/> Size = " + strings.size() + "\n");
		}
		
		/*
		writer.append( "    </form>\n").append( "  </body>\n").append( "</html>\n");
		*/
		writer.append(HTML_PAGE_CLOSING);
		writer.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		context.log( "TestStatefulEJBServlet doPost");

		if (request.getParameter("addString") != null) {
			String e = request.getParameter("userInput");
			myStatefulEJB.addElement(e);
		}
		else if (request.getParameter("removeString") != null) {
			String e = request.getParameter("userInput");
			myStatefulEJB.removeElement(e);
		}

		doGet(request, response);
	}

}
