/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appspot.helper.Util;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import dataManagers.GroupDM;
import dataManagers.UserDM;
import entity.Group;
import entity.Users;

/**
 * Servlet that adds a new message to all registered devices.
 * <p>
 * This servlet is used just by the browser (i.e., not device).
 */
@SuppressWarnings("serial")
public class SendAllMessagesServlet extends BaseServlet {
	private final int MULTICAST_SIZE = 1000;

	/**
	 * Processes the request to add a new message.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		StringBuilder sb = Util.parseJSON(req);
		try {
			JSONObject obj = new JSONObject(sb.toString());
			String groupKey = obj.getString("groupKey");
			String userKey = obj.getString("userKey");
			String msg = obj.getString("message");

			Group group = GroupDM.retrieve(KeyFactory.stringToKey(groupKey));
			Users temp = UserDM.retrieveUserWithKey(KeyFactory
					.stringToKey(userKey));

			if (temp.getDeviceRegId().isEmpty()) {
				// return error message
			} else {
				//Store message into database
				GroupDM.addChatMessage(group, msg);
				
				Queue queue = QueueFactory.getQueue("gcm");
				// send a multicast message using JSON
				// must split in chunks of 1000 devices (GCM limit) because GCM
				// can
				// only handle
				// 1000 devices per message

				//Get all the group members' user devices (including the user)
				TaskOptions taskOptions = TaskOptions.Builder
						.withUrl("/send")
						.param(SendMessageServlet.PARAMETER_DEVICE,
								group.getUserDevices()).method(Method.POST);
				queue.add(taskOptions);

				// int total = 1;
				// List<String> partialDevices = new ArrayList<String>(total);
				// int counter = 0;
				// int tasks = 0;
				// for (String device : devices) {
				// counter++;
				// partialDevices.add(device);
				// int partialSize = partialDevices.size();
				// if (partialSize == MULTICAST_SIZE || counter == total) {
				// String multicastKey =
				// Datastore.createMulticast(partialDevices);
				// logger.fine("Queuing " + partialSize +
				// " devices on multicast " +
				// multicastKey);
				//
				// TaskOptions taskOptions = TaskOptions.Builder
				// .withUrl("/send")
				// .param(SendMessageServlet.PARAMETER_MULTICAST, multicastKey)
				// .method(Method.POST);
				// queue.add(taskOptions);
				// partialDevices.clear();
				// tasks++;
				// }
				// }
				// status = "Queued tasks to send " + tasks +
				// " multicast messages to " +
				// total + " devices";
			}
		} catch (JSONException e) {
			logger.severe(e.toString());
		}
	}

}
