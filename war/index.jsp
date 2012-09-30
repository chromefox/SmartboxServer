<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Intelligent Analyzer</title>
<link rel="stylesheet" href="button/demo.css" type="text/css"  media="screen" /> 
      <script src="http://connect.facebook.net/en_US/all.js">
      </script>
        <link href="css/facebox.css" media="screen" rel="Stylesheet" type="text/css" />
     <link href="css/faceplant.css" media="screen" rel=Stylesheet" type="text/css" />
  <script src="js/jquery.js" type="text/javascript"></script>
  <script src="js/facebox.js" type="text/javascript"></script>
  <script type="text/javascript">
    jQuery(document).ready(function($) {
        $("td").click(function(){
$("a[rel='facebox']", this).trigger("click");
});
      $('a[rel*=facebox]').facebox({
        loading_image : 'loading.gif',
        close_image   : 'closelabel.gif'
        
      })
    })
    
    
    </script>
</head>
<body>
<div id="fb-root"></div>
	<div align="center" style="padding-top:15%">
		<div  class="div_wrapper">
			<form action="logIn.do" name="form1" method="POST">
				<table>
					<tr>
						<td style="font-size:30px">Username:</td><td>
						<input class="input_wrapper" name="username" type="email" id="username" required /></td>
					</tr>
					<tr>
						<td style="font-size:30px">Password:</td><td>
						<input type="password" class="input_wrapper" name="password" id="password" required />
						</td>
					</tr>
					
					<tr>
						<td><a href="#" onclick="document.form1.submit();" class="button big">Sign In!</a>
						
						</td>
						<td><a href="register.jsp" rel="facebox" class="button right">Register</a></td>
						<%String errorMsg = (String) request.getAttribute("errorMsg");
                        if (errorMsg != null) {%>
                                    <td colspan="2"><ul><li style=" font-size: 10px"><%=errorMsg%></li></ul></td>
                                    <%  }%>
					</tr>
					
				</table>
			</form>
		</div></div>
</body>
</html>