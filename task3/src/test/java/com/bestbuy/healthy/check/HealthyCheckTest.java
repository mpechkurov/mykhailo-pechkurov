package com.bestbuy.healthy.check;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bestbuy.TestBase;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class HealthyCheckTest extends TestBase {

    @BeforeClass
    public static void setUp() {
        setUpDefaultConfiguration();
    }

    @Test
    public void healthTest() {
        when()
            .get("/healthcheck")
            .then().statusCode(200)
            .body("uptime", notNullValue());
    }

    @Test
    public void versionTest() {
        when()
            .get("/version")
            .then()
            .statusCode(200)
            .body("version", equalTo("1.1.0"));
    }
}
