package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import appspot.helper.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;

import dataManagers.UserDM;
import entity.Users;

public class authenticationServlet extends HttpServlet {
	 protected final Logger logger = Logger.getLogger(getClass().getName());
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuilder sb = Util.parseJSON(request);

		Gson gson = new GsonBuilder().create();
		Users temp = gson.fromJson(sb.toString(), Users.class);
		Users users = UserDM.retrieve(temp.getEmail().toLowerCase());
		HttpSession session = request.getSession(true);
		RequestDispatcher dispatcher;

		if (users != null) {
			if (temp.getPassword().equals(users.getPassword())) {
				// Send back Users object back to the client
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(new Gson().toJson(users));
			}
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			String errorMsg = "Invalid username or password";
			Gson responseGson = new Gson();
			JsonObject obj = new JsonObject();
			obj.addProperty("error", errorMsg);
			
			response.getWriter().write(responseGson.toJson(obj));
			
			/*
			 * if(username.equals("")&&password.equals("")){
			 * errorMsg="Please enter your username and password"; }else
			 * if(username.equals("")){ errorMsg="Please enter your username"; }
			 * else if(password.equals("")){
			 * errorMsg="Please enter your password"; }else{
			 */
//			// }
//			request.setAttribute("errorMsg", errorMsg);
//			dispatcher = request.getRequestDispatcher("index.jsp");
//			dispatcher.forward(request, response);
		}

	}
}