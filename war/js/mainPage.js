var artistAlbumView=0;
	function selection(a){
			document.getElementById("loading").style.display="";
			document.getElementById("TopArtists1").style.textDecoration="";
			document.getElementById("TopArtists1").style.background="";
			document.getElementById("TopAlbums1").style.textDecoration="";
			document.getElementById("TopAlbums1").style.background="";
			document.getElementById("TopSongs1").style.textDecoration="";
			document.getElementById("TopSongs1").style.background="";
			document.getElementById("TopSongs1").style.textDecoration="";
			document.getElementById("TopSongs1").style.background="";
			

			
			document.getElementById(a).style.textDecoration="underline";
			document.getElementById(a).style.background="#93B300";
			if(a=="TopArtists1"){
				document.getElementById("topAlbums").style.display="none";
				document.getElementById("backArtist").style.display="none";
				document.getElementById("backArtistAlbum").style.display="none";
				document.getElementById("tableDIV").style.display="";
				document.getElementById("songDIV").style.display="none";
				artistAlbumView=0;
			}else if(a=="TopAlbums1"){
				document.getElementById("backArtist").style.display="none";
				document.getElementById("backArtistAlbum").style.display="none";
				document.getElementById("topAlbums").style.display="";
				document.getElementById("tableDIV").style.display="none";
				document.getElementById("songDIV").style.display="none";
				artistAlbumView=2;
			}else if(a=="TopSongs1"){
				document.getElementById("albumArt").style.display="none";
				document.getElementById("songs").innerHTML="";
				document.getElementById("backArtist").style.display="none";
				document.getElementById("backArtistAlbum").style.display="none";
				document.getElementById("topAlbums").style.display="none";
				document.getElementById("tableDIV").style.display="none";
				document.getElementById("artistName").style.display="none";
				document.getElementById("backArtistAlbum").style.display="none";
				document.getElementById("backArtist").style.display="none";
				document.getElementById("songDIV").style.display="";
				
				
			}
		}
		function artistInfo1(){

			selection("TopArtists1");
			if (window.XMLHttpRequest)
			  {// code for IE7+, Firefox, Chrome, Opera, Safari
			  xmlhttp=new XMLHttpRequest();
			  }
			else
			  {// code for IE6, IE5
			  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			  }
			  var num=0;
			  document.getElementById("artistName").innerHTML="";
			var artistArr=artist.split(",");
			for(i in artistArr){
				xmlhttp.open("GET","http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist="+artistArr[i]+"&autocorrect=1&api_key=63d04619e048510100c80c9655a6ad9b",false);
				xmlhttp.send();
				
				xmlDoc=xmlhttp.responseXML;
				if(xmlDoc.getElementsByTagName("lfm")[0].attributes[0].value=='ok'){
					/*var imgSrc="img/unknown.png";
					if(xmlDoc.getElementsByTagName("image")[num]!=undefined){
					//	if(xmlDoc.getElementsByTagName("image")[num].attributes[0].value=='large'){
								imgSrc=xmlDoc.getElementsByTagName("image")[2].childNodes[0].nodeValue;
								
					//		}*/
					document.getElementById("album"+num).innerHTML="<div align='center'><img src='"+xmlDoc.getElementsByTagName("image")[2].childNodes[0].nodeValue+"' width='175px' height='175px' /></div><div align='center'>"+artistArr[i]+"</div>";
					num++;
					
				}
			}
			document.getElementById("loading").style.display="none";
		}
		function changeColor(a){
			a.bgColor="#c3cddf";
			}
		function changeColorBack(a){
			a.bgColor="white";
			}
		function clickArtist(a){
			
			if(artistAlbumView==0){
				ajaxRequestPoints(a.getElementsByTagName('div')[1].childNodes[0].nodeValue,"artist");
				document.getElementById("loading").style.display="";
				document.getElementById("backArtist").style.display="";
				if (window.XMLHttpRequest)
				  {// code for IE7+, Firefox, Chrome, Opera, Safari
				  xmlhttp=new XMLHttpRequest();
				  }
				else
				{// code for IE6, IE5
				  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				var num=2;
				var nameNum=0;
				
				document.getElementById("artistName").innerHTML=a.getElementsByTagName('div')[1].childNodes[0].nodeValue;
				xmlhttp.open("GET","http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist="+a.getElementsByTagName('div')[1].childNodes[0].nodeValue+"&autocorrect=1&api_key=63d04619e048510100c80c9655a6ad9b",false);
				xmlhttp.send();
				xmlDoc=xmlhttp.responseXML;
				if(xmlDoc.getElementsByTagName("lfm")[0].attributes[0].value=='ok'){
					for(i=0;i<50;i++){
						var imgSrc="img/unknown.png";
						if(xmlDoc.getElementsByTagName("image")[num]!=undefined&&xmlDoc.getElementsByTagName("name")[nameNum]!=undefined){
								if(xmlDoc.getElementsByTagName("image")[num].attributes[0].value=='large'){
									imgSrc=xmlDoc.getElementsByTagName("image")[num].childNodes[0].nodeValue;
									num=num+4;
								}
								document.getElementById("album"+i).innerHTML="<div align='center'><img src='"+imgSrc+"' width='175px' height='175px' /></div><div align='center'>"+xmlDoc.getElementsByTagName("name")[nameNum].childNodes[0].nodeValue+"</div>";
								//num=num+4;
								nameNum=nameNum+2;
								
							}else{
								i--;
								name=name+4;
								nameNum=nameNum+2;
							}
						}
					}
				
				artistAlbumView=1;
				document.getElementById("loading").style.display="none";
			}else if(artistAlbumView==1||artistAlbumView==2){
				getAlbumSongs(a);
			}
		}
		function getAlbumSongs(a){
			document.getElementById("loading").style.display="";
			document.getElementById("songs").innherHTML="";
			document.getElementById("songDIV").style.display="";
			document.getElementById("backArtistAlbum").style.display="";
			var artistName="";
			var albumName="";
			var albumArt="";
			if(artistAlbumView==2){
				document.getElementById("topAlbums").style.display="none";
				artistName=a.getElementsByTagName('div')[1].childNodes[0].nodeValue.split(" - ")[0];
				albumName=a.getElementsByTagName('div')[1].childNodes[0].nodeValue.split(" - ")[1];
				albumArt=a.getElementsByTagName('div')[0].childNodes[0].attributes[2].value;
				document.getElementById("backArtistAlbum").innerHTML="Back to top Albums"
				
			}else{
				artistName=document.getElementById("artistName").innerHTML;
				albumName=a.getElementsByTagName('div')[1].childNodes[0].nodeValue;
				albumArt=a.getElementsByTagName('div')[0].childNodes[0].attributes[2].value;
				document.getElementById("backArtistAlbum").innerHTML="Back to "+artistName+"'s Albums"
				
			}
			ajaxRequestPoints(artistName,"album");
			document.getElementById("artistName").style.display="none";
			document.getElementById("albumArt").innerHTML="<img src='"+albumArt+"'/><font size='10'><b>"+artistName+"</u></font>";
			
			document.getElementById("tableDIV").style.display="none";
			if (window.XMLHttpRequest)
			  {// code for IE7+, Firefox, Chrome, Opera, Safari
			  xmlhttp=new XMLHttpRequest();
			  }
			else
			{// code for IE6, IE5
			  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			//document.getElementById("backArtist").style.display="";
			
			xmlhttp.open("GET","http://ws.audioscrobbler.com/2.0/?method=album.getinfo&artist="+artistName+"&album="+albumName+"&autocorrect=1&api_key=63d04619e048510100c80c9655a6ad9b",false);
			xmlhttp.send();
			xmlDoc=xmlhttp.responseXML;
			var songList="";
			if(xmlDoc.getElementsByTagName("lfm")[0].attributes[0].value=='ok'){
				var tracks=xmlDoc.getElementsByTagName("track");
				$("#songs").empty();
				var tableStr="";
				//$("#songs").append('<table id="box-table-a">');
				//document.getElementById("songs").innerHTML="<table id='songsTable'>";
				tableStr="<table id='songsTable'><tbody id='tableBody'></tbody></table>";
				$("#songs").append(tableStr);
				for(i=0;i<tracks.length;i++){	
			//		$("#songs").append('<tr id="box-table-a">'+tracks[i].childNodes[1].firstChild.nodeValue+'</tr>');
					addSong(artistName, tracks[i].childNodes[1].firstChild.nodeValue,(i+1),false)
					
					//songList+='<div id="results">'+getItunes(tracks[i].childNodes[1].firstChild.nodeValue)+"</div>";
				}
				//tableStr+="</table>";
				
			}
			document.getElementById("loading").style.display="none";
		}
		function backArtist(){
			
			clearTable();
			artistInfo1();
		}
		function clearTable(){
			var table=document.getElementById("theTable");
			for(i=0;i<10;i++){
				var tableRow=table.rows[i];
				for(j=0;j<5;j++){
					tableRow.cells[j].innerHTML="";
				}
			}
		}
		function backArtistAlbum(){
			if(artistAlbumView==2){
				getTopAlbums();
			}else{
				document.getElementById("tableDIV").style.display="";
				document.getElementById("artistName").style.display="";
				document.getElementById("songDIV").style.display="none";
				document.getElementById("backArtistAlbum").style.display="none";
			}
			
		}
		function getTopAlbums(){
			selection("TopAlbums1");
			document.getElementById("artistName").style.display="none";
			document.getElementById("songDIV").style.display="none";
			var topAlbum=new Array();
			if (window.XMLHttpRequest)
			  {// code for IE7+, Firefox, Chrome, Opera, Safari
			  xmlhttp=new XMLHttpRequest();
			  }
			else
			{// code for IE6, IE5
			  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			document.getElementById("topAlbums").style.display="";
			var topAlbumNum=4;
			for(j=0;j<artist.split(",").length;j++){
				xmlhttp.open("GET","http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist="+artist.split(",")[j]+"&autocorrect=1&api_key=63d04619e048510100c80c9655a6ad9b",false);
				xmlhttp.send();
				xmlDoc=xmlhttp.responseXML;
				if(xmlDoc.getElementsByTagName("lfm")[0].attributes[0].value=='ok'){
					if(j>29){
						topAlbumNum=1;
					}else if(j>9){
						
						topAlbumNum=2;
					}
					var num1=2;
					var nameNum=0;
					for(i=0;i<topAlbumNum;i++){
						var imgSrc="img/unknown.png";
						if(xmlDoc.getElementsByTagName("image")[num1]!=undefined){
							if(xmlDoc.getElementsByTagName("name")[nameNum]!=undefined){
								if(xmlDoc.getElementsByTagName("image")[num1].attributes[0].value=='large'){
									imgSrc=xmlDoc.getElementsByTagName("image")[num1].childNodes[0].nodeValue;
									num1=num1+4;
								}
						
						
								topAlbum.push(artist.split(",")[j]+";"+imgSrc+";"+xmlDoc.getElementsByTagName("name")[nameNum].childNodes[0].nodeValue);
								
								nameNum=nameNum+2;
							}
						}
					}
				}
			}
			var num01=0;
			var num02=1;
			var num03=2;
			var num04=3;
			var num05=4;
			var num06=5;
			var num07=6;
			var num08=7;
			var num09=8;
			var num10=9;
			for(i=0;i<topAlbum.length;i++){
				if(i<40){
					if(i<4){
						
						document.getElementById("topAlbum"+num01).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num01+=10;
					}else if(i<8){
						document.getElementById("topAlbum"+num02).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num02+=10;
					}else if(i<12){
						document.getElementById("topAlbum"+num03).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num03+=10;
					}else if(i<16){
						document.getElementById("topAlbum"+num04).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num04+=10;
					}else if(i<20){
						document.getElementById("topAlbum"+num05).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num05+=10;
					}else if(i<24){
						document.getElementById("topAlbum"+num06).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num06+=10;
					}else if(i<28){
						document.getElementById("topAlbum"+num07).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num07+=10;
					}else if(i<32){
						document.getElementById("topAlbum"+num08).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num08+=10;
					}else if(i<36){
						document.getElementById("topAlbum"+num09).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num09+=10;
					}else{
						document.getElementById("topAlbum"+num10).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
						num10+=10;
					}
				}else {
					document.getElementById("topAlbum"+i).innerHTML="<div align='center'><img src='"+topAlbum[i].split(";")[1]+"' width='175px' height='175px' /></div><div align='center'>"+topAlbum[i].split(";")[0]+" - "+topAlbum[i].split(";")[2]+"</div>";
				}
			}
			document.getElementById("loading").style.display="none";
		}
/*		function addSong(artist, song, number,check){
			var url='http://itunes.apple.com/search?callback=?';
			var query = artist + ' ' + song;
			$.getJSON(url, {term : query, entity : "musicTrack", limit: 1}, function(json) {
			$.each(json.results, function(i,value){
		    	var myQTObject = new QTObject(value.artworkUrl100, value.trackName, "100", "100");
				myQTObject.addParam("href", value.previewUrl);
				myQTObject.addParam("target", "myself");
				myQTObject.addParam("controller", "false");
				if(check){
					$("#songs").append('<tr id="box-table-a">'
							   +'<td id="box-table-a">'+number+'. </td>'
							   +'<td id="box-table-a"><img src="'+value.artworkUrl100+'" width="100" height="100" /></td>'
							   +'<td id="box-table-a">'+value.trackName+'</td>'
							   +'<td id="box-table-a">'+value.artistName+'</td>'
							   
							   //+'<OBJECT CLASSID="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" CODEBASE="http://www.apple.com/qtactivex/qtplugin.cab" WIDTH="160" HEIGHT="136" ><PARAM NAME="src" VALUE="'+value.previewUrl+'" ><PARAM NAME="autoplay" VALUE="false" ><EMBED SRC="'+value.previewUrl+'" TYPE="image/x-macpaint" PLUGINSPAGE="http://www.apple.com/quicktime/download" WIDTH="160" HEIGHT="136" AUTOPLAY="false"></EMBED></OBJECT>'				   
							   //+'<td><embed src="'+value.previewUrl+'"></embed></td>'
							   //+'<td id="box-table-a"><a href="'+value.previewUrl+'">Preview</a></td>'
							   +'<td id="box-table-a">'+ myQTObject.getHTML()
							   +'</td>'
							   +'<td id="box-table-a"><img src="button/like.png" />&nbsp;<img src="button/dislike.png" /></td>'
							   +'<td id="box-table-a"><a href="'+value.trackViewUrl+'"><img src="button/itunes.png" /></a></td>'
							   +'</tr>');
				}else{
			   $("#songs").append('<tr id="box-table-a">'
					   +'<td id="box-table-a">'+number+'. </td>'
					   +'<td id="box-table-a">'+value.trackName+'</td>'
					   //+'<OBJECT CLASSID="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" CODEBASE="http://www.apple.com/qtactivex/qtplugin.cab" WIDTH="160" HEIGHT="136" ><PARAM NAME="src" VALUE="'+value.previewUrl+'" ><PARAM NAME="autoplay" VALUE="false" ><EMBED SRC="'+value.previewUrl+'" TYPE="image/x-macpaint" PLUGINSPAGE="http://www.apple.com/quicktime/download" WIDTH="160" HEIGHT="136" AUTOPLAY="false"></EMBED></OBJECT>'				   
					   //+'<td><embed src="'+value.previewUrl+'"></embed></td>'
					   //+'<td id="box-table-a"><a href="'+value.previewUrl+'">Preview</a></td>'
					   +'<td id="box-table-a">'+ myQTObject.getHTML()
					   +'</td>'
					   +'<td id="box-table-a"><img src="button/like.png" />&nbsp;<img src="button/dislike.png" /></td>'
					   +'<td id="box-table-a"><a href="'+value.trackViewUrl+'"><img src="button/itunes.png" /></a></td>'
					   +'</tr>');
				}
			})
			}).error(function() {alert("error");});

		}*/
		function topSongs(){
			var topTenArr=new Array(110);
			var middleTwentyArr=new Array(180);
			var lastTwentyArr=new Array(100);
			var chartTwenty=new Array();
			selection("TopSongs1");
			if (window.XMLHttpRequest)
			  {// code for IE7+, Firefox, Chrome, Opera, Safari
			  xmlhttp=new XMLHttpRequest();
			  }
			else
			  {// code for IE6, IE5
			  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			  }
			var artistArr=artist.split(",");
			var iTen=0;
			var iThirty=0;
			var iFifty=0;
			for(i in artistArr){
				xmlhttp.open("GET","http://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist="+artistArr[i]+"&autocorrect=1&api_key=63d04619e048510100c80c9655a6ad9b",false);
				xmlhttp.send();
				var iTenPlus=0;
				var iThirtyPlus=0;
				var iFiftyPlus=0;
				xmlDoc=xmlhttp.responseXML;
				if(xmlDoc.getElementsByTagName("lfm")[0].attributes[0].value=='ok'){
					
					var names=xmlDoc.getElementsByTagName("name");
					var img=xmlDoc.getElementsByTagName("image");
					var imgNum=2;
					if(i<=10){
						for(j=0;j<22;j=j+2){
								var imgSrc="img/unknown.png";
								if(img[imgNum]!=undefined){
									if(img[imgNum].attributes[0].value=='large'){
										imgSrc=img[imgNum].childNodes[0].nodeValue;
									}
								}
								topTenArr.splice((iTen+(11*iTenPlus)),1,artistArr[i]+";"+names[j].childNodes[0].nodeValue+";"+imgSrc);
								iTenPlus++;
								imgNum=imgNum+4;
						}
						iTen++;
					}else if(i<=30){
						for(j=0;j<16;j=j+2){
							var imgSrc="img/unknown.png";
							if(img[imgNum]!=undefined){
								if(img[imgNum].attributes[0].value=='large'){
									imgSrc=img[imgNum].childNodes[0].nodeValue;
								}
							}
							middleTwentyArr.splice((iThirty+(20*iThirtyPlus)),1,artistArr[i]+";"+names[j].childNodes[0].nodeValue+";"+imgSrc);//img[imgNum].childNodes[0].nodeValue);
							iThirtyPlus++;
							imgNum=imgNum+4;
						}
						iThirty++;
					}else if(i<=50){
						for(j=0;j<10;j=j+2){
							if(names[j]!=undefined){
								var imgSrc="img/unknown.png";
								if(img[imgNum]!=undefined){
									if(img[imgNum].attributes[0].value=='large'){
										imgSrc=img[imgNum].childNodes[0].nodeValue;
									}
								}
								lastTwentyArr.splice((iFifty+(20*iFiftyPlus)),1,artistArr[i]+";"+names[j].childNodes[0].nodeValue+";"+imgSrc);
								iFiftyPlus++;
								imgNum=imgNum+4;
							}
						}
						iFifty++;
					}
				}
			}
			topTenArr=topTenArr.filter(function(){return true});
			middleTwentyArr=middleTwentyArr.filter(function(){return true});
			lastTwentyArr=lastTwentyArr.filter(function(){return true});
			var topTracks=topTenArr.concat(middleTwentyArr,lastTwentyArr);
			xmlhttp.open("GET","http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=63d04619e048510100c80c9655a6ad9b",false);
			xmlhttp.send();
			
			xmlDoc=xmlhttp.responseXML;
			var chartNames=xmlDoc.getElementsByTagName("name");
			var iChart=5;
			var limiter=80;
			var imgNum=2;
			var img=xmlDoc.getElementsByTagName("image");
			
			for(j=0;j<limiter;j=j+2){
				var check=true;
				var imgSrc="img/unknown.png";
				var result="";
				if(img[imgNum]!=undefined){
					if(img[imgNum].parentNode.firstChild.nextSibling.childNodes[0].nodeValue==chartNames[j].childNodes[0].nodeValue){
						imgSrc=img[imgNum].childNodes[0].nodeValue;
						imgNum=imgNum+4;
						
					}
				}
				if(chartNames[j+1]!=undefined){
					result=chartNames[j+1].childNodes[0].nodeValue+";"+chartNames[j].childNodes[0].nodeValue+";"+imgSrc;
					
					for(z=0;z<topTracks.length;z++){
						if(result==topTracks[z]){
							check=false;
							break;
						}
					}
					if(check){
						topTracks.splice(iChart,0,result);
						iChart=iChart+5;
					}else{
						limiter=limiter+2;
					}
				}
			}
			$("#songs").empty();
			var tblStr="";
			//$("#songs").append('<table id="topSngsTable">');
			tableStr='<table id="topSngsTable">';
			var len=topTracks.length/5;
			if(len%1!=0){
				len=parseInt(len)+1;
			}
			var trackCount=0;
			var numCol=5;
			for(i=0;i<len;i++){
				//$("#songs").append('<tr>');
				tableStr+='<tr>';
				if(i==(len-1)){
					numCol=topTracks.length%5;
				}
				for(j=0;j<numCol;j++){
					//$("#songs").append('<td style="cursor:pointer" onmouseover="changeColor(this)" onmouseout="changeColorBack(this)" ><a href="preview.jsp" rel="facebox"><div align="center"><img width="150px" height="150px" src="'+topTracks[trackCount].split(";")[2]+'"/><br/><b>'+(trackCount+1)+'. '+topTracks[trackCount].split(";")[1]+'</b><br/>'+topTracks[trackCount].split(";")[0]+'</div></a></td>');
					var imgSrc1=topTracks[trackCount].split(";")[2].substring(topTracks[trackCount].split(";")[2].lastIndexOf("/")+1,topTracks[trackCount].split(";")[2].lastIndexOf("."));
					tableStr+='<td style="cursor:pointer" onmouseover="changeColor(this)" onmouseout="changeColorBack(this)" ><a href="previewSong.jsp?artist='+topTracks[trackCount].split(";")[0]+'&&song='+topTracks[trackCount].split(";")[1]+'&&img='+imgSrc1+'" onclick="ajaxRequestPoints(\''+topTracks[trackCount].split(";")[0]+'\',\'song\');getItunes(\''+topTracks[trackCount].split(";")[0]+'\',\''+topTracks[trackCount].split(";")[1]+'\')"rel="facebox"><div align="center"><img width="150px" height="150px" src="'+topTracks[trackCount].split(";")[2]+'"/><br/><b>'+(trackCount+1)+'. '+topTracks[trackCount].split(";")[1]+'</b><br/>'+topTracks[trackCount].split(";")[0]+'</div></a></td>';
					trackCount++;
				}
				//$("#songs").append('</tr>');
				tableStr+='</tr>';
			}
		//	$("#songs").append('</table>');
			tableStr+='</table>';
			document.getElementById("loading").style.display="none";
			document.getElementById("songs").innerHTML=tableStr;
			something1();
			//displayTopSongs(topTracks,0,50);
		}
		
function displayTopSongs(topTracks,lower,upper){
	$("#songs").empty();
	//$("#songs").append('<table id="box-table-a">');
	for(i=lower;i<upper;i++){	
		//$("#songs").append('<tr id="box-table-a"><td id="box-table-a">'+(i+1)+'. </td><td id="box-table-a">'+topTracks[i].split(";")[1]+'</td><td id="box-table-a">'+topTracks[i].split(";")[0]+'</td></tr>');
		addSong(topTracks[i].split(";")[0], topTracks[i].split(";")[1],(i+1),true)
		//songList+='<div id="results">'+getItunes(tracks[i].childNodes[1].firstChild.nodeValue)+"</div>";
	}
	$("#songs").append('</table>');
}





//-----------------------------------


function addSong(artist, song,rank,check){
	var strVar="";
	var url='http://itunes.apple.com/search?callback=?';
	var query = artist + ' ' + song;
	var bla="";
	divSong = query;
	$.getJSON(url, {term : query, entity : "musicTrack", limit: 1}, function(json) {
	$.each(json.results, function(i,value){
		var myQTObject = new QTObject(value.previewUrl, value.trackName, "100", "15");
		myQTObject.addParam("autostart", "false");
		
		//myQTObject.write();

    	//var myQTObject = new QTObject(value.artworkUrl100, value.trackName, "100", "100");
		//myQTObject.addParam("href", value.previewUrl);
		//myQTObject.addParam("target", "myself");
		//myQTObject.addParam("controller", "false");
	  //var table=;
	  /*var rowCount= table.rows.length;
      var row = table.insertRow(rowCount);
      */
		var x = Math.round(value.trackTimeMillis / 1000);
		var seconds = x % 60;
		var minutes = Math.floor(x / 60);
		strVar='<tr id="tr-'+query+'">'
			   //+'<td id="box-table-a"><img src="'+value.artworkUrl100+'" width="100" height="100" /></td>'
	  +'<td id="box-table-a"><b>'+value.trackName+'</b></td>'
	//  +'<td id="box-table-a">'+value.artistName+'</td>'
	  +'<td id="box-table-a">'+value.primaryGenreName+'</td>'
	 // +'<td id="box-table-a">'+value.trackPrice+' $</td>'
	  +'<td id="box-table-a">'+minutes +":"+((seconds>9)?"":"0")+ seconds+' min</td>'
			   //+'<OBJECT CLASSID="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" CODEBASE="http://www.apple.com/qtactivex/qtplugin.cab" WIDTH="160" HEIGHT="136" ><PARAM NAME="src" VALUE="'+value.previewUrl+'" ><PARAM NAME="autoplay" VALUE="false" ><EMBED SRC="'+value.previewUrl+'" TYPE="image/x-macpaint" PLUGINSPAGE="http://www.apple.com/quicktime/download" WIDTH="160" HEIGHT="136" AUTOPLAY="false"></EMBED></OBJECT>'				   
			   //+'<td><embed src="'+value.previewUrl+'"></embed></td>'
			   //+'<td id="box-table-a"><a href="'+value.previewUrl+'">Preview</a></td>'
	
	//+'<td ><div onclick="activate('+StrHTML+')" >Hear Sample</div></td>'
	//+'<td id="box-table-a"><img src="button/like.png" /></td>'
	+'<td id="box-table-a"><a href="'+value.trackViewUrl+'" target="_blank" onclick="ajaxRequestPoints(\''+value.artistName+'\',\'itunes\')" class="button"><img src="button/itunes.png" />Buy song from iTunes for $'+value.trackPrice+'</a></td>'
	+'<td ><a  class="button" href="preview.jsp?sample=preview&&artist='+artist+'&&song='+song+'" onclick="ajaxRequestPoints(\''+value.artistName+'\',\'sample\')" rel="facebox">Hear Sample</a></td></tr>';
		queryYouTube(query,artist,false);
		document.getElementById("tableBody").innerHTML=document.getElementById("tableBody").innerHTML+strVar;
		
			something1();
		
	})
	
	}).error(function() {alert("error");});
	//getYouTube(query);
	
}

function queryYouTube(song,artist,check){
	var url = "http://gdata.youtube.com/feeds/api/videos?q="+song+"&max-results=1&v=2&alt=jsonc";
	var title;
	var description;
	var player;
	$.getJSON(url,
	    function(response){
	        title = response.data.items[0].title;
	        description = response.data.items[0].description;
	        player = response.data.items[0].player.default;
	        if(check){
	        	player=player.substring(player.indexOf("v=")+2);
	        	document.getElementById("youtube").innerHTML='<object width="640" height="360"><param name="movie" value="http://www.youtube.com/v/'+player+'?version=3&amp;hl=en_US"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/'+player+'?version=3&amp;hl=en_US" type="application/x-shockwave-flash" width="640" height="360" allowscriptaccess="always" allowfullscreen="true"></embed></object>';
	        }else{
		        document.getElementById("tr-"+song).innerHTML+='<td id="box-table-a"><a class="button" onclick="ajaxRequestPoints(\''+artist+'\',\'youtube\')" rel="facebox" href = "youtube.jsp?url='+player+'">Watch song on Youtube</a></td>';
		        jQuery(document).ready(function($) {
		  	      $('a[rel*=facebox]').facebox({
		  	        loading_image : 'facebox/loading.gif',
		  	        close_image   : 'facebox/closelabel.gif'
		  	      })
		  	    });
	        }
	});
}
function something1(){
	jQuery(document).ready(function($) {
	      $('a[rel*=facebox]').facebox({
	        loading_image : 'facebox/loading.gif',
	        close_image   : 'facebox/closelabel.gif'
	      })
	    });
	    
}

function ajaxRequestPoints(artist,type) {
	
	
	   
	  
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
function addSong123(artist, song){
	
	var url='http://itunes.apple.com/search?callback=?';
	var query = artist + ' ' + song;
	divSong = query;
	$.getJSON(url, {term : query, entity : "musicTrack", limit: 1}, function(json) {
	$.each(json.results, function(i,value){
		var myQTObject = new QTObject(value.previewUrl, value.trackName, "100", "15");
		myQTObject.addParam("autostart", "true");
		
	  $("#songs1").append(myQTObject.getHTML());
	  
	   
	})
	}).error(function() {alert("error");});
	//getYouTube(query);

	
}
function getItunes(artist,song){
	var url='http://itunes.apple.com/search?callback=?';
	var query = artist + ' ' + song;
	
	divSong = query;
	$.getJSON(url, {term : query, entity : "musicTrack", limit: 1}, function(json) {
	$.each(json.results, function(i,value){
		var myQTObject = new QTObject(value.previewUrl, value.trackName, "100", "15");
		myQTObject.addParam("autostart", "false");
		//document.getElementById("itunesBuy")
		$("#itunesBuy").append('<a href="'+value.trackViewUrl+'" target="_blank" onclick="ajaxRequestPoints(\''+artist+'\',\'itunes\')" class="button"><img src="button/itunes.png" />Buy song from iTunes for $'+value.trackPrice+'</a>');
		   
	  $("#songs1").append(myQTObject.getHTML());
	 
	})
	}).error(function() {alert("error");});
}

