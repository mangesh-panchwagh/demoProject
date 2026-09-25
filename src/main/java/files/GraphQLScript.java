package files;

import static io.restassured.RestAssured.*;

import org.junit.Assert;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class GraphQLScript {
	
	public static void main(String[] args) {
		
		String response = given().log().all().contentType(ContentType.JSON)
		.body("{\r\n"
				+ "  \"query\": \"query($characterId: Int!, $episodeId: Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    type\\n    id\\n  }\\n  location(locationId: 17) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: \\\"Rahul\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: \\\"hullu\\\"}) {\\n    result {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\",\r\n"
				+ "  \"variables\": {\r\n"
				+ "    \"characterId\": 14,\r\n"
				+ "    \"episodeId\": 11\r\n"
				+ "  }\r\n"
				+ "}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().log().all().extract().response().asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		
		if(js.get("data.character") == null) {
		    System.out.println("Character not found");
		} else {
		    String characterName = js.getString("data.character.name");
		    Assert.assertEquals("Robin", characterName);
		}
		
		//String characterName = js.getString("data.character.name");
		//Assert.assertEquals(characterName, "Robin");
	}
}
