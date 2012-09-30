
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file = "protect.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head> 
<title>Test Page</title> 
<script src="js/ajax.js" type="text/javascript"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>

<script> 
  var movieList = new Array();
  var movieListSorted = new Array();
  var musicID = new Array();
  var publiclike = new Array();
  var artist=new Array();
  var friendLikes=new Array();
  var tweets=new Array();
  var check=false;
  var musicCount2=0;
  var friendCount = 0;
  function showMovies() {
    alert(movieList.length);
  }
  function compareMovies(movieA, movieB) {
    if (movieA.name === movieB.name) return 0;
    if (movieA.name > movieB.name) return 1;
    return -1;
  }
  function popularMovies(movieA, movieB) {
    return movieB.mCount - movieA.mCount;
  }
  function data_fetch_postproc() {
    document.getElementById('test').innerHTML 
      = "Generating recommendations ... ";
    movieList.sort(compareMovies);
    // Now we have sorted list, dedupe and count
    mCtr = 0;
    for (i = 0; i < movieList.length; i++)
    {
      var count = 0;
      movieListSorted[mCtr] = movieList[i];
      for ( j = i; j < movieList.length; j++)
      {
        if ( movieList[i].name === movieList[j].name ) {
          count++;
        } else {
          break;
        }
      }
      i = i+count-1;
      movieListSorted[mCtr++].mCount = count;
    }
    var maxResults = 100;
    if( movieListSorted.length < 100) {
      maxResults = movieListSorted.length;
    } 
    movieListSorted.sort(popularMovies);
    document.getElementById('test').innerHTML = "";
    
    
    
    for( i=0; i<maxResults; i++) {
      /*var newDiv = document.createElement("DIV");
      newDiv.id = movieListSorted[i].id;
      newDiv.innerHTML = movieListSorted[i].name + ' : Likes - ' 
        + movieListSorted[i].mCount;
*/
		artist[i]=movieListSorted[i].name;
		friendLikes[i]=movieListSorted[i].mCount;
		musicID[i]=movieListSorted[i].id;

		
//		FB.api('/'+movieListSorted[i].id, function(response) {
			/*var tempResult=document.getElementById("mainDIV");
			if(i==maxResults-1){
				tempResult.innerHTML=tempResult.innerHTML+response.likes;
			}else{
				tempResult.innerHTML=tempResult.innerHTML+response.likes+",";
			}*/
			
//			publiclike.push(response.likes);
			    
//	  });
		

			
		
		
   //   document.getElementById("movies").appendChild(newDiv);
      
     /*   if( response.link) {
          newDiv.innerHTML+= "<a href='"+response.link+"'>"
            +response.link+"</a><br/>";
            newDiv.innerHTML+=response.likes; 
          newDiv.innerHTML+= '<iframe src='
            + '"http://www.facebook.com/plugins/like.php?'
            + 'href='+response.link+'&amp;layout=standard'
            + '&amp;show_faces=true&amp;'
            + 'width=450&amp;action=like&amp;'
            + 'colorscheme=light&amp;height=80"' 
            + 'scrolling="no" frameborder="0" style="'
            + 'border:none; overflow:hidden;' 
            + 'width:450px; height:80px;"'
            + 'allowTransparency="true"></iframe><br/>';
        }
        document.getElementById(response.id).appendChild(newDiv);*/
      
   
    }
   // var a2=document.getElementById("mainDIV").innerHTML;
	//while(check=false){
//		var a2=1;
	//}
 //   setTimeout("ajaxRequestMusic('POST','addMusic.do',false,document.getElementById('user').innerHTML,artist,friendLikes,publiclike)",10000);
   // ajaxRequestMusic('POST','addMusic.do',false,document.getElementById('user').innerHTML,artist,friendLikes,publiclike) 
   	saveMusic();
  }
 	function saveMusic(){
 	 	var musicCount=musicID.length;
 	 	
 	 //	var accessToken=0;
	//	var userID=0;
 	 	 FB.getLoginStatus(function(response) {
 	 		  if (response.authResponse) {
 	 			var accessToken=response.authResponse.accessToken;
 	 		  	var userID=response.authResponse.uid;
 	 		  for(i=0;i<musicID.length;i++){
 	 	 		FB.api('/'+musicID[i], function(response) {
 	 				musicCount--;
 	 				document.getElementById('getPublicLikes').innerHTML ="Getting public likes "+musicCount+" more artists left"
 	 				publiclike.push(response.likes);
 	 				if(musicCount === 0) { 
 	 					document.getElementById('getPublicLikes').innerHTML ="Got public Likes";
 	 					
 	 					checkArtists(accessToken,userID);
 	 	 				//getNumberTweets(0,accessToken,userID);
 	 	 						};
 	 		  });
 	 	 	 	}
 	 		  }
 	 	 });
 	 /*	for(i=0;i<musicID.length;i++){
 		FB.api('/'+musicID[i], function(response) {
			musicCount--;
			publiclike.push(response.likes);
			if(musicCount === 0) { ajaxRequestMusic('POST','addMusic.do',false,userID,artist,friendLikes,publiclike,accessToken) ; };
	  });
 	 	}*/

 	}
 	function checkArtists(accessToken,userID){
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		  var num=0;
		  var runNum=artist.length;
		  document.getElementById("analyzeLikes").innerHTML="Analyzing facebook data "+runNum+" more artists left";
		
		for(i in artist){
			xmlhttp.open("GET","http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist="+artist[i]+"&autocorrect=1&api_key=63d04619e048510100c80c9655a6ad9b",false);
			xmlhttp.timeout = 4000;
			xmlhttp.timeout = function () { 
				artist.splice(i,1);
				publiclike.splice(i,1);
				friendLikes.splice(i,1);
			}
			xmlhttp.send();
			
			xmlDoc=xmlhttp.responseXML;
			if(xmlDoc!=undefined&&xmlDoc!=""){
				if(xmlDoc.getElementsByTagName("lfm")[0].attributes[0].value=='ok'){
					artist[i]=xmlDoc.getElementsByTagName("name")[0].childNodes[0].nodeValue;
				}else{
					artist.splice(i,1);
					publiclike.splice(i,1);
					friendLikes.splice(i,1);
				}
			}
			document.getElementById("analyzeLikes").innerHTML="Analyzing facebook data "+runNum+" more artists left";
			runNum--;
		}
		
		for(i=0;i<artist.length-1;i++){
			if(artist[i]=="[unknown]"){
				artist.splice(i,1);
			}else{
			for(z=i+1;z<artist.length;z++){
				if(artist[i]==artist[z]){
					artist.splice(i,1);
					publiclike.splice(i,1);
					friendLikes.splice(i,1);
					break;
				}
			}
			}
		}
		document.getElementById("analyzeLikes").innerHTML="Got all facebook data";
		
		if(artist.length>50){
			artist=artist.slice(0,50);
			publiclike=publiclike.slice(0,50);
			friendLikes=friendLikes.slice(0,50);
		}
		musicCount2=artist.length;
		getNumberTweets(0,accessToken,userID);
 	}
 	function getNumberTweets(num,accessToken,userID){
 		
			
 		
 		
			
 			 document.getElementById('getTweets').innerHTML ="Getting Tweet count "+musicCount2+" more artists left";
 			
			$(document).ready(function() {
			$.getJSON('http://otter.topsy.com/searchcount.json?q='+artist[num]+'&apikey=1C05A7E357CC4EE189786B5B2C0454AC&callback=?',
				    function(data) {
				        tweets.push(data.response.m);
				        musicCount2--;
				        if(musicCount2>0){
				        	 getNumberTweets(num+1,accessToken,userID);
						}
				        else{
							document.getElementById('getTweets').innerHTML ="Got Tweets";
				 	 	 	ajaxRequestMusic('POST','addMusic.do',false,artist,friendLikes,publiclike,tweets,accessToken,userID) ; 
				        }
				});
			
			});
			
			
		
 	}
  function get_friend_likes() {
    document.getElementById('test').innerHTML = "Requesting "
      + "data from Facebook ... ";
    FB.api('/me/friends', function(response) {
        friendCount = response.data.length;
        for( i=0; i<response.data.length; i++) {
          friendId = response.data[i].id;
          FB.api('/'+friendId+'/music', function(response) {
            movieList = movieList.concat(response.data);
            friendCount--;
            document.getElementById('test').innerHTML = friendCount 
              + " friends to go ... ";
            if(friendCount === 0) { 
            	document.getElementById('getfb').innerHTML ="Got Facebook Data";
                data_fetch_postproc(); };
          });
        } 
      });
  }
  function logIn(){
	  FB.login(function(response) {
		   if (response.authResponse) {
		     console.log('Welcome!  Fetching your information.... ');
		     FB.api('/me', function(response) {
		       console.log('Good to see you, ' + response.name + '.');
		       FB.logout(function(response) {
		         console.log('Logged out.');
		       });
		     });
		   } else {
		     console.log('User cancelled login or did not fully authorize.');
		   }
		 }, {scope: 'email'});
}
</script> 
</head> 
<body> 
<div id="fb-root"></div> 
<div id="login"></div> 
<div id="mainDIV" ></div>
<div id="getfb"></div>
<div id="getPublicLikes"></div>
<div id="analyzeLikes"></div>
<div id="getTweets"></div>
<div id="buttonTab"></div>
<div id="test"></div> 
<div id="saveNum"></div>
<div id="movies"></div> 
<div id="hiddenButton" style="visibility:hidden">
	<form  name="redirectForm" id="redirectForm" action="addMusic.do" method="GET"></form>
