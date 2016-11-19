package com.ibm.sample.jazzbot.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.sendgrid.SendGrid;

@WebServlet("/mail")
public class Mail extends HttpServlet {
	private static final long serialVersionUID = 1L;   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String emailBody = request.getParameter("text");
		JsonObject cred = Set.settingMap.get(sessionId)==null?new JsonObject():Set.settingMap.get(sessionId);		
		JsonObject output = new JsonObject();		
		try {
			
			SendGrid sendgrid = new SendGrid(cred.get("apikey").getAsString());
			SendGrid.Email email = new SendGrid.Email();
			
			email.addTo(cred.get("toEmail").getAsString());
			email.setFrom(cred.get("fromEmail").getAsString());			
			email.setSubject(cred.get("subject").getAsString());
			
			
			email.setHtml(emailBody);			
			String result = sendgrid.send(email).getMessage();			
			output.addProperty("result",result);
			
	
		} catch (Exception e) {
			output.addProperty("err", e.getMessage());
		}
		
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		out.println(output);
		
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
