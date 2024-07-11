package verifymessagepi;

import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class TestingCommunication {
	
	
	private static final String URL = "http://localhost:3000/api";
	private static final String MESSAGE_ENDPOINT = "/messages";
    private static final String FROM_USER_ID = "748971f1-4d7d-40d0-8fa4-2b2d8b0d3ad5";
    private static final String TO_USER_ID = "a7ecb590-a5c3-4894-aaab-3d7a2932a8dc";
    private static String MESSAGE_CONTENT = "Greetings from Perry’s Summer Vacation";
    private static String UPDATED_MESSAGE_CONTENT = "Greetings from Perry’s Summer Vacation Goods and Services";
    String messageId= null;
    
    private String createMessageJson() {
        return "{\n" +
                "    \"from\": { \"id\": \"" + FROM_USER_ID + "\" },\n" +
                "    \"to\": { \"id\": \"" + TO_USER_ID + "\" },\n" +
                "    \"message\": \"" + MESSAGE_CONTENT + "\"\n" +
                "}";
    }
    
    private String updateMessageJson() {
        return "{\n" +
                "    \"message\": \""+UPDATED_MESSAGE_CONTENT+"\"\n" +
                "}";
    }

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = URL;
    }
    
    @Test (priority=1)
    public void createMessage() {  
    	// Create a message
    	Response response = given()
                .contentType(ContentType.JSON)
                .body(createMessageJson())
                .when()
                .post(MESSAGE_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        messageId = response.jsonPath().getString("id");
    }
    
    @Test (priority=3,dependsOnMethods={"createMessage"})
    public void updateMessage() {
  
        // PUT -Updating the message
        given()
                .pathParam("id", messageId)
                .contentType(ContentType.JSON)
                .body(updateMessageJson())
                .when()
                .put(MESSAGE_ENDPOINT + "/:{id}")
                .then()
                .statusCode(200);

    }
    
    @Test (priority=2,dependsOnMethods={"createMessage"})
    public void getMessageById() {
    	//GET - Get a message by ID 
        given()
                .pathParam("id", messageId)
                .when()
                .get(MESSAGE_ENDPOINT + "?{id}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .response();
    }
    
    @Test (priority=4)
    public void messagesBetweenUsers() {
       // GET -Getting messages between two users
        given()
        		.when()
                .get(MESSAGE_ENDPOINT+ "?from="+FROM_USER_ID+"&to="+TO_USER_ID+"")
                .then().log().all()
                .statusCode(200)
                .extract()
                .response();

    }
    
    @Test(priority=2,dependsOnMethods={"createMessage"})
    public void deleteMessage() {

        // Delete the message
        given()
                .pathParam("id", messageId)
                .when()
                .delete(MESSAGE_ENDPOINT + "/{id}")
                .then()
                .statusCode(204);

    }

}