</div>
<script src="http://connect.facebook.net/en_US/all.js"></script> 
<script> 
FB.init({
    appId  : '237134126333932',
    status : true, // check login status
    cookie : true, // enable cookies 
    xfbml  : true,  // parse XFBML
    oauth : true
  });
FB.Event.subscribe('auth.sessionChange', function(response) {
  window.location.reload();
});
FB.getLoginStatus(function(response) {
  //if (response.authResponse) {
  if(response.authResponse){
    // logged in and connected user, someone you know
    get_friend_likes();
    document.getElementById('login').innerHTML
      ='<a href="#" onclick="logOut.do">Logout</a><br/>';
  } else {
	  FB.login(function(response) {
		   if (response.authResponse) {
		     console.log('Welcome!  Fetching your information.... ');
		     FB.api('/me', function(response) {
		       location.reload(true);
		     });
		   } else {
			   document.getElementById('login').innerHTML
			      =' <fb:login-button scope="user_about_me,friends_about_me,user_activities,friends_activities,user_interests,friends_interests,user_likes,friends_likes">'+
			       ' Authorize Facebook'+
			       '</fb:login-button>';
			    FB.XFBML.parse();
		   }
		 }, {scope: 'user_about_me,friends_about_me,user_activities,friends_activities,user_interests,friends_interests,user_likes,friends_likes'});
			

	  
  /*  document.getElementById('login').innerHTML
      =' <fb:login-button scope="user_about_me,friends_about_me,user_activities,friends_activities,user_interests,friends_interests,user_likes,friends_likes">'+
       ' Authorize Facebook'+
       '</fb:login-button>';
    FB.XFBML.parse();*/
  }//<fb:login-button perms="user_about_me,friends_about_me,user_activities,friends_activities,user_checkins,user_education_history,user_events,user_groups,user_hometown,user_interests,friends_interests,user_likes,friends_likes,user_location,user_notes,user_relationships,user_relationship_details,user_religion_politics,user_status,user_work_history,read_insights,read_mailbox,read_stream,offline_access">
});
</script> 
</body> 
</html>