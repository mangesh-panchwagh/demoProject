package ClientCredentialsAuth;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

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
		
		// here we are using RequestSpecBuilder
		RequestSpecification req = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.setBaseUri("https://rahulshettyacademy.com/")
				.addQueryParam("key", "qaclick123").build();
		
		// here we are using ResponseSpecBuilder
		ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		RequestSpecification res = given().spec(req)
			.body(p);
		
		// here we are using Response
		Response  response = res.when()
			.post("maps/api/place/add/json")
		.then().spec(resspec)
			.log()
			.all()
			.statusCode(200)
			.extract()
			.response();
		
		String responseString = response.asString();
		System.out.println(responseString);
	}
}
