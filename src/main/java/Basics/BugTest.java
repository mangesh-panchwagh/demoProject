package Basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class BugTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://panchwaghmangesh177.atlassian.net";
		
		String createIssueResponse = given()
			.log()
			.all()
			.header("Content-Type","application/json")
			.header("Authorization","Basic cGFuY2h3YWdobWFuZ2VzaDE3N0BnbWFpbC5jb206QVRBVFQzeEZmR0YwWHVMbVZaQS1Scy1oYnlYa0RFVHdielc0NnQ5UjFhT0o1T0tNcGpMc0JKeVMyRlBxRXJ3QzZibV9Ob2VXcDk1TFlvVTNoNFpBeHVGcndjb1ZnMHRZQURCcFA1cVFDVzlLQXJDOWkxWE1DNlc1ZTNucFBGLTQ1ZE53clp2cTQtWTdyeUZwN2lyRW5vemZhcnphZDNsT0dkUWg3NG1zbkczSWJQZzVST2YwdWVBPTJGMTAyMEQ3")
			.body("{\r\n"
					+ "  \"fields\": {\r\n"
					+ "    \"project\": {\r\n"
					+ "      \"key\": \"SCRUM\"\r\n"
					+ "    },\r\n"
					+ "    \"summary\": \"Website Items are not working - automation Rest Assured\",\r\n"
					+ "    \"issuetype\": {\r\n"
					+ "      \"name\": \"Task\"\r\n"
					+ "    }\r\n"
					+ "  }\r\n"
					+ "}")
		.when()
			.post("rest/api/3/issue")
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(201)
			.extract().response().asString();
		
		JsonPath js = new JsonPath(createIssueResponse);
		String issueId = js.getString("id");
		System.out.println(issueId);
		
		given()
			.pathParam("key", issueId)
			.header("X-Altassian-Token","no-check")
			.header("Authorization","Basic cGFuY2h3YWdobWFuZ2VzaDE3N0BnbWFpbC5jb206QVRBVFQzeEZmR0YwWHVMbVZaQS1Scy1oYnlYa0RFVHdielc0NnQ5UjFhT0o1T0tNcGpMc0JKeVMyRlBxRXJ3QzZibV9Ob2VXcDk1TFlvVTNoNFpBeHVGcndjb1ZnMHRZQURCcFA1cVFDVzlLQXJDOWkxWE1DNlc1ZTNucFBGLTQ1ZE53clp2cTQtWTdyeUZwN2lyRW5vemZhcnphZDNsT0dkUWg3NG1zbkczSWJQZzVST2YwdWVBPTJGMTAyMEQ3")
			.multiPart("file",new File("C:/My Data/My Courses/Agile Fundamentals/Untitled.png"))
			.log()
			.all()
		.when()
			.post("/rest/api/3/issue/{key}/attachments")
		.then()
			.log()
			.all()
			.assertThat()
			.statusCode(200)
			;
			
		
		// Add attachment
		
		
	}
}
