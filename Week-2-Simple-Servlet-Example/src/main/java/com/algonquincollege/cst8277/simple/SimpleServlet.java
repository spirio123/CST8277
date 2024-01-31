/**
 * File: SimpleServlet.java
 * Course materials CST 8277
 * 
 * @author Mike Norman
 * 
 */
package com.algonquincollege.cst8277.simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/simpleServlet")
public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Yeah(!) for new text blocks – just like Python
	private static final String TABLE_OF_PROPERTIES_OPENING = """
		<!DOCTYPE html>
		<html lang="en">
		<head>
		    <meta charset="utf-8">
		    <title>Simple Servlet Example with a little CSS styling</title>
			<style>
		        table {
		          border: solid 1px #DDEEEE;
		          border-collapse: collapse;
		          border-spacing: 0;
		          table-layout: auto;
		          width: 95%;
		        }
		        table tr td {
		          border: solid 1px #DDEEEE;
		        }
		        table th {
		          border: solid 1px black;
		          background-color: #d8d8d8;
		        }
		    </style>
		</head>
        <body>
            <table> 
		        <tr><th>Key</th><th>Value</th><tr>
		""";
	private static final String TABLE_OF_PROPERTIES_CLOSING = """
            </table>
        </body>
        </html>
		""";
	
	@Inject
	protected ServletContext context;

	
	@Resource(lookup="java:app/jndi/example-properties")
	protected Properties properties;
	
	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context.log("doGet");
		
		// set response headers
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		//use writer to send HTML to client browser
        PrintWriter writer = response.getWriter();
        writer.append(TABLE_OF_PROPERTIES_OPENING); //sends table
		properties.forEach((key, value) -> {
			writer.append("<tr><td>" + key + "</td><td>" + properties.getProperty((String)key) + "</td></tr>\n");
		  });
        writer.append(TABLE_OF_PROPERTIES_CLOSING);
        writer.flush(); //sends the rest to the client
	}

}