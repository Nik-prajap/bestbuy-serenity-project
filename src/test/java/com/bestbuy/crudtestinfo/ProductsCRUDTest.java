package com.bestbuy.crudtestinfo;

import com.bestbuy.stepsinfo.ProductsSteps;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import testbase.TestBaseForProducts;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class ProductsCRUDTest extends TestBaseForProducts {

    static String name = TestUtils.getRandomValue() + "PrimeUser";
    //static String UpdatedName = TestUtils.getRandomValue() + "UpdatedName";
    static String type = "HardGood";
    static String upc = "0987" + TestUtils.getRandomValue();
    static double price = 5.99;
    static String description = "Creating New product of Alkaline batteries 5.5v";
    static String model = "Battery Model";
    static int productID;

    @Steps
    ProductsSteps steps;


    // create product
    @Title("This will create a new product")
    @Test
    public void test001() {

        ValidatableResponse response = steps.createProduct(name, type, upc, price, description, model)
                .log().all()
                .statusCode(201);

        productID = response.log().all().extract().path("id");

    }

    // read product
    @Title("Verify that the product is added successfully")
    @Test
    public void test002() {

        HashMap<String, ?> productMap = steps.getProductInfoByProductId(productID);
        Assert.assertThat(productMap, hasValue(name));

        productID = (int) productMap.get("id");

    }

    @Title("Update the Product and Verify the updated Product")
    @Test
    public void test003() {

        ValidatableResponse response = steps.updateProduct(productID, name, type, upc, price, description, model);

        productID = response.log().all().extract().path("id");

        HashMap<String, ?> productMap = steps.getProductInfoByProductId(productID);

        Assert.assertThat(productMap, hasValue(name));

    }

    @Title("Delete the Product and verify if the product is deleted")
    @Test
    public void test004() {

        // Delete Product
        steps.deleteProduct(productID).statusCode(200);

        // Get Product List
        steps.getProductInfoById(productID).statusCode(404);
    }
}
