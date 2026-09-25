package Basics;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {

		JsonPath js = new JsonPath(payload.coursePrice());

		int count = js.getInt("courses.size()");

		// Verify if Sum of all Course prices matches with Purchase Amount
		int OverallPrice = 0;
		for (int i = 0; i < count; i++) {

			int price = js.get("courses[" + i + "].price");
			int copies = js.get("courses[" + i + "].copies");
			int totalPrice = price * copies;
			OverallPrice = OverallPrice + totalPrice;
		}
		System.out.println(OverallPrice);
		int purchaseAmount = js.get("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		Assert.assertEquals(OverallPrice, purchaseAmount);
		
		if (purchaseAmount == OverallPrice) {
			System.out.println("Sum of all courses prices matches with Purchase amount...");
		} else {
			System.out.println("Sum of all courses prices does not match with Purchase amount...");
		}
		
	}

}
