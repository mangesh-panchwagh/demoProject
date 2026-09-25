package Basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import files.ReusableMethods;
import files.payload;

public class test {

	public static void main(String[] args) {
		
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
		
		// POST Request : Create a new resource : here new place Id will be created
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		System.out.println("########## Check POST request create new resource #########");
		String response = given()
			.log()
			.all().
			queryParam("key", "qaclick123").
			header("Content-Type", "application/json")
			.body(payload.AddPlace())
		.when()
			.post("maps/api/place/add/json")
		.then()
			.log()
			.all()
			.assertThat().
			statusCode(200).
			body("scope", equalTo("APP"))
			.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		// Add place -> Update place with New Address -> Get Place to validate if new address is present in response
		// AddPlace, PutPlace, GetPlace
		
		
		System.out.println("Response is : " + response);
		//JsonPath js = new JsonPath(response);
		JsonPath js = ReusableMethods.rawToJson(response);
		String PlaceId = js.getString("place_id");
		
		System.out.println("Place Is : " + PlaceId);
		
		// PUT Request : Update existing resource : pass placeId
		System.out.println("########## Check PUT request update resource #########");
		String newAddress = "70 Summer walk, INDIA Updated";
		given()
			.log()
			.all()
			.queryParam("key", "qaclick123")
			.header("Content-Type","application/json")
			.body("{\r\n"
					+ "\"place_id\":\""+PlaceId+"\",\r\n"
					+ "\"address\":\""+newAddress+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}\r\n"
					+ "")
		.when()
			.put("maps/api/place/update/json")
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.body("msg", equalTo("Address successfully updated"));
			//.header("server", "Apache/2.4.52 (Ubuntu)");
//			.extract()
//			.response()
//			.asString();
		
		
		// GET Request : Get existing resource : Pass placeId
		System.out.println("########## Check GET request to see updated resource #########");
		String getPlaceResponse = given()
			.log()
			.all()
			.queryParam("key", "qaclick123")
			.queryParam("place_id", PlaceId)
		.when()
			.get("maps/api/place/get/json")
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract()
			.asString();
		
//		JsonPath js1 = new JsonPath(getPlaceResponse);
//		String actualAddress = js1.getString("address");
		
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		
		System.out.println(actualAddress);
		// Cucumber JUnit, TestNg
		assertEquals(actualAddress, newAddress);
		
		// DELETE Request : Delete resource
		System.out.println("########## Check DELETE request to DELETE resource #########");
		String deleteResponse = given()
			.log()
			.all()
			.header("Content-Type","application/json")
			.queryParam("key", "qaclick123")
			.body("{\r\n"
					+ "    \"place_id\":\""+PlaceId+"\"\r\n"
					+ "}")
		.when()
			.delete("maps/api/place/delete/json")
		.then()
			.log()
			.all()
			.statusCode(200)
			.body("status", equalTo("OK"))
			.extract()
			.asString();

		
		// GET Request : Check deleted resource exists or not
		System.out.println("########## Check GET address after address/place is being deleted #########");
		String getPlaceResponseAfterDelete = given()
				.log()
				.all()
				.queryParam("key", "qaclick123")
				.queryParam("place_id", PlaceId)
			.when()
				.get("maps/api/place/get/json")
			.then()
				.log()
				.all()
				.assertThat()
				.statusCode(404)
				.extract()
				.asString();
		
		JsonPath js2 = ReusableMethods.rawToJson(getPlaceResponseAfterDelete);
		String actualAddressAfterDelete = js2.getString("msg");
		
		String deleteMessageAfterDelete = "Get operation failed, looks like place_id  doesn't exists";
		//System.out.println("actualAddress" + actualAddress);
		// Cucumber JUnit, TestNg
		assertEquals(actualAddressAfterDelete, deleteMessageAfterDelete);
		
		// Check update address after address/place is being deleted
		// PUT Request : Check deleted resources you can not update
		
		System.out.println("########## Check UPDATE address after address/place is being deleted #########");
		String newAddressAfterPlaceDelete = "Added Address After Delete";
		
		String updatePlaceResponseAfterDelete = given()
			.log()
			.all()
			.queryParam("key", "qaclick123")
			.body("{\r\n"
					+ "\"place_id\":\""+PlaceId+"\",\r\n"
					+ "\"address\":\""+newAddressAfterPlaceDelete+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}")
		.when()
			.put("maps/api/place/update/json")
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(404)
			.extract()
			.asString();
		
		System.out.println("responseForDeletedPlace : " + updatePlaceResponseAfterDelete);
		
		JsonPath js3 = ReusableMethods.rawToJson(updatePlaceResponseAfterDelete);
		String actualAddressAfterPut = js3.getString("msg");
		System.out.println("actualAddressAfterPut : " + actualAddressAfterPut);
		
		String updateMessageAfterPUT = "Update address operation failed, looks like the data doesn't exists";
		assertEquals(actualAddressAfterPut, updateMessageAfterPUT);
		
		System.out.println("########## Check DELETE address after address/place is being deleted #########");
		
		String deletePlaceResponseAfterDelete = 
				given()
					.log()
					.all()
					.queryParam("key", "qaclick123")
					.body("{\r\n"
							+ "    \"place_id\":\""+PlaceId+"\"\r\n"
							+ "}")
				.when()
					.delete("maps/api/place/delete/json")
				.then()
					.log()
					.all()
					.assertThat()
					.statusCode(404)
					.extract()
					.asString();
				
		System.out.println("deletePlaceResponseAfterDelete : " + deletePlaceResponseAfterDelete);
		
		String expectedDeletePlaceResponseAfterDelete= "Delete operation failed, looks like the data doesn't exists";
		JsonPath js4 = ReusableMethods.rawToJson(deletePlaceResponseAfterDelete);
		String actualMessageAfterDelete = js4.getString("msg");
		System.out.println("actualMessageAfterDelete : " + actualMessageAfterDelete);
		assertEquals(actualMessageAfterDelete,expectedDeletePlaceResponseAfterDelete);
	}
}
