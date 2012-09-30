package servlets;
import dataManagers.*;
import entity.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class userMusicServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
       // response.setContentType("text/html");
        HttpSession session = request.getSession(true);
        String temptweets[] = request.getParameterValues("tweets");
        String tempartist[] = request.getParameterValues("artist");
        String tempfriendLikes[] = request.getParameterValues("friendLikes");
        String temppublicLikes[] = request.getParameterValues("publicLike");
        
        String accessToken=request.getParameter("accessToken");
        String userID=request.getParameter("userID");
        
        String artist[]=tempartist[0].split(",");
        String friendLikes[]=tempfriendLikes[0].split(",");
        String publicLikes[]=temppublicLikes[0].split(",");
        String tweets[]=temptweets[0].split(",");
        Double[] score=new Double[artist.length];
        
        double friendLikeSum=0;
        double publicLikeSum=0;
        double tweetSum=0;
        
        for(int i=0;i<artist.length;i++){
        	friendLikeSum+=Integer.parseInt(friendLikes[i]);
        	publicLikeSum+=Integer.parseInt(publicLikes[i]);
        	tweetSum+=Integer.parseInt(tweets[i]);        	
        }
        for(int i=0;i<artist.length;i++){
        	score[i]=((Integer.parseInt(friendLikes[i])/friendLikeSum)*7000)+((Integer.parseInt(publicLikes[i])/publicLikeSum)*2000)+((Integer.parseInt(tweets[i])/tweetSum)*1000);
        }
        double temp=0;
        String strTemp="";
        for(int i=0;i<score.length-1;i++){
        	for(int j=i+1;j<score.length;j++){
        		if(score[j]>score[i]){
        			temp=score[j];
        			score[j]=score[i];
        			score[i]=temp;
        			
        			strTemp=artist[j];
        			artist[j]=artist[i];
        			artist[i]=strTemp;
        		}
        	}
        }
        String artistStr="";
        for(int i=0;i<score.length;i++){
        	if(i==score.length-1){
        		artistStr+=artist[i]+":"+score[i];
        	}else{
        		artistStr+=artist[i]+":"+score[i]+",";
        	}
        }
        Users currentUser=(Users) session.getAttribute("user");
        
        RequestDispatcher dispatcher;
       try{
    	  currentUser.setArtistInfo(artistStr);
    	  currentUser.setPriv(1);
    	  currentUser.setAccessToken(accessToken);
    	  currentUser.setUid(userID);
    	  UserDM.modify(currentUser);
        
    
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<message>"+artist[0]+"-"+tweets[0]+"</message>"); 
       
       // response.sendRedirect("home.jsp");
        }catch(Exception e){
        	response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<message>not-saved</message>"); 
           
            
        }
        /*   String errorMsg="";
        request.setAttribute("errorMsg", errorMsg);
        dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    */

  }
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
    	HttpSession session = request.getSession(true);
    	RequestDispatcher dispatcher;
    	Users user = (Users) session.getAttribute("user");
    	if(user.getPriv()==1){
    		response.sendRedirect("home.jsp");
    	}
    }
}