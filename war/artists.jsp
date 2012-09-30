<%@page import = "dataManagers.*,entity.*,java.util.*"%>
<%
/*	String artistStr=_user.getArtistInfo();
	String[] artistPoints=artistStr.split(",");
	String[] artist=new String[artistPoints.length];
	Integer[] points=new Integer[artistPoints.length];
	String artist1="";
	for(int i=0;i<artistPoints.length;i++){
		if(i==artistPoints.length-1){
			artist1+=artistPoints[i].substring(0,artistPoints[i].indexOf(":"));
		}else{
			artist1+=artistPoints[i].substring(0,artistPoints[i].indexOf(":"))+",";
		}
		artist[i]=artistPoints[i].substring(0,artistPoints[i].indexOf(":"));
		points[i]=Integer.parseInt(artistPoints[i].substring(artistPoints[i].indexOf(":")+1));
	}*/
	int rows=50/5;
	int count=0;
	int count2=0;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"> 
<head> 
	<title>Home</title> 
	<link href="css/facebox.css" media="screen" rel="Stylesheet" type="text/css" />
     <link href="css/faceplant.css" media="screen" rel=Stylesheet" type="text/css" />
  
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.js"></script>
  
  <script src="js/facebox.js" type="text/javascript"></script>
 
  	<link rel="stylesheet" href="button/demo.css" type="text/css"  media="screen" /> 
	<link rel="stylesheet" type="text/css" href="css/style.css" media="screen" />
	<script type="text/javascript">
		
		var artist="Justin Bieber,Linkin Park,The Fray,Shreya Ghoshal,Bruno Mars,Owl City,The Beatles,50 Cent,Maroon 5,Rihanna,Lady Gaga,Metallica, Smelly Cat by Phoebe Buffay,Backstreet Boys,Coldplay,Taylor Swift,Bob Marley,Katy Perry,Hoobastank,AC/DC,U2,Pink Floyd,Tera Honay Laga Hoon - Ajab Prem Ki Ghazab Kahani,Poets of the Fall,John Mayer,Enrique Iglesias,Ye dooriyan by Mohit Chauhan,Pitbull,Kesha,Slash,Lil Wayne,Justin Timberlake,Green Day,Black Eyed Peas,Usher,System of a Down,Michael Jackson,Bullet for My Valentine,Bon Jovi,Avril Lavigne,Red Hot Chili Peppers,A. R. Rehman,Shakira,Nickelback,Eminem,David Guetta,A.R. Rahman,Guns N' Roses,Tiësto,AKON";

 				
	</script>
	
<script type="text/javascript" src="js/mainPage.js"></script>
<script type="text/javascript" src="js/qtobject.js"></script>
	
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