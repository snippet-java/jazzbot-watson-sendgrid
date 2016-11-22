
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;

@WebServlet("/help")
public class Help extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonArray output = new JsonArray();
		
		output.add("set apikey <APIKEY> - set api key for sendgrid service");
		output.add("set toemail <EMAIL> - set destination email address");
		output.add("set fromemail <EMAIL> - set from email address");
		output.add("set subject <SUBJECT> - set subject of email");
		output.add("set bccemail <EMAIL> - set bcc email address");
		output.add("set ccemail <EMAIL> - set cc email address");
	
		
		output.add("mail <BODY_OF_EMAIL> - The email will be send with this body to the mentioned email's");
    	
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		out.println(output);
		
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
