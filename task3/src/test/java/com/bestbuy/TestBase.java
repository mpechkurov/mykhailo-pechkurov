package com.bestbuy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;

public class TestBase {

    public static void setUpConfiguration(String uri, int port) {
        RestAssured.baseURI = uri;
        RestAssured.port = port;
    }

    public static void setUpDefaultConfiguration() {
        setUpConfiguration("http://localhost", 3030);
    }

    public void setUpMinimalCorrectProduct(JSONObject product) {
        product.put("name", "test");
        product.put("type", "testType");
        product.put("upc", "12345upc");
        product.put("description", "test description");
        product.put("model", "testModel");
    }

    public Date validateDateFormat(String dateToValdate) {

        //        "2019-02-17T11:25:20.929Z"
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddThh:mm:ss");
        //To make strict date format validation
        formatter.setLenient(false);
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateToValdate);
            System.out.println("++validated DATE TIME ++" + formatter.format(parsedDate));

        } catch (ParseException e) {
            //Handle exception
        }
        return parsedDate;
    }

    protected void deleteProductById(int productId) {
        when().delete("/products/{productId}", productId);
    }

    protected int createProduct(JSONObject jsonObject) {
        return given().contentType(JSON).body(jsonObject.toString()).post("/products/").then().contentType(JSON)
                      .extract().path("id");
    }

}
