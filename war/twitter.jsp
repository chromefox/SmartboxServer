<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript">
var artist=["Coldplay","Lady Gaga"];
var num=artist.length;
var results=new Array();
function something(bla){
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	xmlhttp.open("GET","http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=Phish&api_key=b25b959554ed76058ac220b7b2e0a026",false);
	xmlhttp.send();
	xmlDoc=xmlhttp.responseXML;
	alert(xmlDoc.getElementsByTagName("image")[3].childNodes[0].nodeValue);
	var images=xmlDoc.getElementsByTagName("image");
	//alert(xmlDoc.getElementsByTagName("image")[3].childNodes[0].nodeValue);
	for(imagesNode in images){
		
		if(imagesNode.attribute=="mega"){
			alert(imagesNode.value);
		}
	} 


	/*
$(document).ready(function() {
 

$.getJSON('http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=Phish&api_key=b25b959554ed76058ac220b7b2e0a026',
	    function(data) {
	 $('#twitter').append( data.response.m );
	        
	});*/
	/*
}
$.getJSON(''http://otter.topsy.com/searchcount.json?q='+artist[bla]+'&apikey=1C05A7E357CC4EE189786B5B2C0454AC&callback=?',
        function(data) {
            $('#twitter').append(beforecounter + data.response.all + aftercounter);
    });*/
//})
}
</script>


</head>
<body onload="something(0)"><ul>
<li><a href="http://twitter.com/" id="twitter">Twitter</a></li>
</ul>

</body>
</html>