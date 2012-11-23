package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONTokener;

public class pointsServlet extends BaseServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException	, IOException {
		try {
			
			String duration=GetDistance("1.29686","103.85220","1.30495","103.83249");
			//Try creating a user here
//			Users users = new Users("test", "t", "t", "84827483");
//			UserDM.createUser(users);
		
//			UserEvent event = new UserEvent();
//			PersistenceManager pm = PMF.get().getPersistenceManager();
//			pm.makePersistent(event);
			
			// Testing creation of many to many relationship, getting the
			// objects by key and many other things
			// Get current user
			
//			for(Group group : groupList) {
//				for(ChatMessage msg : group.getMessages()) {
//						String a = msg.getMessage();
//						int b = 0;
//					}
//				}
			
//			user.setGroupList((ArrayList<Group>) groupList);
//			Gson gson = new GsonBuilder()
//					.excludeFieldsWithoutExposeAnnotation().create();
			//Test 1 - Retrieve object from key
//			String str = gson.toJson(user);
//			Key key = KeyFactory.stringToKey(user.getEncodedKey());
//			Users test = (Users) UserDM.retrieveUserWithKey(key);
			
			//Test 2 - create a message for the group
//			Group test1 = GroupDM.retrieve("test ");
//			GroupDM.addChatMessage(test1, "final test");
//			String a = test1.getMessages().get(0).getMessage();
			
//			for(ChatMessage msg2: test1.getMessages()) {
//				logger.severe(msg2.getMessage());
//			}
//			
//			String a = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(test1);
			
			//Pseudo code for chat messaging
			//Every time a user create a chat message, it will send the message to the server
			//It needs to send group key
			
			//The server will record/save the message for the group, while getting the other user's deviceIds
			//And send the message to the other users through GCM
			
			
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(
					"<message>" + duration + "</message>");
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
	
	private String GetDistance(String srcLat,String srcLong, String destLat, String destLong) {
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