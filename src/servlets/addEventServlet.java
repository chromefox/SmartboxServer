package servlets;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appspot.helper.Util;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dataManagers.GroupDM;
import entity.Group;
import entity.UserEvent;

public class addEventServlet extends BaseServlet {
	
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StringBuilder sb = Util.parseJSON(request);
			JSONObject obj = new JSONObject(sb.toString());
			String userEventJSON = obj.getJSONObject("event").toString();
			String userKey = obj.getString("userKey");
			String groupKey = obj.getString("groupKey");
			Gson gson = new GsonBuilder().create();
			UserEvent userEvent = gson.fromJson(userEventJSON, UserEvent.class);
			//Add the event to the users in the group
			Group group = GroupDM.retrieve(KeyFactory.stringToKey(groupKey));
			group.addUserEventToAllUsers(userEvent);
			
			Queue queue = QueueFactory.getQueue("gcm");
			// Get all the group members' user devices (excluding the user)
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl("/send")
					.param(SendMessageServlet.PARAMETER_DEVICE,
							group.getUserDevicesWithoutUser(KeyFactory.stringToKey(userKey)))
					.param(SendMessageServlet.PARAMETER_EVENT_IDENTIFIER, SendMessageServlet.EVENT_CREATED_IDENTIFIER)		
					.param(SendMessageServlet.PARAMETER_MESSAGE, userEventJSON)
					.method(Method.POST);
			queue.add(taskOptions);
			
			//Send Messages to all Devices that userEvents have been added 
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
			logger.log(Level.SEVERE, e.getStackTrace().toString());
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