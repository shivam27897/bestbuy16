package com.bestbuy.storeinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.Utils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCurdTest extends TestBase {

    static String name = "software testing" + Utils.getRandomValue();
    static String address = "10 dorchester" + Utils.getRandomValue();
    static Object storesID;

    @Title("get all stores list")
    @Test
    public void test001() {

        SerenityRest.given()
                .when()
                .log().all()
                .get(EndPoints.GET_ALL_STORES)
                .then().log().all().statusCode(200);
    }

    @Title("create new stores")
    @Test
    public void test002() {

        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType("training");
        pojo.setAddress("10 dorchester");
        pojo.setAddress2("ha39rf");
        pojo.setCity("harrow");
        pojo.setState("fyfcy");
        pojo.setZip("1234");
        pojo.setLat(1);
        pojo.setLng(6);
        pojo.setHours("12:00");


        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_STORES)
                .then().log().all()
                .statusCode(201);
    }

    @Title("Verify if stores was created")
    @Test
    public void test003() {

        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, ?> storemap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        System.out.println();
        Assert.assertThat(storemap, hasValue(name));
        storesID = storemap.get("id");
    }

    @Title("update the stores and verify the update information")
    @Test
    public void test004() {

        address = address + "abc";
        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType("testing");
        pojo.setAddress(address);
        pojo.setAddress2("ha39rf");
        pojo.setCity("harrow");
        pojo.setState("fyfcy");
        pojo.setZip("1234");
        pojo.setLat(2);
        pojo.setLng(6);
        pojo.setHours("11:00");

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID", storesID)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_STORES_BY_ID)
                .then()
                .statusCode(200);


        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> storemap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        Assert.assertThat(storemap, hasValue(name));
    }

    @Title("Delete the stores and verify if the stores is deleted")
    @Test
    public void test005() {

        SerenityRest.given()
                .pathParam("storesID", storesID)
                .when()
                .delete(EndPoints.DELETE_STORES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("storesID", storesID)
                .when()
                .get(EndPoints.GET_SINGLE_STORES_BY_ID)
                .then().log().all().statusCode(404);
    }
}