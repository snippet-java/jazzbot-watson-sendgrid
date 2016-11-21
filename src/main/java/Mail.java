
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sendgrid.SendGrid;

@WebServlet("/mail")
public class Mail extends HttpServlet {
	private static final long serialVersionUID = 1L;   

	private String parameters = "{"
			+ "\"apikey\":\"\","
			+ "\"toEmail\":\"\","
			+ "\"fromEmail\":\"\","
			+ "\"subject\":\"\","
			+ "\"text\":\"hello my friend\""
			+ "}";
	
	 public static void main(String[] args) {
		 Mail mail = new Mail();
		 JsonObject params = new JsonParser().parse(mail.parameters).getAsJsonObject();
		 System.out.println(mail.process(params, params.get("text").getAsString()));
	 }
	
	private String process(JsonObject credConfig, String text) {
		
		String output = "";
		try {
			String apiKey = credConfig.get("apikey").getAsString();
			String toEmail = credConfig.get("toEmail").getAsString();
			String fromEmail = credConfig.get("fromEmail").getAsString();
			String subject = credConfig.get("subject").getAsString();
			
			SendGrid sendgrid = new SendGrid(apiKey);
			SendGrid.Email email = new SendGrid.Email();
			
			email.addTo(toEmail);
			email.setFrom(fromEmail);			
			email.setSubject(subject);		
			email.setHtml(text);			
			output = sendgrid.send(email).getMessage();		
		}
		 catch (Exception e) {
			output = e.getMessage();
	     }

		return output;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String emailBody = request.getParameter("text");
		JsonObject credConfig = Set.settingMap.get(sessionId)==null?new JsonObject():Set.settingMap.get(sessionId);		
		
		String output = process(credConfig, emailBody);
		
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();		
		out.println(output);
		out.close();
	}
	
}
