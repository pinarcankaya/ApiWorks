package tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import utilities.ConfigReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class US_04_Registiration {
//   Scenario: @TC_004_01 Musteri adreslerini okuma
//      Given "https://www.gmibank.com/api/tp-account-registrations" API endpoint'i kullanin
//      And Olusturulmus tum musteri adreslerini okuyun
//      And Ikinci musterinin SSN numarasinin "765-56-4544" oldugunu dogrulayin
//      And Olusturulmus musterilerden besinci musterinin  email adresinin "kaska@gmail.com" oldugunu dogrulayiniz
//      Then 2201 id numarali musterinin adresini "Istanbul" olarak guncelleyiniz

    Response response;
    JsonPath json;
    List<Map<String, Object>> allAddressData, allCustomerData;
    Response responceBeforePatch;
    Response responceAfterPatch;
    Response responceAfterPost;


    public void api_endpoint_i_kullanin(String endpoint) {
        response = given().
                auth().oauth2(ConfigReader.getProperty("token")).
                accept(ContentType.JSON).
                when().
                get(endpoint);

        response.then().assertThat().statusCode(200);

    }

    @Given("Olusturulmus tum musteri adreslerini okuyun")
    public void olusturulmus_tum_musteri_adreslerini_okuyun() {
        json = response.jsonPath();
        allAddressData = json.getList("address");
//System.out.println(allAddressData);


    }


    @Given("Ikinci musterinin SSN numarasinin {string} oldugunu dogrulayin")
    public void ikinci_musterinin_SSN_numarasinin_oldugunu_dogrulayin(String ssn) {
        List<String> list = json.getList("ssn");
        String secondCustomerSSN = list.get(1);
        String jsonSSN = json.getString("ssn[1]");
        Assert.assertEquals(ssn, secondCustomerSSN);
        Assert.assertEquals(ssn, jsonSSN);
        System.out.println(secondCustomerSSN);
        System.out.println(jsonSSN);


    }

    @Given("Olusturulmus musterilerden besinci musterinin email adresinin {string} oldugunu dogrulayiniz")
    public void olusturulmus_musterilerden_besinci_musterinin_email_adresinin_oldugunu_dogrulayiniz(String email) {
        allCustomerData = json.getList("$");
        String fifthCustomerEmail = allCustomerData.get(4).get("email").toString();
        Assert.assertEquals(email, fifthCustomerEmail);
//Assert.assertEquals(email,allCustomerData.get(4).get("email").toString());


    }

    @Then("{int} id numarali musterinin adresini {string} olarak guncelleyiniz")
    public void id_numarali_musterininadresini_olarak_guncelleyiniz(Integer int1, String Istanbul) {
        responceBeforePatch = given().
                auth().oauth2(ConfigReader.getProperty("token")).
                accept(ContentType.JSON).
                when().
                get("https://www.gmibank.com/api/tp-account-registrations/2201");

// responceBeforePatch.prettyPrint();
//
//// JSONObject jsonObject = new JSONObject();
//// jsonObject.put("address","Istanbul");
////
//// responseAfterPatch = given().
//// auth().oauth2(ConfigurationReader.getProperty("token")).
//// contentType(ContentType.JSON).
//// body(jsonObject.toString()).
//// when().
//// patch("/2201");
////
//// responseAfterPatch.prettyPrint();
//
        String postbody = "{\n" +
                " \"ssn\": \"158-65-4575\",\n" +
                " \"firstName\": \"ali\",\n" +
                " \"lastName\": \"veli\",\n" +
                " \"address\": \"Istanbul\",\n" +
                " \"mobilePhoneNumber\": \"850-311-15312\",\n" +
                " \"userId\": 2151,\n" +
                " \"userName\": \"ali\",\n" +
                " \"email\": \"abc@gmail.com\"\n" +
                "}\n";

// responseAfterPost = given().
// auth().oauth2(ConfigurationReader.getProperty("token")).
// contentType(ContentType.JSON).
// body(postbody).
// when().
// post("https://www.gmibank.com/api/tp-account-registrations");
//
// responseAfterPost.prettyPrint();
// //**********************************************************//

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("ssn", "158-65-3175");
        postMap.put("firstName", "Ayse");
        postMap.put("lastName", "Kaya");
        postMap.put("address", "Ankara");
        postMap.put("mobilePhoneNumber", "367-333-11812");
        postMap.put("userId", "2151");
        postMap.put("userName", "piny");
        postMap.put("email", "ptyr@gmail.com");

        responceAfterPost = given().
                auth().oauth2(ConfigReader.getProperty("token")).
                contentType(ContentType.JSON).
                body(postMap).
                when().
                post("https://www.gmibank.com/api/tp-account-registrations");

        responceAfterPost.prettyPrint();
    }
}