package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String empid = request.getParameter("empid");
		
		String url = "jdbc:mysql://127.0.0.1:3306/servletsPractise";
		String user = "root";
		String pass = "Ablintong97!";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			
			PreparedStatement ps = con.prepareStatement("select * from employee where empid = ?");
			
			// the emp id will be in the request
			ps.setString(1, empid);
			
			out.print("<table width=50% border=1>");
			out.print ("<caption>Employee Details:</caption>");
			
			//run the sql 
			ResultSet rs = ps.executeQuery ();
			
			//print column names
			out.print ("</br></br>");
			ResultSetMetaData rsmd = rs.getMetaData ();
				// print number of the column
			int total = rsmd.getColumnCount ();
				// print the head
			out.print ("<tr>");
			
			for (int i = 1; i <= total; i++) 
			{
				out.print("<th>" + rsmd.getColumnName(i) + "</th>");
			}
			out.print ("<tr>");
			
			// print the result the row of data
			while (rs.next()) {
				out.print("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + " </td><td>" + rs.getInt(3) + "</td></tr>");
			}
			out.print ("</table>");
				
		}
		catch (Exception e2) {
			e2.printStackTrace();
		}
		finally {
			out.close();
		}
	}
}
