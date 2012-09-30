package servlets;
import dataManagers.*;
import entity.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class pointsServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        HttpSession session = request.getSession(true);
        
        String artist = request.getParameter("artist"); 
        String type = request.getParameter("type");
        
        Users currentUser=(Users) session.getAttribute("user");
        currentUser=UserDM.retrieve(currentUser.getUser());
        String artistInfo=currentUser.getArtistInfo();
        String[] artistarr=artistInfo.split(",");
        Double[] score=new Double[artistarr.length];
        String[] artists=new String[artistarr.length];
        
        for(int i=0;i<artistarr.length;i++){
        	artists[i]=artistarr[i].substring(0, artistarr[i].indexOf(":"));
    		score[i]=Double.parseDouble(artistarr[i].substring(artistarr[i].indexOf(":")+1));
        	if(artists[i].equals(artist)){
        		double points=0;
        		if(type.equals("artist")){
        			points=1;
        		}else if(type.equals("album")){
        			points=.05;
        		}else if(type.equals("song")){
        			points=0.5;
        		}else if(type.equals("itunes")){
        			points=5;
        		}else if(type.equals("sample")){
        			points=0.5;
        		}else if(type.equals("youtube")){
        			points=0.5;
        		}
        		
        		score[i]=score[i]+points;
        	}
        	
        }	
        double temp=0;
        String strTemp="";
        for(int i=0;i<score.length-1;i++){
           	for(int j=i+1;j<score.length;j++){
           		if(score[j]>score[i]){
           			temp=score[j];
           			score[j]=score[i];
           			score[i]=temp;
           			
           			strTemp=artists[j];
           			artists[j]=artists[i];
           			artists[i]=strTemp;
           		}
           	}
        }
        String artistStr="";
        for(int i=0;i<score.length;i++){
           	if(i==score.length-1){
           		artistStr+=artists[i]+":"+score[i];
           	}else{
           		artistStr+=artists[i]+":"+score[i]+",";
           	}
        }
        
        try{
        	currentUser.setArtistInfo(artistStr);
        	UserDM.modify(currentUser);
        }catch(Exception e){
        	response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<message>Registeration Unsuccessful!</message>"); 
            
        }
        /*   String errorMsg="";
        request.setAttribute("errorMsg", errorMsg);
        dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    */

  }
}