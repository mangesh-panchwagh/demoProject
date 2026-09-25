package Basics;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn,String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		String resp = given()
			.log()
			.all()
			.header("Content-type","application/json")
			.body(payload.addBook(isbn,aisle))
		.when()
			.post("/Library/Addbook.php")
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			.extract().response().asString();
			
		JsonPath js = ReusableMethods.rawToJson(resp);
		String id = js.get("ID");
		String msg = js.get("Msg");
		System.out.println(msg);
		System.out.println(id);
		//Assert.assertEquals(msg,"Book Already Exists");
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		
		// array collection of elements
		// Multidimensional array : collection of arrays
		
		return new Object[][] {
			{"ojfwtyr","93633"},{"cwettee","42533"},{"okmfett","5333"}
		};
	}
	
	
	
	
	
	
	
	
	
	
}
