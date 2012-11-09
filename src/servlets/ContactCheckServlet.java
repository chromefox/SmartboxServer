package servlets;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import appspot.helper.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import dataManagers.UserDM;

import entity.Contact;
import entity.Users;

public class ContactCheckServlet extends BaseServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Contact> availableContactList = new ArrayList<Contact>();
			StringBuilder sb = Util.parseJSON(request);

			Gson gson = new GsonBuilder().create();

			Type collectionType = new TypeToken<ArrayList<Contact>>() {}.getType();
			List<Contact> deviceContactList = gson.fromJson(sb.toString(), collectionType);
			List<Users> users = UserDM.retrieveAll();
			// Do the mobile number checking on the User database

			// TODO some basic checking on the international code, for now
			// assume it's only 8 digits - Singapore Number
			for (Contact contact : deviceContactList) {
				//loop through user to check existence
				for(Users user : users) {
					String userMobile = Util.parseMobileNumber(user.getMobileNumber());
					String contactMobile = Util.parseMobileNumber(contact.getMobileNumber());
					if(userMobile.equals(contactMobile)) {
						availableContactList.add(contact);
						break;
					}
				}
			}
			
			//Return the availableContactList json
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(HttpServletResponse.SC_OK);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new Gson().toJson(availableContactList));
		} catch (Exception e) {
			String errorMsg = "Server Error";
			Gson responseGson = new Gson();
			JsonObject obj = new JsonObject();
			obj.addProperty("error", errorMsg);
			
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(responseGson.toJson(obj));
			
			logger.severe(e.toString());
		}
	}
}