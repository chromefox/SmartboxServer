package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import appspot.helper.Util;

import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import dataManagers.GroupDM;
import dataManagers.PMF;
import dataManagers.UserDM;
import entity.Group;
import entity.Users;

public class authenticationServlet extends BaseServlet {
	private Gson gson;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuilder sb = Util.parseJSON(request);
		gson = new GsonBuilder().create();
		Users temp = gson.fromJson(sb.toString(), Users.class);
		Users users = UserDM.retrieveUser(temp.getEmail().toLowerCase());
		HttpSession session = request.getSession(true);
		RequestDispatcher dispatcher;

		if (users != null) {
			if (temp.getPassword().equals(users.getPassword())) {
				// Send back Users object back to the client with Group
				// relationship
				List<Group> groupList = GroupDM.retrieveUserGroup(users);
				//setting it as attribute so that the client can reconstruct the array object.
				users.setGroupList((ArrayList<Group>) groupList);
				
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
						.create();
				
				String a = gson.toJson(users).toString();
				response.getWriter().write(gson.toJson(users));
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
			// // }
			// request.setAttribute("errorMsg", errorMsg);
			// dispatcher = request.getRequestDispatcher("index.jsp");
			// dispatcher.forward(request, response);
		}

	}
}