package tests;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import static io.restassured.RestAssured.given;


public class US_03_States {
    Response response;
    private RequestSpecification spec01;
    @BeforeTest
    public void spec(){
        spec01 = new RequestSpecBuilder().
                setBaseUri("https://www.gmibank.com/api/tp-states").
                build();

    }

    @Test
    public void tc01() {
//1071 adet state oldugunu dogrulayiniz
        response = given().
                accept(ContentType.JSON).
                spec(spec01).
                auth().oauth2(ConfigReader.getProperty("token1")).
                when().
                get();

        response.prettyPrint();

        response.then().assertThat().statusCode(HttpStatus.SC_OK).body("id",hasSize(1071));

    }
    @Test
    public void tc02() {
//name lerin icinde "Koblenz", "Alsace", "Virginia" oldugunu dogrulayiniz.
        response = given().
                accept(ContentType.JSON).
                spec(spec01).
                auth().oauth2(ConfigReader.getProperty("token1")).
                when().
                get();

// response.prettyPrint();
        response.
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body("name",hasItems("Koblenz","Alsace","Virginia"));

    }

    @Test
    public void tc03() {

//id si "65960" olan state in name'i "nereliyim" ve tpcountry si null dür, dogrulayiniz.(spec kulanabilirsiniz)
        response = given().
                spec(spec01).
                auth().oauth2(ConfigReader.getProperty("token1")).
                when().
                get("/65960");
        response.prettyPrint();
        response.then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body(
                        "name",is("nereliyim"),
                        "tpcountry",equalTo(null));



    }


}