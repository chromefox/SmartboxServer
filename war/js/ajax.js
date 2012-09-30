function something(call,url,asyn){
 //Validation
		  ajaxRequest(call,url,asyn,0,0);
	    // logged in and connected user, someone you know
	 
 }
function ajaxRequest(call,url,asyn,accessToken,uid) {
	
		
   var idField1 = document.getElementById("userName");
   var idField2 = document.getElementById("userPassword");
   var idField3 = document.getElementById("name");
   var idField4 = document.getElementById("dob");
  
   if (typeof XMLHttpRequest != "undefined") {
       req = new XMLHttpRequest();
   } else if (window.ActiveXObject) {
       req = new ActiveXObject("Microsoft.XMLHTTP");
   }
   req.open(call,url,asyn);
   req.onreadystatechange = callback;
   req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
   req.send("username=" + encodeURIComponent(idField1.value)+"&&password=" + encodeURIComponent(idField2.value)+
		   "&&name=" + encodeURIComponent(idField3.value)+
		   "&&dob=" + encodeURIComponent(idField4.value)+
		   "&&accessToken="+encodeURIComponent(accessToken)+
		   "&&uid="+encodeURIComponent(uid));
   document.getElementById("mainDIV").innerHTML="<div style='text-align: center'><img src='facebox/loading.gif' /></div>";
	
}
function ajaxRequestMusic(call,url,asyn,artist,friendLikes,publicLike,tweets,accessToken,userID) {
	

	  
	   if (typeof XMLHttpRequest != "undefined") {
	       req = new XMLHttpRequest();
	   } else if (window.ActiveXObject) {
	       req = new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   req.open(call,url,asyn);
	   req.onreadystatechange = callback1;
	   req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	   //var a5=document.getElementById("mainDIV").innerHTML.split(",");
	   req.send("artist=" + encodeURIComponent(artist)+
			   "&&friendLikes=" + encodeURIComponent(friendLikes)+
			   "&&publicLike=" + encodeURIComponent(publicLike)+
			   "&&tweets="+encodeURIComponent(tweets)+
			   "&&accessToken="+encodeURIComponent(accessToken)+
			   "&&userID="+encodeURIComponent(userID));
	   //document.getElementById("mainDIV").innerHTML="<div style='text-align: center'><img src='facebox/loading.gif' /></div>";
		
	}

function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {
        	var message = req.responseXML.getElementsByTagName("message")[0];
        	 setMessage(message.childNodes[0].nodeValue);
        }
    }
}
function setMessage(message){
	document.getElementById("mainDIV").innerHTML="<div style='text-align: center'>"+message+"</div>";
	
}
function callback1() {
    if (req.readyState == 4) {
        if (req.status == 200) {
        	var message = req.responseXML.getElementsByTagName("message")[0];
        	 setMessage1(message.childNodes[0].nodeValue);
        	 window.location ="home.jsp";
        	// document.getElementById("buttonTab").innerHTML="<a href='home.jsp'>Click to Proceed</a>";
        }
    }
}
function setMessage1(message){
	if(message=="saved"){
		document.forms["redirectForm"].submit();

	}
	
}
