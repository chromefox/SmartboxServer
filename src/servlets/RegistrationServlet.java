package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dataManagers.UserDM;
import entity.Users;

public class RegistrationServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String mobileNo = request.getParameter("mobileNo");
		String password = request.getParameter("password");

		try {
			UserDM.createUser(new Users(name, password, email, mobileNo));
			HttpSession session = request.getSession(true);
			RequestDispatcher dispatcher;

			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		/*
		 * String errorMsg=""; request.setAttribute("errorMsg", errorMsg);
		 * dispatcher = request.getRequestDispatcher("index.jsp");
		 * dispatcher.forward(request, response);
		 */

	}
}