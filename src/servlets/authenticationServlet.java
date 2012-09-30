package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dataManagers.UserDM;
import entity.Users;

public class authenticationServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Users users = UserDM.retrieve(email);
		HttpSession session = request.getSession(true);
		RequestDispatcher dispatcher;

		if (users != null) {
			if (password.equals(users.getPassword())) {
				session.setAttribute("user", users);
				// Send back Users object back to the client
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(new Gson().toJson(users));
			}
		} else {
			String errorMsg = "";
			/*
			 * if(username.equals("")&&password.equals("")){
			 * errorMsg="Please enter your username and password"; }else
			 * if(username.equals("")){ errorMsg="Please enter your username"; }
			 * else if(password.equals("")){
			 * errorMsg="Please enter your password"; }else{
			 */
			errorMsg = "Invalid username or password";
			// }
			request.setAttribute("errorMsg", errorMsg);
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}

	}
}