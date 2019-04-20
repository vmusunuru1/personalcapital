package personalcapital;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;									
import java.net.URLEncoder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;



public class Plans implements RequestHandler<Request, JSONObject> {
	
	public JSONObject handleRequest(Request request, Context context) {
		
		String url="https://f60u3265f6.execute-api.us-west-2.amazonaws.com/dev/plans/_search?q=";
		if(request.planName!=null){
			try {
				url+="PLAN_NAME:\""+URLEncoder.encode(request.planName, "UTF-8")+"\"";
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}						
		}
		
		if(request.sponsorName!=null){
			try {
				url+="SPONSOR_DFE_NAME:\""+URLEncoder.encode(request.sponsorName, "UTF-8")+"\"";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
				}
		if(request.sponsorState!=null){
			try {
				url+="SPONS_DFE_MAIL_US_STATE:\""+URLEncoder.encode(request.sponsorState, "UTF-8")+"\"";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		StringBuffer response = new StringBuffer(); 
		JSONObject json = null;
		try {
			URL obj= new URL(url);
			System.out.println(url);
			HttpURLConnection con= (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null){
				response.append(inputLine);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		JSONParser parser = new JSONParser();
		
		try {
			json = (JSONObject) parser.parse(response.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
		}

	
}