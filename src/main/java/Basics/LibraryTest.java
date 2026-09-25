package Basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import files.ReusableMethods;

public class LibraryTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		System.out.println("########## Check POST request create new book as a resource #########");
		
		String createBookResponse = given()
			.log()
			.all()
			.header("Content-Type", "application/json")
			.header("Connection", "close")
			.body("{\r\n"
					+ "    \"name\":\"Mangeshbook\",\r\n"
					+ "    \"isbn\":\"AAA\",\r\n"
					+ "    \"aisle\":\"99999\",\r\n"
					+ "    \"author\":\"John Doe\"\r\n"
					+ "}")
		.when()
			.post("Library/Addbook.php")
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(createBookResponse);
		String bookId = js.getString("ID");
		String msg = js.getString("Msg");
		
		System.out.println("bookId : " + bookId);
		System.out.println("Msg : " + msg);
		
		System.out.println("########## Check GET request to see created resource #########");
		String getLibraryResponse = 
				given()
					.log()
					.all()
					.header("Content-Type", "application/json")
					.queryParam("ID", "AAA99999")
				.when()
					.get("Library/GetBook.php")
				.then()
					.log()
					.all()
					.assertThat()
					.statusCode(200)
					.extract().asString();
		
		System.out.println(getLibraryResponse);
		JsonPath js1 = ReusableMethods.rawToJson(getLibraryResponse);
		String bookID = js1.getString("ID");
		String expectedBookID = "AAA99999";
		
		System.out.println(bookID);
		// Cucumber JUnit, TestNg
		assertEquals(bookID, expectedBookID);
		
	
		
					
	}
}
