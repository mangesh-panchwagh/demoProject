package ClientCredentialsAuth;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class EcommerceAPITest {

	public static void main(String[] args) {
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
		.setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest = new LoginRequest();
		
		loginRequest.setUserEmail("mangesh013@gmail.com");
		loginRequest.setUserPassword("Mangesh@123");
		
		RequestSpecification recLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginRequest);
		
		LoginResponse loginResponse = recLogin.when().post("/api/ecom/auth/login")
					.then().log().all().extract().response().as(LoginResponse.class);
		
		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		
		System.out.println(loginResponse.getToken());
		System.out.println(loginResponse.getUserId());
		
		// Add Product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addHeader("Authorization", token)
				.build();
		
		File file = new File("C://Users//10851440//OneDrive - LTIMindtree//Documents//LaptopImage.png");
		
		System.out.println("Does file exists : " + file.exists());
		
		// here we have added form parameters 
		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
				.param("productName", "laptop")
				.param("productAddedBy", userId)
				.param("productCategory", "fashion")
				.param("productSubCategory", "Shirts")
				.param("productPrice", "11500")
				.param("productDescription", "Lenova")
				.param("productFor", "women")
				.multiPart("productImage",new File("C://Users//10851440//OneDrive - LTIMindtree//Documents//LaptopImage.png"));
				
		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
			.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");
				
		// Create Order
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addHeader("Authorization", token).setContentType(ContentType.JSON)
				.build();
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);
		
		String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order")
			.then().log().all().extract().response().asString();
		
		JsonPath js2 = new JsonPath(responseAddOrder);
		String orderId = js2.get("orderId");
		
		System.out.println(responseAddOrder);
		System.out.println("Order Id is : " + orderId);
		
		// View Order Details
		// Check View Order Details again need to check id which Id to pass OrderId or ProductId
		/*
		System.out.println("####################################### View Order Details #######################################");
		RequestSpecification viewOrderBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification reqViewOrder = given().log().all().spec(viewOrderBasereq)
					.param("id", orders);
		
		String responseViewOrderDetails = reqViewOrder.when().get("/api/ecom/order/get-orders-details")
			.then().log().all().extract().response().asString();
		
		
		System.out.println(responseViewOrderDetails);
		
		System.out.println("####################################### View Order Details #######################################");
		
		*/
		
		// Delete Order
		// Here we will use path parameters
		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addHeader("Authorization", token).setContentType(ContentType.JSON)
				.build();
		
		RequestSpecification deleteProdReq = given().log().all().spec(deleteProductBaseReq)
				.pathParam("productId", productId);
		
		String deleteProductresponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}")
			.then().log().all().extract().response().asString();
		
		JsonPath js3 = new JsonPath(deleteProductresponse);
		Assert.assertEquals("Product Deleted Successfully", js3.get("message"));
		
		
	}
}
