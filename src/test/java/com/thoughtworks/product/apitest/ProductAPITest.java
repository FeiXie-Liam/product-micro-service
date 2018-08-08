package com.thoughtworks.product.apitest;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DBRider
@DBUnit(caseSensitiveTableNames = true)
public class ProductAPITest {

    @LocalServerPort
    private int port;

    @Test
    @DataSet("product.yml")
    public void should_return_a_product() {
        RestAssured
                .given()
                .port(port)
                .when()
                .get("/products/1")
                .then()
                .statusCode(200)
                .body("name", is("苹果"))
                .body("imageUrl", is("./assets"));
    }
}
