package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	static RequestSpecification req;
	
    public RequestSpecification requestSpecification() throws IOException {
    	
    	// we added this condition for This is called Lazy Initialization.(req==null) : is called lazy initialization
    	if(req==null) {
    		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        	
     	    req = new RequestSpecBuilder().setContentType(ContentType.JSON)
     				.setBaseUri(getGlobalValue("baseUrl"))	// we are reading baseUrl from data.properties
     				.addQueryParam("key", "qaclick123")
     				.addFilter(RequestLoggingFilter.logRequestTo(log))
     				.addFilter(ResponseLoggingFilter.logResponseTo(log))
     				.setContentType(ContentType.JSON)
     				.build();
     	    
     	    return req;   
    	}
    	return req;
    }
    
    public String getGlobalValue(String key) throws IOException {
    	
    	Properties p = new Properties();
    	
    	String user_dir = System.getProperty("user.dir");
		String file_path = user_dir +  "/src/test/java/resources/global.properties";
		
		FileInputStream fis = new FileInputStream(file_path);
    	p.load(fis);
    	
	
    	return p.getProperty(key);
		
    }
    
  public String getJsonPath(Response response,String key) {
		
	  String resp = response.asString();
	  JsonPath js = response.jsonPath();
	  return js.get(key).toString();
	  
	}	
}
