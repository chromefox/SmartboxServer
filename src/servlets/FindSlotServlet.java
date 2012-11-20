package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import appspot.helper.Util;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import dataManagers.GroupDM;
import entity.Group;
import entity.UserEvent;
import entity.Users;

public class FindSlotServlet extends BaseServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<UserEvent> allEvents = new ArrayList<UserEvent>();
		DateTime beginPlusDuration = null;
		try {
			//Parse JSON
			StringBuilder sb = Util.parseJSON(request);
			JSONObject obj = new JSONObject(sb.toString());
			String groupKey = obj.getString("groupKey");
			int duration = obj.getInt("duration");
			long endDateMil = obj.getLong("endDate");
			long startDateMil = obj.getLong("startDate");
			DateTime endDate = new DateTime(new Date(endDateMil));
			DateTime beginDate = new DateTime(new Date(startDateMil));
			Group group = GroupDM.retrieve(KeyFactory.stringToKey(groupKey));
			//Comparison logic
			//Get all the group's users and their corresponding schedule
			//for every hour after beginDate, check everyone's schedule
			//according to duration length
			//if everyone is free, return the timeslot boolean and add to arrays
			
			//Handling not everyone is free
			//Get the count of people who are free in that period
			
			//Return format: number of free people, timeslot (in millis for easier convertion to date object)
			ArrayList<Users> users = group.getUserObjects();
			
			
			JSONObject respObject = new JSONObject();
			JSONArray respArray = new JSONArray();
			
			int availableCount = 0;
			DateTime eventStart;
			DateTime eventEnd;
			while(!beginDate.isEqual(endDate) && !beginDate.isAfter(endDate)) {
				availableCount = users.size();
				beginPlusDuration = beginDate.plusHours(duration);
				
				for (Users user : users) {
					for(UserEvent event : user.getUserEvents()) {
						eventStart = new DateTime(event.getStartDate());
						eventEnd = new DateTime(event.getEndDate());
						
						//As long as there is an event inside the period beginDate and beginPlusDuration, break and available
						//count --
						
						//Case 1: Event is way before the specified hour duration => return
						if(eventStart.isBefore(beginDate) && eventEnd.isBefore(beginDate)) {
							continue;
						} 
						
						//Case 2: Event is way after the specified hour duration => return
						else if(eventStart.isAfter(beginPlusDuration)) {
							continue;
						}
						
						//Case 3: Event is in between, case 1, start before, but end after start
						else if(beginDate.isAfter(eventStart) && beginDate.isBefore(eventEnd)) {
							//not available
							availableCount--;
							break; //break out of the userevent
						}
						//Case 4: Event is in between, case 2, start after meeting time
						else if(beginPlusDuration.isAfter(eventStart) && beginPlusDuration.isBefore(eventEnd)) {
							//not available
							availableCount--;
							break;
						}
					}
				}
				
				DateTimeFormatter fmt = DateTimeFormat.forPattern("E, d MMM, H:m");
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("date", fmt.print(beginDate));
				jsonObj.put("availableCount", availableCount);
				//Store the beginDate and the corresponding available count
				respArray.put(jsonObj);
				beginDate = beginDate.plusHours(1);
			}
			
			respObject.put("dates", respArray);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(respObject.toString());
		} catch (Exception e) {
			logger.severe(e.toString());
			logger.severe(e.getStackTrace().toString());
			//return error message json
			
		}
		/*
		 * String errorMsg=""; request.setAttribute("errorMsg", errorMsg);
		 * dispatcher = request.getRequestDispatcher("index.jsp");
		 * dispatcher.forward(request, response);
		 */
	}
}