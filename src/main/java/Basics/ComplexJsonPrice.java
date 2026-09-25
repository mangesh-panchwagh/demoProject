package Basics;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPrice {

	public static void main(String[] args) {

		// Using JsonPath class how do you get data from nested json
		
		JsonPath js = new JsonPath(payload.coursePrice());

		// Print number of courses returned by API

		int count = js.getInt("courses.size()");
		int count2 = js.getInt("dashboard.size()");

		System.out.println(count);

		// Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		// Title of the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);

		// Course Titles and Prices:
		for (int i = 0; i < count; i++) {
			String courseTitles = js.get("courses[" + i + "].title");
			// System.out.print(courseTitles + " : ");

			// int courseTitle = js.get("courses["+i+"].price");
			// System.out.print(js.get("courses["+i+"].price").toString());
			// System.out.println();
			System.out.println(
					js.get("courses[" + i + "].title").toString() + " : " + js.get("courses[" + i + "].price"));
		}

		// Print no of copies sold by RPA Course

		System.out.println("Print no of copies sold by RPA Course:");
		for (int i = 0; i < count; i++) {
			String courseTitle = js.get("courses[" + i + "].title");
			if (courseTitle.equalsIgnoreCase("RPA")) {
				// System.out.println("Title : " + courseTitle);
				// System.out.println("copies : " + js.get("courses["+i+"].copies").toString());
				int copies = js.get("courses[" + i + "].copies");
				System.out.println(copies);
				break;
			}
		}
		// Verify if Sum of all Course prices matches with Purchase Amount
		int OverallPrice = 0;
		for (int i = 0; i < count; i++) {

			int price =  js.get("courses["+i+"].price");
			int copies = js.get("courses["+i+"].copies");
			int totalPrice = price * copies;
			OverallPrice = OverallPrice + totalPrice;
		}
		System.out.println(OverallPrice);
		int purchaseAmount = js.get("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		if(purchaseAmount == OverallPrice) {
			System.out.println("Sum of all courses prices matches with Purchase amount...");
		}else {
			System.out.println("Sum of all courses prices does not match with Purchase amount...");
		}
	}
}
