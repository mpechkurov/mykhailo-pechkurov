package com.bestbuy.products;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bestbuy.TestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

public class ProductsValidationTest extends TestBase {

    public static final String WRONG_PRODUCT_ID = "0987654";
    private final List<String> expectedErrorsForMandatoryField = Arrays.asList("should have required property 'name'",
                                                                               "should have required property 'type'",
                                                                               "should have required property 'upc'",
                                                                               "should have required property "
                                                                               + "'description'",
                                                                               "should have required property 'model'");
    private JSONObject requestParams;

    @BeforeClass
    public static void setUp() {
        setUpDefaultConfiguration();
    }

    @Before
    public void setUpTest() {
        requestParams = new JSONObject();
    }

    @Test
    public void createProductMandatoryFieldValidationTest() {

        given()
            .contentType(JSON)
            .body(requestParams.toString())
            .when()
            .post("/products")
            .then()
            .statusCode(400)
            .body("name", equalTo("BadRequest"),
                  "message", equalTo("Invalid Parameters"),
                  "errors", equalTo(expectedErrorsForMandatoryField));
    }

    @Test
    public void createProductExtraFieldValidationTest() {
        setUpMinimalCorrectProduct(requestParams);
        requestParams.put("wrongfireld", "someValue");

        given()
            .contentType(JSON)
            .body(requestParams.toString())
            .when()
            .post("/products")
            .then()
            .statusCode(400)
            .body("name", equalTo("BadRequest"),
                  "message", equalTo("Invalid Parameters"),
                  "errors", equalTo(Arrays.asList("should NOT have additional properties: 'wrongfireld'"))
                 );
    }

    @Test
    public void deleteProductsValidationTest() {
        when()
            .delete("/products/{productId}", WRONG_PRODUCT_ID)
            .then()
            .statusCode(404)
            .body("name", equalTo("NotFound"),
                  "message", equalTo(String.format("No record found for id '%s'", WRONG_PRODUCT_ID)),
                  "code", equalTo(404),
                  "className", equalTo("not-found"));
    }

    @Test
    public void getProductsByIdValidationTest() {
        when()
            .get("/products/{productId}", WRONG_PRODUCT_ID)
            .then()
            .statusCode(404)
            .body("name", equalTo("NotFound"),
                  "message", equalTo(String.format("No record found for id '%s'", WRONG_PRODUCT_ID)),
                  "code", equalTo(404),
                  "className", equalTo("not-found"));
    }

}
