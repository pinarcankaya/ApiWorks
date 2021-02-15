package tests;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class US_06_Countries {


    Response response;
    List<Map<String, Object>> listofCountries ;
    List<Map<String, Object>> listofId ;

    JsonPath json;

    @Test //Status kodunun 200 oldugunu dogrulayiniz
    public void TC01(){
        response=given().
                accept(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                when().
                get(ConfigReader.getProperty("gmb_url"));

        response.
                then().
                assertThat(). //assertThat() kullanirsaniz "Hart Assertion "yapiyorsunuz demektir.
                contentType(ContentType.JSON).
                statusCode(200);

//response.prettyPrint();
//response.prettyPeek(); //
//System.out.println("Status code :"+ response.getStatusCode());
// System.out.println("Content Type: "+ response.getContentType());
//System.out.println(response.getHeaders()); //Headers=basliklar

        json=response.jsonPath(); //asagidaki testler icin olusturdum.


    }

    @Test //Olusturulmus olan country'lerin ID lerinin oldugunu dogrulayiniz
    public void TC02(){
        TC01();
// listofCountries=response.as(ArrayList.class);
// System.out.println("ulke sayisi = "+listofCountries.size()); //958
//
// listofId=response.as(ArrayList.class);
// listofId=json.getList("id");
// System.out.println("id sayisi = " +listofId.size()); //958
//
// Assert.assertTrue(listofCountries.size()==listofId.size());
// Assert.assertEquals(958,listofId.size());
        listofCountries=json.getList("$"); //as methodunun alternatifi olarak kullanilabilir.
        listofCountries=json.getList("name");
        String id=json.getString("name[12]");
        System.out.println(id);



    }
    @Test //name'i " Scottland "olan ulkeleri dogrulayiniz
    public void TC03(){
        TC01();
        listofCountries=response.as(ArrayList.class);
        listofCountries=json.getList("name");
        System.out.println(listofCountries);
        Assert.assertTrue(listofCountries.contains("Scottland"));


        System.out.println("12.ulke : " +listofCountries.get(12));


    }
}
