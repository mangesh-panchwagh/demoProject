package stepDefinitions;

import static io.restassured.RestAssured.given;
//import static org.testng.Assert.assertEquals;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinitions extends Utils{

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	   
		//resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		// we have moved it to the next function
		
		// Calling requestSpecification method from Utils class
		res = given().log().all().spec(requestSpecification())	// we are reading url, putting logger in common method
			.body(data.addPlacePayload(name, language, address));	// getting data from addPlacePayload method from TestDataBuild class
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		 
		
		// Constructor will be called with value of resource which you pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
		{
		response = res.when()			
				.post(resourceAPI.getResource());
		 System.out.println(response);
		 
		}else if(method.equalsIgnoreCase("GET")) {
			response = res.when().get(resourceAPI.getResource());
		}
	}
	
	@Then("the API calls got success with status code {int}")
	public void the_api_calls_got_success_with_status_code(Integer int1) {
	   assertEquals(response.getStatusCode(),200);
		
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
	    
		//String resp = response.asString();)
		//js = response.jsonPath();
		
		//assertEquals(js.get(keyValue).toString(), Expectedvalue);
		assertEquals(getJsonPath(response,keyValue), Expectedvalue);
		
		System.out.println(keyValue + " " + Expectedvalue);
		
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    
		// Prepare reqspec
		// hit get API Call
		
		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		
		user_calls_with_http_request(resource,"GET");
		
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName,expectedName);
		System.out.println("actualName : " + actualName);
		System.out.println("expectedName : " + expectedName);
		
		// Constructor will be called with value of resource which you pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

	}
	
//	@Then("verify address created maps to {string} using {string}")
//	public void verifyAddress(String expectedAddress, String resource) throws IOException {
//
//	    assertEquals(getJsonPath(response, "address"), expectedAddress);
//	    System.out.println("expectedAddress : " + expectedAddress);
//	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	   
		res = given().spec(requestSpecification())
			.body(data.deletePlacePayload(place_id));
	}

}
