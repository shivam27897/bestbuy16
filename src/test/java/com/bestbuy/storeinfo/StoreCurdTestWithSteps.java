package com.bestbuy.storeinfo;

import com.bestbuy.stepsinfo.ProductSteps;
import com.bestbuy.stepsinfo.StoreSteps;
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
public class StoreCurdTestWithSteps extends TestBase {
    static String name = "software" + Utils.getRandomValue();
    static String type = "standard";

    static String add = "traj";
    static String add2 = "ha36td";
    static String city= "harrow";
    static String state = "fyfcys";
    static String zip = "1234";
    static int lat = 1;
    static int lng = 6;
    static String hours = "12:00";
    static Object storesID;


    @Steps
    StoreSteps storeSteps;

    @Title("This will create a new stores")
    @Test
    public void test001() {

        ValidatableResponse response =storeSteps.createStores( name,  type,   add,  add2, city, state, zip,  lat,  lng, hours);
        response.statusCode(201);

    }

    @Title("verify if stores is created")
    @Test
    public void test002() {
        HashMap<String, Object> productsMapData =storeSteps.getStoresInfoByName(name);
        Assert.assertThat(productsMapData,hasValue(name));
        storesID= productsMapData.get("id");
    }

    @Title("update the stores information")
    @Test
    public void test003() {
        name = name +"11";

        storeSteps.updateStores(storesID,name,  type,  lat,  lng,  add,  add2, city, state, zip,  hours);
        HashMap<String,Object> mapData= storeSteps.getStoresInfoByName(name);
        Assert.assertThat(mapData,hasValue(name));

    }
    @Title("Delete stores info by storeID and verify its deleted")
    @Test
    public void test004(){
        storeSteps.deleteStoresInfoByID(storesID).statusCode(200);
        storeSteps.getStoresInfoByStoresId(storesID).statusCode(404);
    }
}
