package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import appspot.helper.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dataManagers.UserDM;
import entity.Users;

public class RegistrationServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StringBuilder sb = Util.parseJSON(request);

			Gson gson = new GsonBuilder().create();
			Users users = gson.fromJson(sb.toString(), Users.class);
			UserDM.createUser(users);
			
			
			HttpSession session = request.getSession(true);
			RequestDispatcher dispatcher;

			response.setContentType("application/json");
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