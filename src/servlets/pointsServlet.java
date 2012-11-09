package servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataManagers.GroupDM;
import dataManagers.PMF;
import dataManagers.UserDM;
import entity.Group;
import entity.Users;

public class pointsServlet extends BaseServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Testing creation of many to many relationship, getting the
			// objects by key and many other things
			// Get current user
			Users user = UserDM.retrieve("t");
			
			List<Group> groupList = GroupDM.retrieveUserGroup(user);
			
//			
//			user.setGroupList((ArrayList<Group>) groupList);
//			Gson gson = new GsonBuilder()
//					.excludeFieldsWithoutExposeAnnotation().create();
//			
			
			
			//Test 1 - Retrieve object from key
//			String str = gson.toJson(user);
//			Key key = KeyFactory.stringToKey(user.getEncodedKey());
//			Users test = (Users) UserDM.retrieveUserWithKey(key);
			
			//Test 2 - create a message for the group
//			Group test1 = GroupDM.retrieve("Op2");
//			GroupDM.addMessage(test1, "test");

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(
					"<message>Test success</message>");
		} catch (Exception e) {
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(
					"<message>Registeration Unsuccessful!</message>");
			logger.severe(e.toString());
		}
		/*
		 * String errorMsg=""; request.setAttribute("errorMsg", errorMsg);
		 * dispatcher = request.getRequestDispatcher("index.jsp");
		 * dispatcher.forward(request, response);
		 */
	}
}