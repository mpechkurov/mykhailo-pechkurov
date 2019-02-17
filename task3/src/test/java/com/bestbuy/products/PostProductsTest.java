package com.bestbuy.products;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bestbuy.TestBase;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostProductsTest extends TestBase {

    private JSONObject requestParams;
    private int productId;

    @BeforeClass
    public static void setUp() {
        setUpDefaultConfiguration();
    }

    @Before
    public void setUpTestData() {
        requestParams = new JSONObject();
    }

    @After
    public void eraseProduct() {
        deleteProductById(productId);
    }

    @Test
    public void createProductTest() {
        setUpMinimalCorrectProduct(requestParams);

        Response myResponse = given().contentType(JSON).body(requestParams.toString()).post("/products");
        productId = myResponse.then().contentType(JSON).extract().path("id");
        myResponse.then().statusCode(SC_CREATED).body("id", notNullValue(),
                                                      "name", equalTo("test"),
                                                      "type", equalTo("testType"),
                                                      "upc", equalTo("12345upc"),
                                                      "description", equalTo("test description"),
                                                      "model", equalTo("testModel"),
                                                      "updatedAt", notNullValue(),
                                                      "createdAt", notNullValue());
    }

}
