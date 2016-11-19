package com.ibm.sample.jazzbot.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

@WebServlet("/set")
public class Set extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    protected static Map<String, JsonObject> settingMap = new HashMap<String, JsonObject>();



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String apikey = request.getParameter("apikey");
		String toEmail = request.getParameter("toEmail");
		String fromEmail = request.getParameter("fromEmail");
		String ccEmail = request.getParameter("ccEmail");
		String bccEmail = request.getParameter("bccEmail");

		String subject = request.getParameter("subject");

		
		JsonObject configCred = settingMap.get(sessionId) == null?new JsonObject():settingMap.get(sessionId);
		if(apikey != null)
			configCred.addProperty("apikey", apikey);
		if(toEmail != null)
			configCred.addProperty("toEmail", toEmail);
		if(fromEmail != null)
			configCred.addProperty("fromEmail", fromEmail);
		if(subject != null)
			configCred.addProperty("subject", subject);
		if(ccEmail != null)
			configCred.addProperty("subject", ccEmail);
		if(bccEmail != null)
			configCred.addProperty("subject", subject);


		settingMap.put(sessionId, configCred);
		String output = "SET operation successful";		
    	response.setContentType("text/html");
		PrintWriter out = response.getWriter();		
		out.println(output);		
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
