
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
	private static final String APIKEY = "apikey";
	private static final String TO_EMAIL = "toemail";
	private static final String FROM_EMAIL = "fromemail";
	private static final String CC_EMAIL = "ccemail";
	private static final String BCC_EMAIL = "bccemail";
	private static final String SUBJECT = "subject";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionId = request.getParameter("sessionId");
		String apikey = request.getParameter(APIKEY);
		String toemail = request.getParameter(TO_EMAIL);
		String fromemail = request.getParameter(FROM_EMAIL);
		String ccemail = request.getParameter(CC_EMAIL);
		String bccemail = request.getParameter(BCC_EMAIL);

		String subject = request.getParameter(SUBJECT);

		
		JsonObject configCred = settingMap.get(sessionId) == null?new JsonObject():settingMap.get(sessionId);
		if(apikey != null)
			configCred.addProperty(APIKEY, apikey);
		if(toemail != null)
			configCred.addProperty(TO_EMAIL, toemail);
		if(fromemail != null)
			configCred.addProperty(FROM_EMAIL, fromemail);
		if(subject != null)
			configCred.addProperty(SUBJECT, subject);
		if(ccemail != null)
			configCred.addProperty(CC_EMAIL, ccemail);
		if(bccemail != null)
			configCred.addProperty(BCC_EMAIL, subject);


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
