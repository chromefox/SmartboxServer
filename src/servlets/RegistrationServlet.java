package servlets;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import appspot.helper.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dataManagers.UserDM;
import entity.Users;

public class RegistrationServlet extends BaseServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StringBuilder sb = Util.parseJSON(request);

			Gson gson = new GsonBuilder().create();
			Users users = gson.fromJson(sb.toString(), Users.class);
			
			//Clean up the mobile number so that the stored number is standardized
			//We are only storing the 8 digit numbers without country code becoz we assume you are in Singapore
			users.setMobileNumber(Util.parseMobileNumber(users.getMobileNumber()));
			
			UserDM.createUser(users);

//			HttpSession session = request.getSession(true);
//			RequestDispatcher dispatcher;
			
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
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