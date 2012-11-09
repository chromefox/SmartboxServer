package servlets;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appspot.helper.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dataManagers.GroupDM;
import dataManagers.UserDM;
import entity.Contact;
import entity.Group;
import entity.Users;

public class addGroupServlet extends BaseServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StringBuilder sb = Util.parseJSON(request);
			Gson gson = new GsonBuilder().create();
			Group group = gson.fromJson(sb.toString(), Group.class);
			//Create and persist a group with user and user's contacts
			Users user = UserDM.retrieveUser(group.getUserEmail());
			group = GroupDM.createGroup(group);
			group.addUser(user);
			for (Contact contact : group.getContacts()) {
				Users userTemp = UserDM.retrieveFromMobile(contact
						.getMobileNumber());
				group.addUser(userTemp);
			}
			
			GroupDM.persist(group);
			
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