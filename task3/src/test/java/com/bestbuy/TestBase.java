package com.bestbuy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.json.JSONObject;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;

public class TestBase {

    private static final String CONFIG_PROPERTIES = "src/test/resources/config.properties";

    public static void setUpConfiguration(String uri, int port) {
        RestAssured.baseURI = uri;
        RestAssured.port = port;
    }

    protected static void setUpDefaultConfiguration() {
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream(CONFIG_PROPERTIES);
            property.load(fis);
            setUpConfiguration(property.getProperty("host"), Integer.valueOf(property.getProperty("port")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setUpMinimalCorrectProduct(JSONObject product) {
        product.put("name", "test");
        product.put("type", "testType");
        product.put("upc", "12345upc");
        product.put("description", "test description");
        product.put("model", "testModel");
    }

    protected void deleteProductById(int productId) {
        when().delete("/products/{productId}", productId);
    }

    protected int createProduct(JSONObject jsonObject) {
        return given().contentType(JSON).body(jsonObject.toString()).post("/products/").then().contentType(JSON)
                      .extract().path("id");
    }

}
