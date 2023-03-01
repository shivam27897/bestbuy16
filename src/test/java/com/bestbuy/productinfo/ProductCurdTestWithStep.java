package com.bestbuy.productinfo;


import com.bestbuy.stepsinfo.CategoriesSteps;
import com.bestbuy.stepsinfo.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.Utils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCurdTestWithStep extends TestBase {

    static String name = "shivam" + Utils.getRandomValue();
    static String type = "standard" + Utils.getRandomValue();
    static String model = "ytedf" + Utils.getRandomValue();
    static int price = 123;
    static int ship = 122;
    static String upc = "sdfsf";
    static String disc = "dfbgd";
    static String manufac= "fdvfdv";
    static String url = "dfvdfdffd";
    static String img = "gredgtg";
    static Object productsID;


    @Steps
    ProductSteps productSteps;

    @Title("This will create a new products")
    @Test
    public void test001() {

        ValidatableResponse response =productSteps.createProducts( name, type, price, ship, upc, disc, manufac, model,url,img);
        response.statusCode(201);

    }

    @Title("verify if products is created")
    @Test
    public void test002() {
        HashMap<String, Object> productsMapData =productSteps.getProductsInfoByName(name);
        Assert.assertThat(productsMapData,hasValue(name));
        productsID= productsMapData.get("id");
    }

    @Title("update the products information")
    @Test
    public void test003() {
        name = name +"11";

        productSteps.updateProducts(productsID,name, type, price, ship, upc, disc, manufac, model,url,img);
        HashMap<String,Object> mapData= productSteps.getProductsInfoByName(name);
        Assert.assertThat(mapData,hasValue(name));

    }
    @Title("Delete products info by productsID and verify its deleted")
    @Test
    public void test004(){
        productSteps.deleteProductsInfoByID(productsID).statusCode(200);
        productSteps.getProductsInfoByProductsId(productsID).statusCode(404);
    }
}
