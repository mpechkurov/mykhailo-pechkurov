package com.bestbuy.products;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bestbuy.TestBase;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class GetProductsByIdTest extends TestBase {

    private static final int PRODUCT_ID = 150115;

    @BeforeClass
    public static void setUp() {
        setUpDefaultConfiguration();
    }

    @Test
    public void getProductByIdTest() {
        when()
            .get("/products/{productId}", PRODUCT_ID)
            .then()
            .statusCode(SC_OK)
            .assertThat()
            .body("id", equalTo(PRODUCT_ID));
    }

    @Test
    public void getProductByIdReturnCorrectJson() {
        when()
            .get("/products/{productId}", PRODUCT_ID)
            .then()
            .statusCode(SC_OK)
            .assertThat()
            .body(matchesJsonSchemaInClasspath("products-schema.json"));
    }

}
