package com.bestbuy.categoriesinfo;

import com.bestbuy.stepsinfo.CategoriesSteps;
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
public class CategoriesTestWithSteps extends TestBase {
    static String name = "nice gift" + Utils.getRandomValue();
    static String id = "abcd" + Utils.getRandomValue();
    static Object categoryId;

    @Steps
    CategoriesSteps categoriesSteps;

    @Title("This will create a new categories")
    @Test
    public void test001() {

        ValidatableResponse response =categoriesSteps.createCategories(name,id);
        response.statusCode(201);

    }

    @Title("verify if categories is created")
    @Test
    public void test002() {
        HashMap<String, Object> categoriesMapData =categoriesSteps.getCategoriesInfoByName(name);
        Assert.assertThat(categoriesMapData,hasValue(name));
        categoryId= categoriesMapData.get("id");
    }

    @Title("update the categories information")
    @Test
    public void test003() {
        name = name +"11";

        categoriesSteps.updateCategories(categoryId,name,id);
        HashMap<String,Object> mapData= categoriesSteps.getCategoriesInfoByName(name);
        Assert.assertThat(mapData,hasValue(name));

    }
    @Title("Delete categories info by StudentID and verify its deleted")
    @Test
    public void test004(){
        categoriesSteps.deleteCategoriesInfoByID(categoryId).statusCode(200);
        categoriesSteps.getCategoriesInfoByCategoriesId(categoryId).statusCode(404);
    }
}
