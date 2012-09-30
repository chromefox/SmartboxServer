<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String img=request.getParameter("img");
	String artist=request.getParameter("artist");
	String song=request.getParameter("song");
	img="http://userserve-ak.last.fm/serve/126/"+img+".png";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- 
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.js"></script>
<script type="text/javascript" src="js/qtobject.js"></script>
-->
<link rel="stylesheet" href="button/demo.css" type="text/css"  media="screen" /> 
<!-- 
<script type="text/javascript">
var artist11="";

var song11="";

var img11="";

/*
function choose1(){
	if(sample=="preview"){
		addSong(artist,song);
	}
}*/

function queryYouTube11(song){
	ajaxRequestPoints(artist11,"youtube");
	var url = "http://gdata.youtube.com/feeds/api/videos?q="+song+"&max-results=1&v=2&alt=jsonc";
	var title;
	var description;
	var player;
	$.getJSON(url,
	    function(response){
	        title = response.data.items[0].title;
	        description = response.data.items[0].description;
	        player = response.data.items[0].player.default;
	        player=player.substring(player.indexOf("v=")+2);
	        document.getElementById("youtube").innerHTML='<object width="640" height="360"><param name="movie" value="http://www.youtube.com/v/'+player+'?version=3&amp;hl=en_US"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/'+player+'?version=3&amp;hl=en_US" type="application/x-shockwave-flash" width="640" height="360" allowscriptaccess="always" allowfullscreen="true"></embed></object>';
	});
}
/*
function addSong11(artist, song){
	alert("HA");
	ajaxRequestPoints11(artist11,"sample");
	var url='http://itunes.apple.com/search?callback=?';
	var query = artist + ' ' + song;
	divSong = query;
	$.getJSON(url, {term : query, entity : "musicTrack", limit: 1}, function(json) {
	$.each(json.results, function(i,value){
		var myQTObject = new QTObject(value.previewUrl, value.trackName, "100", "15");
		myQTObject.addParam("autostart", "false");
		//document.getElementById("itunesBuy")
		$("#itunesBuy").append('<a href="'+value.trackViewUrl+'" onclick="ajaxRequestPoints11(\''+artist11+'\',\'itunes\')" class="button"><img src="button/itunes.png" />Buy song from iTunes for $'+value.trackPrice+'</a>');
		   
	  $("#songs1").append(myQTObject.getHTML());
	 
	})
	}).error(function() {alert("error");});
	//getYouTube(query);

	
}*/
function ajaxRequestPoints11(artist,type) {
	
	
	   
	  
	   if (typeof XMLHttpRequest != "undefined") {
	       req = new XMLHttpRequest();
	   } else if (window.ActiveXObject) {
	       req = new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   req.open('POST','addPoints.do',true);
	//   req.onreadystatechange = callback;
	   req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	   req.send("artist=" + encodeURIComponent(artist)+"&&type=" + encodeURIComponent(type));
	  
		
	}
</script>
-->
</head>
<body>

<div id="mainDIV" style="width:640px;font-size:30px">
	<div></div>
	<div style="border-bottom:3px solid">
	<table><tr><td rowspan="2"><img src="<%=img%>"></td><td><font size="10"><b><%=song%></b></font><br style="clear:both"/><div style="float:left"><font size="3" ><%=artist%></font></div><div style="float:left" id="songs1"></div></td></tr></table></div>
<!-- 	<div style="float:left" id="songs1"> <a style="cursor:pointer" class="button big" onclick="addSong11('<%=song%>','<%=artist%>')">Play Sample</a></div>
	--><div style="float:left" id="itunesBuy"></div>
	<div  id="youtube"><a style="cursor:pointer" class="button big" onclick="queryYouTube('<%=artist%> <%=song%>','<%=song%>',true)" >Watch on Youtube!</a></div>
	
	<!-- <input type="button" class="button big" onclick="queryYouTube11('<%=artist%> <%=song%>')" value="Watch on youtube"/>
	<input type="button" class="button big" onclick="addSong('<%=song%>','<%=artist%>')" value="Play Sample"/> -->
</div><!-- 
<script>
	
	//ajaxRequestPoints11(artist11,"sample");
	var url='http://itunes.apple.com/search?callback=?';
	var query = <%=artist%> + ' ' + <%=song%>;
	
	divSong = query;
	$.getJSON(url, {term : query, entity : "musicTrack", limit: 1}, function(json) {
	$.each(json.results, function(i,value){
		var myQTObject = new QTObject(value.previewUrl, value.trackName, "100", "15");
		myQTObject.addParam("autostart", "false");
		//document.getElementById("itunesBuy")
		$("#itunesBuy").append('<a href="'+value.trackViewUrl+'" target="_blank" onclick="ajaxRequestPoints(\''+artist11+'\',\'itunes\')" class="button"><img src="button/itunes.png" />Buy song from iTunes for $'+value.trackPrice+'</a>');
		   
	  $("#songs1").append(myQTObject.getHTML());
	 
	})
	}).error(function() {alert("error");});

</script>-->
</body>

</html>
