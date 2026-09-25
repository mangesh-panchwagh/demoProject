package ClientCredentialsAuth;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.Mobile;
import pojo.webAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

// We are performing deserialization here
public class AuthServerAndCourseDetailsPOJO {

	public static void main(String[] args) {
		
		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		// Make a call to resource owner and get whatever access_token you needed.
		// Once you are getting a token you are making a call sending it as query_parameter and logging the response
		
		
		String response = given()
			.log()
			.all()
			.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.formParam("grant_type", "client_credentials")
			.formParam("scope", "trust")
		.when()
			.log()
			.all()
			.post("oauthapi/oauth2/resourceOwner/token")
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		
		String access_token = js.getString("access_token");
		
		System.out.println("access_token : " + access_token);
		
		GetCourse gc = given()
			.log()
			.all()
			.queryParam("access_token", access_token)
		.when()
			.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
//		.then()
//			.log()
//			.all()
//			.assertThat()
//			.statusCode(401)
//			.extract().response().asString();
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		System.out.println(gc.getCourses().getApi().get(0).getCourseTitle());
		System.out.println(gc.getCourses().getWebAutomation().get(0).getCourseTitle());
	
		
//		System.out.println(gc.getCourses().getApi().size());
//		System.out.println(gc.getCourses().getWebAutomation().size());
//		System.out.println(gc.getCourses().getMobile().size());
		
		int TotalPrice = 0;
		for(int i=0;i<gc.getCourses().getWebAutomation().size();i++) {
			
			String price = gc.getCourses().getWebAutomation().get(i).getPrice();
			System.out.println(Integer.parseInt(price));
			TotalPrice = Integer.parseInt(price);
			TotalPrice += Integer.parseInt(price);
		}
		System.out.println("TotalPrice : " + TotalPrice);
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses = gc.getCourses().getApi();
		for(int i=0; i<apiCourses.size(); i++) {
			
			if(apiCourses.get(i).getCourseTitle().equals("SoapUI Webservices testing")){
				
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		// Print all the course title of course webAutomation
		System.out.println("Print all the course title of course webAutomation : " );
		
		List<webAutomation> webCourses = gc.getCourses().getWebAutomation();
		
		for(int i=0;i<webCourses.size();i++) {
			System.out.println(webCourses.get(i).getCourseTitle());
		}
		
		// Now collect all the titles and compare these two arrays
		ArrayList<String> ActualarrayList = new ArrayList<String>();
		for(int i=0;i<webCourses.size();i++) {
			ActualarrayList.add(webCourses.get(i).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitles);
		
		Assert.assertTrue(ActualarrayList.equals(expectedList));
		
		System.out.println("Print all the course title of course Mobile : " );
		List<Mobile> mobile = gc.getCourses().getMobile();
		for(int i=0;i<mobile.size();i++) {
			System.out.println(mobile.get(i).getCourseTitle());
		}
		
	}
}
