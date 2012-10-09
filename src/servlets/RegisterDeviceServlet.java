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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appspot.helper.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dataManagers.UserDM;

import entity.Users;

/**
 * Servlet that registers a device, whose registration id is identified by
 * {@link #PARAMETER_REG_ID}.
 *
 * <p>
 * The client app should call this servlet everytime it receives a
 * {@code com.google.android.c2dm.intent.REGISTRATION C2DM} intent without an
 * error or {@code unregistered} extra.
 */
@SuppressWarnings("serial")
public class RegisterDeviceServlet extends HttpServlet {

  private static final String PARAMETER_REG_ID = "regId";

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException {
    //update the user's reg_id (device_id)
		StringBuilder sb;
		try {
			sb = Util.parseJSON(req);
			Gson gson = new GsonBuilder().create();
			// Strongly typed object containing deviceRegId
			Users users = gson.fromJson(sb.toString(), Users.class);
			UserDM.modifyRegId(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
