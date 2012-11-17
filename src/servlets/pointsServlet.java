package servlets;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dataManagers.GroupDM;
import dataManagers.PMF;
import dataManagers.UserDM;
import entity.Group;
import entity.UserEvent;
import entity.Users;

public class pointsServlet extends BaseServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException	, IOException {
		try {
			//Try creating a user here
//			Users users = new Users("test", "t", "t", "84827483");
//			UserDM.createUser(users);
		
//			UserEvent event = new UserEvent();
//			PersistenceManager pm = PMF.get().getPersistenceManager();
//			pm.makePersistent(event);
			
			// Testing creation of many to many relationship, getting the
			// objects by key and many other things
			// Get current user
//			Users user = UserDM.retrieve("t");-
			
//			List<Group> groupList = GroupDM.retrieveUserGroup(user);
						
//			user.setGroupList((ArrayList<Group>) groupList);
//			Gson gson = new GsonBuilder()
//					.excludeFieldsWithoutExposeAnnotation().create();
			//Test 1 - Retrieve object from key
//			String str = gson.toJson(user);
//			Key key = KeyFactory.stringToKey(user.getEncodedKey());
//			Users test = (Users) UserDM.retrieveUserWithKey(key);
			
			//Test 2 - create a message for the group
//			Group test1 = GroupDM.retrieve("is");
//			GroupDM.addChatMessage(test1, "test1111");
//			String a = test1.getMessages().get(0).getMessage();
//			String b = gson.toJson(test1);
			
			//Pseudo code for chat messaging
			//Every time a user create a chat message, it will send the message to the server
			//It needs to send group key
			
			//The server will record/save the message for the group, while getting the other user's deviceIds
			//And send the message to the other users through GCM
			
			
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