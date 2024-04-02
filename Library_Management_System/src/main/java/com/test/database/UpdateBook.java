package com.test.database;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/updateBook")
public class UpdateBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id=request.getParameter("id");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String category = request.getParameter("category");
		String total_page = request.getParameter("total_page");

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book?useSSL=false","root", "root");
			String query = "update books set total_page=?, author=?, category=? , title=? where id=?";

			RequestDispatcher rd = null;

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, total_page);
			ps.setString(2, author);
			ps.setString(3, category);
			ps.setString(4, title);
			ps.setString(5, id);

			int rowCount = ps.executeUpdate();

			rd = request.getRequestDispatcher("Book_data.jsp");

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
