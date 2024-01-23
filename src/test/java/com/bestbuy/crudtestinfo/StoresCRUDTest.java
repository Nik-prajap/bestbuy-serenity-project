package com.bestbuy.crudtestinfo;

import com.bestbuy.stepsinfo.StoresSteps;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import testbase.TestBaseForStores;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class StoresCRUDTest extends TestBaseForStores {

    static String name = TestUtils.getRandomValue() + "Prime";
    static String type = "Testing";
    static String address = "101-Shanti-Bhawan,";

    static String address2 = "Matajina Garba Party Plot";
    static String city = "Ahmedabad";

    static String state = "Gujarat";
    static String zip = "380004";
    static int storeID;

    @Steps
    StoresSteps storesSteps;

    @Title("This will create a new store")
    @Test
    public void test001() {
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip)
                .statusCode(201);
        storeID = response.log().all().extract().path("id");

    }

    @Title("Verify if the store was added to application")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = storesSteps.getStoreInfoByStoreName(storeID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Update the store information and verify the updated information")
    @Test
    public void test003() {
        ValidatableResponse response = storesSteps
                .updateStore(storeID, name, type, address, address2, city, state, zip).statusCode(200);
        storeID = response.log().all().extract().path("id");

        HashMap<String, Object> productMap = storesSteps.getStoreInfoByStoreName(storeID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Delete the Store and verify if the store has been deleted")
    @Test
    public void test004() {
        storesSteps.deleteStore(storeID).statusCode(200);
        storesSteps.getStoreById(storeID).statusCode(404);

    }

}
