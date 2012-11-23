package servlets;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appspot.helper.Util;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import dataManagers.UserDM;
import entity.Contact;
import entity.UserEvent;
import entity.Users;

public class EventSyncServlet extends BaseServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//Parse JSON
			StringBuilder sb = Util.parseJSON(request);
			//Parse all the userevents
			JSONObject obj = new JSONObject(sb.toString());
			JSONArray events = obj.getJSONArray("events");
			String userKey = obj.getString("userKey");
			Users user = UserDM.retrieveUserWithKey(KeyFactory.stringToKey(userKey));
			Type collectionType = new TypeToken<ArrayList<UserEvent>>() {}.getType();
			List<UserEvent> eventList = new Gson().fromJson(events.toString(), collectionType);
			
			//delete all userEvents for the user first
			UserDM.deleteAllUserEvents(user);
			//persist all the userevent for the user
			UserDM.addUserEvent(user, eventList);
		} catch (Exception e) {
			logger.severe(e.toString());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			String errorMsg = "Calendar Syncing Failed";
			Gson responseGson = new Gson();
			JsonObject obj = new JsonObject();
			obj.addProperty("error", errorMsg);

			response.getWriter().write(responseGson.toJson(obj));
		}
		/*
		 * String errorMsg=""; request.setAttribute("errorMsg", errorMsg);
		 * dispatcher = request.getRequestDispatcher("index.jsp");
		 * dispatcher.forward(request, response);
		 */
	}
}