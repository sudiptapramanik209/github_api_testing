package github_restassord;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;
public class github_api {
	public static String baseUrl="https://api.github.com";
	public static String owner;
	public static String repo_name;
	public static String token="";
	JsonPath jsonpath;
	public static String shs;
	public static int id;
	
	
//	1 CREATE_A_REPOSITORY_FOR_A_AUTHENTICATED_USER
	@Test(priority=0)
	public void CREATE_A_REPOSITORY_FOR_A_AUTHENTICATED_USER() {
		String jsonBody = "{\r\n"
				+ "	\"name\": \"Masai-API-project\",\r\n"
				+ "	\"description\": \"This is created by API as project 777\" \r\n"
				+ "}";
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.body(jsonBody)
				.when()
				.post("/user/repos")
				.then()
				.assertThat()
				.statusCode(201)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
//		System.out.println(res);
		
		JsonPath js = new JsonPath(res);
		
		owner = js.get("owner.login") ;
		System.out.println(owner);
		
		repo_name = js.get("name") ;
		System.out.println(repo_name);
		System.out.println("Created repo for Authenticated User!!!");
	}
	
//	2 UPDATE_A_REPOSITORY
	@Test(priority=1)
	public void UPDATE_A_REPOSITORY() {
		String jsonBody = "{\r\n"
				+ "	\"name\":\"Masai_updated_repo\"\r\n"
				+ "}";
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.body(jsonBody)
				.when()
				.patch("/repos/"+owner+"/"+repo_name)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
//		System.out.println(res);
		
		JsonPath js = new JsonPath(res);
		
		repo_name = js.get("name") ;
		System.out.println(repo_name);
		System.out.println("updated the name in Repository!!!");
	}
//	4 GET_A_REPOSITORY
	@Test(priority=2)
	public void GET_A_REPOSITORY() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.when()
				.get("/repos/"+owner+"/"+repo_name)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
//	5 CREATE_FILE_CONTENT
	@Test(priority=3)
	public void CREATE_FILE_CONTENT() {
		String jsonBody="{\r\n"
				+ "                \"message\":\"my commit message\",\r\n"
				+ "                \"content\":\"SGVsbG8gbmV3IEZpbGUgQ3JlYXRlZA==\"\r\n"
				+ "            }";
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.body(jsonBody)
				.when()
				.put("/repos/"+owner+"/"+repo_name+"/contents/javaScript.js")
				.then()
				.assertThat()
				.statusCode(201)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
		
		JsonPath js =new JsonPath(res);
		shs = js.get("content.sha");
		System.out.println(shs);
	}
	@Test(priority=4)
	public void CREATE_A_FORK() throws InterruptedException {
		String jsonBody="{\r\n"
				+ "                \"organization\":\"sudipta1234-pramanik\",\r\n"
				+ "                \"name\":\"new_fork456\",\r\n"
				+ "                \"default_branch_only\":true\r\n"
				+ "                \r\n"
				+ "            }";
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.body(jsonBody)
				.when()
				.post("/repos/"+owner+"/"+repo_name+"/forks")
				.then()
				.assertThat()
				.statusCode(202)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
		
		Thread.sleep(5000);
	}
	
