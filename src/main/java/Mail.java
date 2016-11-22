
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
	
	
	private static final String APIKEY = "apikey";
	private static final String TO_EMAIL = "toEmail";
	private static final String FROM_EMAIL = "fromEmail";
	private static final String SUBJECT = "subject";
	private static final String CC_EMAIL = "ccemail";
	private static final String BCC_EMAIL = "bccemail";
	
	private static final long serialVersionUID = 1L;   
	
	String parameters = "{"
            + "\""+APIKEY+"\":\"\","
            + "\""+TO_EMAIL+"\":\"\","
            + "\""+FROM_EMAIL+"\":\"\","
            + "\""+CC_EMAIL+"\":\"\","
            + "\""+BCC_EMAIL+"\":\"\","
            + "\""+SUBJECT+"\":\"\","
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
			//Get details from the config
			String apikey = credConfig.get(APIKEY).getAsString();
			String toemail = credConfig.get(TO_EMAIL).getAsString();
			String fromemail = credConfig.get(FROM_EMAIL).getAsString();
			String subject = credConfig.get(SUBJECT).getAsString();
			String[] ccemail = null,bccemail = null;		
			
			SendGrid sendgrid = new SendGrid(apikey);
			SendGrid.Email email = new SendGrid.Email();			
			email.addTo(toemail);
			email.setFrom(fromemail);			
			email.setSubject(subject);		
			email.setHtml(text);
			
			if(credConfig.has(CC_EMAIL)) {
				ccemail= new String[] {credConfig.get(CC_EMAIL).getAsString()};
				email.setCc(ccemail); 
			}
			if(credConfig.has(BCC_EMAIL)) {
				bccemail= new String[] {credConfig.get(BCC_EMAIL).getAsString()};
				email.setBcc(bccemail);
			}
						
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
