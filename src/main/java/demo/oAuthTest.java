package demo;
import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;
public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://accounts.google.com/v3/signin/accountchooser?auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&response_type=code&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&dsh=S505729902%3A1788760533546828&o2v=2&service=lso&flowName=GeneralOAuthFlow&opparams=%253Fauth_url%253Dhttps%25253A%25252F%25252Faccounts.google.com%25252Fo%25252Foauth2%25252Fv2%25252Fauth&continue=https%3A%2F%2Faccounts.google.com%2Fsignin%2Foauth%2Fv3%2Fconsent%3Fauthuser%3Dunknown%26part%3DAJi8hANIkeIz5i_-_GPGykEl1exm36We4WVZlR4uRrsMt4OR4kjZ0M_Pa9mgAQ6a0bEYdpd8s6uoRdt4HVFc93LB9tNnnyR8dPKpQIBAQaRdS6Ibfpdeqq6y1b-CYnzLI2ys8llUPsQE9SqI0VsMfwReK0GTRHcqgcndoFoJ2tfaqFAk6pQ_Q4XZVetMWPuVJJeYDNEqJZI4PEILUR-X3zYG8qhn31S5af_6S42hIStnNMfxcr_t-E1qfqXDrUSErbU-hvnO4IkTAPYgwpiLX1IMmiCplXgMd-2WtsTGRfnEjqo-_9yHKZyJyeGlnyuW713Wo0lbB2vwSlkKT7YTlOs8SVSdkL1_Vl3Zj-5Dza2L0SnExJF7aM4srgYL7pGAl5Nw_vB7O3PS6_aBwA4nP-NG4EDi1XuA9HdDXv0VfEWzHTiQrTpb4WbVDxVAUSbeN4CMc-pOa4q0s8-9gvx-CWJQ_wQ5Dn3RNg%26flowName%3DGeneralOAuthFlow%26as%3DS505729902%253A1788760533546828%26client_id%3D692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com%26requestPath%3D%252Fsignin%252Foauth%252Fv3%252Fconsent%23&app_domain=https%3A%2F%2Frahulshettyacademy.com");
//		//driver.findElement(By.cssSelector("input[type='email']")).sendKeys("mangesh013@gmail.com");
//		driver.findElement(By.id("identifierId")).sendKeys("mangesh013@gmail.com");
//		
//		driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("mangesh013@gmail.com");
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(4000);
//		String url = driver.getCurrentUrl();
		
		String url = "https://rahulshettyacademy.com/getCourse.php?iss=https://accounts.google.com&code=4/0ATsMZqDSi-pa_vaZK1DkZThPXxnKSnSDFiFbskmbnLQFn8Y9QIdewOek9MjuaIONbC5bjg&scope=email+https://www.googleapis.com/auth/userinfo.email+openid&authuser=0&prompt=none";
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&")[0];
		
		System.out.println("Code : " + code);
		
		System.out.println("############################### Get Code ###############################");
		String getCode = given().log().all()
				.queryParams("scope","https://www.googleapis.com/auth/userinfo.email")
				.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("response_type","code")
				.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
			.when().log().all()
			.get("https://accounts.google.com/o/oauth2/v2/auth").asString();
		
		JsonPath js2 = new JsonPath(getCode);
		//String code = js2.getString("code");
		System.out.println("Code : " + code);
		
		System.out.println("############################### Get access token ###############################");
		// exchangecode
		String accessTokenResponse = given().urlEncodingEnabled(false)
			.queryParams("code", code)
			.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type","authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token")
		.asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String access_token = js.getString("access_token");
		System.out.println("access_token : " + access_token);
		
		System.out.println("############################### Get actual resource ###############################");

		String response = given()
			.queryParam("access_token",access_token)
		.when()
			.get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(response);
	}
}