	@Test(priority=5)
	public void LIST_FORK() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.when()
				.get("/repos/"+owner+"/"+repo_name+"/forks")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
	@Test(priority=6)
	public void LIST_REPOSITORIES_FOR_A_USER() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.when()
				.get("/users/"+owner+"/repos")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
	@Test(priority=7)
	public void LIST_REPOSITORY_LANGUAGE() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.when()
				.get("/repos/"+owner+"/"+repo_name+"/languages")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
	@Test(priority=8)
	public void LIST_PUBLIC_REPOSITORIES() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.when()
				.get("/repositories")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
	@Test(priority=9)
	public void CREATE_A_RELEASE() {
		String jsonBody="{\r\n"
				+ "                \"tag_name\": \"Git_release_tag\"\r\n"
				+ "            }";
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.body(jsonBody)
				.when()
				.post("/repos/"+owner+"/"+repo_name+"/releases")
				.then()
				.assertThat()
				.statusCode(201)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
	@Test(priority=10)
	public void LIST_REPOSITORY_TAGS() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
			
				.when()
				.get("/repos/"+owner+"/"+repo_name+"/tags")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
	@Test(priority=11)
	public void CREATE_AN_AUTOLINK_REFERENCE_FOR_A_REPOSITORY() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.body("{\n" +
	                    "  \"key_prefix\": \"Github-Api-Testing\",\n" +
	                    "  \"url_template\": \"https://example.com/Api?query=<num>\"\n" +
	                    "}")
				.when()
				.post("/repos/"+owner+"/"+repo_name+"/autolinks")
				.then()
				.assertThat()
				.statusCode(201)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
		JsonPath js =new JsonPath(res);
		id = js.get("id");
		System.out.println(id);
	}
	
	@Test(priority=12)
	public void GET_AN_AUTOLINK_REFERENCE_FOR_A_REPOSITORY() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
			
				.when()
				.get("/repos/"+owner+"/"+repo_name+"/autolinks/"+id)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
		
	}
	@Test(priority=13)
	public void GET_A_REPOSITORY_AGAIN() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.when()
				.get("/repos/"+owner+"/"+repo_name)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
	@Test(priority=14)
	public void REPLACE_ALL_REPOSITORY_TOPICS() {
		String jsonBody="{\r\n"
				+ "                \"names\": [\r\n"
				+ "                \"octocat\",\r\n"
				+ "                \"atom\",\r\n"
				+ "                \"electron\",\r\n"
				+ "                \"api\"\r\n"
				+ "              ]\r\n"
				+ "            }";
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.body(jsonBody)
				.when()
				.put("/repos/"+owner+"/"+repo_name+"/topics")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
	@Test(priority=15)
	public void GET_ALL_REPOSITORY_TOPICS() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
				.when()
				.get("/repos/"+owner+"/"+repo_name+"/topics")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
	
	}
	
	@Test(priority=17)
	public void DELETE_AN_AUTOLINK_REFERENCE_FOR_A_REPOSITORY() {
		
		RestAssured.baseURI = baseUrl;
		Response response = given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+token)
			
				.when()
				.delete("/repos/"+owner+"/"+repo_name+"/autolinks/"+id)
				.then()
				.assertThat()
				.statusCode(204)
				.extract().response();
		
		String res = response.asString() ;
		System.out.println(res);
		
	}
	
	@Test(priority=18)
	public void DELETE_FORK() {
	
			RestAssured.baseURI = baseUrl;
			Response response = given()
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer "+token)
					.when()
					.delete("/repos/"+"sudipta1234-pramanik/new_fork456")
					.then()
					.assertThat()
					.statusCode(204)
					.extract().response();
			
			String res = response.asString() ;
			System.out.println(res);
		}
	
	@Test(priority=19)
	public void DELETE_A_FILE() {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		jsonData.put("message", "deleted file");
		jsonData.put("sha", shs);
			RestAssured.baseURI = baseUrl;
			Response response = given()
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer "+token)
					.body(jsonData)
					.when()
					.delete("/repos/"+owner+"/"+repo_name+"/contents/javaScript.js")
					.then()
					.assertThat()
					.statusCode(200)
					.extract().response();
			
			String res = response.asString() ;
			System.out.println(res);
		}
		
	@Test(priority=20)
	public void DELETE_A_REPOSITORY() {
		
			RestAssured.baseURI = baseUrl;
			Response response = given()
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer "+token)
					.when()
					.delete("/repos/"+owner+"/"+repo_name)
					.then()
					.assertThat()
					.statusCode(204)
					.extract().response();
			
			String res = response.asString() ;
			System.out.println(res);
		}
}