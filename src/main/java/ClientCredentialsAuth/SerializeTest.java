package ClientCredentialsAuth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setWebsite("https://rahulshettyacademy.com/");
		p.setName("Frontline house");
		p.setPhone_number("(+91)983 893 3937");
		
		List<String> myList = new ArrayList<String>();
		myList.add("show park");
		myList.add("shop");
		p.setTypes(myList);
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		p.setLocation(l);
		
		Response res = given()
			.log()
			.all()
			.queryParam("key", "qaclick123")
			.body(p)
		.when()
			.post("maps/api/place/add/json")
		.then()
			.log()
			.all()
			.statusCode(200)
			.extract()
			.response();
		
		String responseString = res.asString();
		System.out.println(responseString);
	}
}
