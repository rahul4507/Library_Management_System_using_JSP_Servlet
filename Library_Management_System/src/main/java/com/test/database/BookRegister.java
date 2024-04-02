package com.test.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addBooks")
public class BookRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		java.lang.String title = request.getParameter("title");
		java.lang.String author = request.getParameter("author");
		java.lang.String category = request.getParameter("category");
		java.lang.String total_page = request.getParameter("total_page");

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book?useSSL=false",
					"root", "root");
			java.lang.String query = "insert into books (title, author, category, total_page) values (?, ?, ?, ?)";

			RequestDispatcher rd = null;

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, title);
			ps.setString(2, author);
			ps.setString(3, category);
			ps.setString(4, total_page);

			int rowCount = ps.executeUpdate();
			if(rowCount > 0)
			{
				System.out.println("Student added successfully..");
			}

			rd = request.getRequestDispatcher("index.jsp");

			if (rowCount > 0) {
				request.setAttribute("status", "success");
				System.out.println(request.getAttribute("status") + "!!!!!");
			} else {
				request.setAttribute("status", "failed");

			}

			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
