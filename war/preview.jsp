<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String sample=request.getParameter("sample");
	String artist=request.getParameter("artist");
	String song=request.getParameter("song");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- 
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.js"></script>
  
<script type="text/javascript" src="js/qtobject.js"></script>
<script type="text/javascript">
/*
 
 var artist123="";

var song123=";

var sample123="";


function choose1(){
	if(sample=="preview"){
		addSong(artist,song);
	}
}
*/

</script>
-->
</head>
<body>

<div id="mainDIV" style="width:600px;font-size:30px">
	<div><%=song%><br/><%=artist%><br/> <input type="button" onclick="addSong123('<%=song%>','<%=artist%>')" value="Play Sample"/></div>
	<div id="songs1"></div>
</div>

</body>

</html>
