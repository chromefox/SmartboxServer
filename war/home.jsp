<%@page import = "dataManagers.*,entity.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file = "protect.jsp"%>
<%
	Users currentUser=UserDM.retrieve(_user.getUser());
	String artistStr=currentUser.getArtistInfo();
	String[] artistPoints=artistStr.split(",");
	String[] artist=new String[artistPoints.length];
	Double[] points=new Double[artistPoints.length];
	String artist1="";
	for(int i=0;i<artistPoints.length;i++){
		if(i==artistPoints.length-1){
			artist1+=artistPoints[i].substring(0,artistPoints[i].indexOf(":"));
		}else{
			artist1+=artistPoints[i].substring(0,artistPoints[i].indexOf(":"))+",";
		}
		artist[i]=artistPoints[i].substring(0,artistPoints[i].indexOf(":"));
		points[i]=Double.parseDouble(artistPoints[i].substring(artistPoints[i].indexOf(":")+1));
	}
	int rows=artist.length/5;
	int count=0;
	int count2=0;
	artist1=artist1.replace('"',' ');
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head> 
	<title>Home</title> 
	<link href="css/facebox.css" media="screen" rel="Stylesheet" type="text/css" />
     <link href="css/faceplant.css" media="screen" rel=Stylesheet" type="text/css" />
  
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.js"></script>
  
  <script src="js/facebox.js" type="text/javascript"></script>
 
  
	<link rel="stylesheet" type="text/css" href="css/style.css" media="screen" />
	<script type="text/javascript">
		
		var artist="<%=artist1%>"; 
	
	</script>
	
<script type="text/javascript" src="js/mainPage.js"></script>
<script type="text/javascript" src="js/qtobject.js"></script>
	<link rel="stylesheet" href="button/demo.css" type="text/css"  media="screen" /> 
	</head> 
<body onload="artistInfo1()" > 
	<div id="wrap">
	<div id="top">
                <h2><a href="home.jsp" title="Back to main page">Intelligent Analyzer</a></h2>
			<div id="menu">
                        <ul>
                            <li id="TopArtists1" onclick="artistInfo1()"><div>Top Artists</div></li>
                            <li onclick="getTopAlbums()" id="TopAlbums1"><div>Top Albums</div></li>
                            <li onclick="topSongs()" id="TopSongs1"><div>Top Songs</div></li>
                            <li id="About"><div>About</div></li>
                            <li onclick="window.location='logOut.do'"><div>Logout</div></li>
                        </ul>
                    </div>
    </div>
    <div style="text-align: center" id="loading"><img src='facebox/loading.gif' /><br/>Loading...</div>
	<div onclick="backArtist()" style="display:none;cursor:pointer" id="backArtist">Back to Artist View</div>
	</br>
	<div onclick="backArtistAlbum()" style="display:none;cursor:pointer" id="backArtistAlbum"></div>
	</br>
	<div id="artistName" style="font-weight:bold;font-size:30px;text-decoration:underline"></div>
	<div id="tableDIV" align="center">
	<table id="theTable">
		<%for(int i=0;i<rows;i++){ %>
			<tr>
				<%for(int j=0;j<5;j++){%>
					<td id="album<%=count%>" width="200px" height="200px" style="cursor:pointer" onmouseover="changeColor(this)" onmouseout="changeColorBack(this)" onclick="clickArtist(this)"></td>
				<%count++;
				} %>
			</tr>
		<%} %>
	</table>
	</div>
	<div id="topAlbums" align="center" style="display:none">
		<table id="theTable">
		<%for(int i=0;i<20;i++){ %>
			<tr>
				<%for(int j=0;j<5;j++){%>
					<td id="topAlbum<%=count2%>" width="200px" height="200px" style="cursor:pointer" onmouseover="changeColor(this)" onmouseout="changeColorBack(this)" onclick="clickArtist(this)"></td>
				<%count2++;
				} %>
			</tr>
		<%} %>
	</table>
	</div>
	<div id="songDIV">
		<div id="albumArt" style="border-bottom:3px solid"></div>
		<div id="songs"></div>
	</div>
	</div>
</body> 
</html>