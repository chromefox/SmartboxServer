package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appspot.helper.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dataManagers.GroupDM;
import dataManagers.UserDM;
import entity.Group;
import entity.Users;

public class getGroupServlet extends BaseServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StringBuilder sb = Util.parseJSON(request);
			Gson gson = new GsonBuilder().create();
			Users user = UserDM.retrieve(gson.fromJson(sb.toString(), String.class));
			
			List<Group> groupList = GroupDM.retrieveUserGroup(user);
			
			
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(HttpServletResponse.SC_OK);
//			response.getWriter().write(gson.toJson(users));
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