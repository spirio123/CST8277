package com.algonquincollege.cst8277.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algonquincollege.cst8277.ejb.StatelessEJB;

@WebServlet("/TestStatelessEJBServlet")
public class TestStatelessEJBServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected static final String TEST_STATELESS_EJB_SERVLET_ENDPOINT = "TestStatelessEJBServlet";
	
	@EJB(lookup="java:global/Servlet-EJB-Example/StatelessEJB") // similar to @Inject // naming goes as global/projectname/filename
	StatelessEJB myStatelessEJB; // doesn't have to be instantiated because it is injected
	
	// Yeah(!) for new text blocks – just like Python
	private static final String HTML_PAGE_OPENING = // first part o fthe html
			"""
			<!DOCTYPE html>
			<html>
				<head>
					<title>Servlet Stateless EJB Page</title>
				</head>
				<body>
					<h1>Servlet Stateless EJB Page</h1>
					<form action=
			""";
	
	private static final String HTML_PAGE_CLOSING = //last part of the html
			"""
					method="POST">
						Enter your name:  <input type="text" name="userInput"/> <br/>
						<input type="submit" value="Submit"/> <br/>
					</form> 
					<a href="JSPStatefulEJB.jsp">Stateful Demo</a>
				</body>
			</html>
			""";
	
	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response) // to invoke go directly to the url, if you go to the source you'll see it is produced by the doGet
			throws ServletException, IOException {

		ServletContext context = getServletContext();
		context.log( "TestStatelessEJBServlet doGet");
		
		// set response headers
		response.setContentType( "text/html");
		response.setCharacterEncoding( "UTF-8");

		PrintWriter writer = response.getWriter();
		/* No need to do this anymore, thanks to text blocks!
		writer.append( "<!DOCTYPE html>\n").append( "<html>\n").append( "  <head>\n")
			.append( "    <title>Servlet Stateless EJB Page</title>\n").append( "  </head>\n").append( "  <body>\n")
			.append( "    <h1>Servlet Stateless EJB Page</h1>\n")
			.append( "    <form action=\"" + TEST_STATELESS_EJB_SERVLET_ENDPOINT + "\" method=\"POST\">\n")
			.append( "      Enter your name:  <input type=\"text\" name=\"userInput\"/> <br/>\n")
			.append( "      <input type=\"submit\" value=\"Submit\"/> <br/>\n");
			writer.append( "    </form>\n")
			.append( "  </body>\n").append( "</html>\n");
		*/
		writer.append(HTML_PAGE_OPENING);
		writer.append("\"").append(TEST_STATELESS_EJB_SERVLET_ENDPOINT).append("\""); // appends the file name so the webpage can locate it's location
		writer.append(HTML_PAGE_CLOSING);
		writer.flush();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = getServletContext(); // just for logging
		context.log( "TestStatelessEJBServlet doPost");

		// set response headers
		response.setContentType( "text/html");
		response.setCharacterEncoding( "UTF-8");

		PrintWriter writer = response.getWriter(); // instanciated a print/writer so it can compose the response
		writer.println(myStatelessEJB.sayHello(request.getParameter("userInput"))); //this is where everything is put together
	}

}
