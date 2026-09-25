package ClientCredentialsAuth;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class AuthServerAndCourseDetails {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
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
		
		String r2 = given()
			.log()
			.all()
			.queryParam("access_token", access_token)
		.when()
			.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(401)
			.extract().response().asString();
		System.out.println(r2);
	}
}
