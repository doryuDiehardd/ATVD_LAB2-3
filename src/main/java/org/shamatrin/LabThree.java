package org.shamatrin;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.*;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LabThree {
    private static final String baseUrl = "https://82f4dd58-eaad-4b22-ae2f-fd040d44e139.mock.pstmn.io";
    @BeforeClass
    public void setup(){
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new
                RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }
    @Test()
    public void verifyGetSuccess(){
        given().get("/shamatrin/lab4/get/success")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
    @Test(dependsOnMethods = "verifyGetSuccess")
    public void verifyGetFailure(){
        given().get("/shamatrin/lab4/get/failure")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
    @Test(dependsOnMethods = "verifyGetFailure")
    public void verifyPost200(){
        Response response = given().post("/shamatrin/lab4/post/create?permission=yes");
        response.then().statusCode(HttpStatus.SC_OK);
        System.out.println(response.jsonPath().get("result").toString());
    }
    @Test(dependsOnMethods = "verifyPost200")
    public void verifyPost400(){
        Response response = given().post("/shamatrin/lab4/post/create");
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        System.out.println(response.jsonPath().get("result").toString());
    }
    @Test(dependsOnMethods = "verifyPost400")
    public void verifyPut200(){
        Map<String, ?> body = Map.of(
                "name", "Andrii",
                "surname", "Shamatrin"
        );
        given().body(body)
                .header("Content-Type", "application/json")
                .put("/shamatrin/lab4/put/update?loggedIn=1")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
    @Test(dependsOnMethods = "verifyPut200")
    public void verifyPut500(){
        Map<String, ?> body = Map.of(
                "name", "",
                "surname", ""
        );
        given().body(body)
                .header("Content-Type", "application/json")
                .put("/shamatrin/lab4/put/update")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
    @Test(dependsOnMethods = "verifyPut500")
    public void verifyDelete(){
        Response response = given().header("SessionKey",
                "Junior").delete("/shamatrin/lab4/deleteAll");
        response.then().statusCode(HttpStatus.SC_GONE);
        System.out.println(response.jsonPath().get("msg").toString());
    }
}