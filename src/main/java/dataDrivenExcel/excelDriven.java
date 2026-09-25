package dataDrivenExcel;

import org.testng.annotations.Test;

import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.HashMap;

public class excelDriven {
	
	HashMap<String,Object> jsonMap = new HashMap<>();
	
	String payload = "{\r\n"
			+ "    \"name\":\"Learn Selenium Automation with Java\",\r\n"
			+ "    \"isbn\":\"seleniumJavaapi\",\r\n"
			+ "    \"aisle\":\"999\",\r\n"
			+ "    \"author\":\"John Doe\"\r\n"
			+ "}";

	@Test
	public void postDataXML() {
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		String res = given()
			.log()
			.all()
			.header("Content-Type","application/json")
			.body("{\r\n"
					+ "    \"name\":\"Learn Selenium as\",\r\n"
					+ "    \"isbn\":\"SelenniumJ\",\r\n"
					+ "    \"aisle\":\"99999999999\",\r\n"
					+ "    \"author\":\"John Doeas\"\r\n"
					+ "}")
		.when()
			.post("http://216.10.245.166/Library/Addbook.php")
		.then()
			.log()
			.all().assertThat()
			.statusCode(200).extract().response().asString();
		
		
		JsonPath js = ReusableMethods.rawToJson(res);
		String id = js.get("ID");
		System.out.println(id);
	}
}
