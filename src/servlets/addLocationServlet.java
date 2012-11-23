package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appspot.helper.Util;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONTokener;

import dataManagers.GroupDM;
import dataManagers.UserDM;
import entity.Group;
import entity.Meeting;
import entity.Users;

public class addLocationServlet extends BaseServlet {
	
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StringBuilder sb = Util.parseJSON(request);
			JSONObject obj = new JSONObject(sb.toString());
			String userKey = obj.getString("userKey");
			double latitude = obj.getDouble("latitude");
			double longitude = obj.getDouble("longitude");
			Key usersKey = KeyFactory.stringToKey(userKey);
			Users user = UserDM.retrieveUserWithKey(usersKey);
			
			double destLat;
			double destLong;
			String distance;
			//update all user's groups	
			//Pseudo-code
			//get all the user's groups
			Iterator<Key> iter = user.getGroupSet().iterator();
			while(iter.hasNext()) {
				Key key = iter.next();
				Group group = GroupDM.retrieve(key);
				Meeting meeting = group.getMeeting();
				if(meeting != null) {
					destLat = meeting.getLatitude();
					destLong = meeting.getLongitude();
					//only query if all info available
					if(destLat != 0 && destLong != 0 && latitude != 0 && longitude != 0){
						distance = getDistance(latitude, longitude, destLat, destLong);	
					}else {
						distance = "N/A";
					}
					
					//Save the distance
					//Find position of userKey
					Iterator<Key> iter2 = group.getUserSet().iterator();
					int count = 0;
					int position = -1;
					while(iter2.hasNext()) {
						Key userKeyTemp = iter2.next();
						if(userKeyTemp.equals(usersKey) || userKeyTemp == usersKey) {
							position = count;
							break;
						}
						count++;
					}
					
					try {
					group.getUserDistances().set(position, distance);
					group.setMeeting(meeting);
					//persist changes in userdistance array
					GroupDM.persist(group);
					} catch(Exception e) {}
				}
			}
			
			//get the lat/long of the group's meetings
			
			//Send the request to google - aysnchronous - different thread
			
			//get the position of the user inside the arraylist
			
			
			//update the arraylist inside each group
			
			
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
	
	private String getDistance(double srcLat,double srcLong, double destLat, double destLong) {
		Calendar c = Calendar.getInstance(); 
		int seconds = (int) (c.getTimeInMillis()/1000);
	    StringBuilder urlString = new StringBuilder();
	    urlString.append("http://maps.googleapis.com/maps/api/directions/json?");
	    urlString.append("origin=");//from
	    urlString.append( srcLat);
	    urlString.append(",");
	    urlString.append(srcLong);
	    urlString.append("&destination=");//to
	    urlString.append( destLat);
	    urlString.append(",");
	    urlString.append( destLong);
	    urlString.append("&departure_time=");
	    urlString.append( Integer.toString(seconds));
	    urlString.append("&mode=transit&sensor=true");
	//    Log.d("xxx","URL="+urlString.toString());

	    // get the JSON And parse it to get the directions data.
	    HttpURLConnection urlConnection= null;
	    URL url = null;
	    try{
		    url = new URL(urlString.toString());
		    urlConnection=(HttpURLConnection)url.openConnection();
		    urlConnection.setRequestMethod("GET");
		    urlConnection.setDoOutput(true);
		    urlConnection.setDoInput(true);
		    urlConnection.connect();
	
		    InputStream inStream = urlConnection.getInputStream();
		    BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
	
		    String temp, response = "";
		    while((temp = bReader.readLine()) != null){
		        //Parse data
		        response += temp;
		    }
		    //Close the reader, stream & connection
		    bReader.close();
		    inStream.close();
		    urlConnection.disconnect();
	
		    //Sortout JSONresponse 
		    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
		    JSONArray array = object.getJSONArray("routes");
		        //Log.d("JSON","array: "+array.toString());
	
		    //Routes is a combination of objects and arrays
		    JSONObject routes = array.getJSONObject(0);
		        //Log.d("JSON","routes: "+routes.toString());
	
	
		    JSONArray legs = routes.getJSONArray("legs");
		        //Log.d("JSON","legs: "+legs.toString());
	
		    JSONObject steps = legs.getJSONObject(0);
		            //Log.d("JSON","steps: "+steps.toString());
	
		    JSONObject distance = legs.getJSONObject(0);
		        //Log.d("JSON","distance: "+distance.toString());
		    JSONObject time = distance.getJSONObject("duration");

			String sDuration = time.getString("text");
			return sDuration;
	    }catch(Exception e){
	    	
	    }
	    return null;
	}
}