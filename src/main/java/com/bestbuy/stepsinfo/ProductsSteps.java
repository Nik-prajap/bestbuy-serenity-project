package com.bestbuy.stepsinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class ProductsSteps extends ProductPojo {

    @Step("Creating Products with name : {0} , type : {1}, upc : {2}, price : {3}, description : {4} and model : {5}")
    public ValidatableResponse createProduct(String name, String type, String upc, double price, String description, String model) {

        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, upc, price, description, model);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .when().body(productPojo)
                .post(Path.PRODUCTS)
                .then().log().all().statusCode(201);

    }


    @Step("Getting the Product information with ProductId : {0}")
    public HashMap<String, Object> getProductInfoByProductId(int productID) {
//        String s1 = "findAll{it.name == '";
//        String s2 = "'}.get(0)";

        return SerenityRest.given().log().all()
                .when().pathParam("productID", productID)
                .get(Path.PRODUCTS + EndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path("");
    }

    @Step("Updating student information with studentId : {0}, name : {1} , type : {2}, upc : {3}, price : {4}, description : {5} and model : {6}")
    public ValidatableResponse updateProduct(int productID, String name, String type, String upc, double price, String description, String model) {


        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, upc, price, description, model);

        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .contentType(ContentType.JSON)
                .pathParam("id", productID)
                .when()
                .body(productPojo)
                .patch(Path.PRODUCTS + EndPoints.UPDATE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);

    }


    @Step("Deleting Product information with ProductId : {0}")
    public ValidatableResponse deleteProduct(int productID) {

        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .delete(Path.PRODUCTS + EndPoints.DELETE_PRODUCT_BY_ID).then()
                .log().all().statusCode(200);

    }

    @Step("Getting Product information with ProductId : {0}")
    public ValidatableResponse getProductInfoById(int productID) {

        return SerenityRest.given().log().all()
                .pathParam("productID", productID).when().get(Path.PRODUCTS + EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().all().statusCode(404);
    }
}
