<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="js/ajax.js" type="text/javascript"></script>
   

</head>
<body>
<div id="mainDIV" style="width:600px;font-size:30px">
	<form >
	
				<table>
					<tr><td>Name:</td>
					<td><input type="text" class="input_wrapper"  id="name" required/>
					
					</td></tr>
					<tr><td>Date of Birth:</td>
					<td><input class="input_wrapper" id="dob" type="date" min="1950-01-01" max="2011-01-01" value="1990-01-01" required  /></td>
					</tr>
					<tr>
						<td >Username:</td><td>
						<input class="input_wrapper" type="email" id="userName" required /></td>
					</tr>
					<tr>
						<td>Password:</td><td>
						<input type="password" class="input_wrapper" id="userPassword" required /></td>
					</tr>
					<tr>
						<td>Confirm Password:</td><td>
						<input class="input_wrapper" type="password" id="c_password" required /></td>
					</tr>
				<!-- 	<tr>
						<td><div id="fb-root"></div>
   
      <script>
         FB.init({ 
            appId:'237134126333932', cookie:true, 
            status:true, xfbml:true 
         });
      </script>
      <fb:login-button perms="user_about_me,friends_about_me,user_activities,friends_activities,user_checkins,user_education_history,user_events,user_groups,user_hometown,user_interests,friends_interests,user_likes,friends_likes,user_location,user_notes,user_relationships,user_relationship_details,user_religion_politics,user_status,user_work_history,read_insights,read_mailbox,read_stream,offline_access">
      
         Authorise Facebook
      </fb:login-button><	/td><td><div id="fbCheck"></div></td>
					</tr>-->					
					<tr>
						<td><a href="#" onclick="something('POST','register.do',true);" class="button big" style="background:green;border:1px solid;color:white">Create account!</a></td>
					</tr>
					
				</table>
			</form>
			</div>
</body>
</html>