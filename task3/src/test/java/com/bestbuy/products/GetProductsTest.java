package com.bestbuy.products;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bestbuy.TestBase;

import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

public class GetProductsTest extends TestBase {

    private static final int DEFAULT_AMOUNT_OF_PRODUCTS = 10;
    private static final int PRODUCT_LIMIT_AMOUNT = 2;
    private static final int MAXIMUM_LIMIT_FOR_RETURNED_PRODUCTS = 25;
    private static final int NOT_EXISTED_AMOUNT_OF_PRODUCTS = 60000;

    @BeforeClass
    public static void setUp() {
        setUpDefaultConfiguration();
    }

    @Test
    public void getProductsReturnDefaultAmountOfProductsTest() {
        when().get("/products").then().statusCode(SC_OK)
              .body("total", notNullValue(),
                    "limit", equalTo(DEFAULT_AMOUNT_OF_PRODUCTS),
                    "skip", equalTo(0),
                    "data.id", hasSize(DEFAULT_AMOUNT_OF_PRODUCTS));
    }

    @Test
    public void getProductsWithLimitParameterTest() {
        when().get("/products?$limit={lim}", PRODUCT_LIMIT_AMOUNT).then().statusCode(SC_OK)
              .body("limit", equalTo(PRODUCT_LIMIT_AMOUNT),
                    "data.id", hasSize(PRODUCT_LIMIT_AMOUNT));
    }

    @Test
    public void getProductsWithLimitZeroTest() {
        when().get("/products?$limit={lim}", 0).then().statusCode(SC_OK)
              .body("limit", equalTo(0),
                    "data.id", hasSize(0));
    }

    @Test
    public void getProductsWithLimitMoreThanProductAmountTest() {
        when().get("/products?$limit={lim}", NOT_EXISTED_AMOUNT_OF_PRODUCTS).then().statusCode(SC_OK)
              .body("limit", equalTo(MAXIMUM_LIMIT_FOR_RETURNED_PRODUCTS));
    }

    @Test
    public void getProductsWithSkipParameterTest() {
        when().get("/products?$skip={skip}", 5).then().statusCode(SC_OK)
              .body("skip", equalTo(5),
                    "data.id", hasSize(DEFAULT_AMOUNT_OF_PRODUCTS));
    }

    @Test
    public void getProductsWithSkipParameterMoreThanProductAmountTest() {
        when().get("/products?$skip={skip}", NOT_EXISTED_AMOUNT_OF_PRODUCTS).then().statusCode(SC_OK)
              .body("skip", equalTo(NOT_EXISTED_AMOUNT_OF_PRODUCTS),
                    "data.id", empty());
    }

    @Test
    public void getProductsWithLimitAndSkipParametersTest() {
        when().get("/products?$limit={lim}&$skip={skip}", 5, 5).then().statusCode(SC_OK)
              .body("limit", equalTo(5),
                    "skip", equalTo(5));
    }

}
