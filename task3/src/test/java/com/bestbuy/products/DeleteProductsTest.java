package com.bestbuy.products;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bestbuy.TestBase;

import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DeleteProductsTest extends TestBase {
    private JSONObject requestParams;
    private int productId;

    @BeforeClass
    public static void setUp() {
        setUpDefaultConfiguration();
    }

    @Before
    public void setUpTest() {
        requestParams = new JSONObject();
        setUpMinimalCorrectProduct(requestParams);
        productId = createProduct(requestParams);
    }

    @Test
    public void deleteProductTest() {
        when()
            .delete("/products/{productId}", productId)
            .then()
            .statusCode(SC_OK)
            .body("id", equalTo(productId),
                  "name", equalTo("test"),
                  "type", equalTo("testType"),
                  "upc", equalTo("12345upc"),
                  "description", equalTo("test description"),
                  "model", equalTo("testModel"),
                  "updatedAt", notNullValue(),
                  "createdAt", notNullValue());
    }

}
