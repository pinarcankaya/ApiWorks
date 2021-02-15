package tests;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import pojos.CountryPojo;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import utilities.ConfigReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import java.util.List;

public class US_05_Customers {

    Response response;
    JsonPath json;

    @Test
    public void countries() throws IOException {
        response = given().
                accept(ContentType.JSON).
                auth().oauth2(ConfigReader.getProperty("token")).
                when().
                get(ConfigReader.getProperty("countryUrl"));
        json = response.jsonPath();
// response.prettyPrint();

        ObjectMapper objectMapper = new ObjectMapper();
        CountryPojo[] allCountryPojoArray = objectMapper.readValue(response.asString(), CountryPojo[].class);

//1.kisinin id'sini getir
        int id = allCountryPojoArray[0].getId(); //pojo ile cozum
        System.out.println(id);

        int idJson = json.getInt("id[0]"); //json ile cozum
        System.out.println(idJson);

        String namePojo = allCountryPojoArray[5].getName();//pojo ile cozum
        System.out.println(namePojo);

        String nameJson = json.getString("name[5]");
        System.out.println(nameJson);

        int idPojo1 = allCountryPojoArray[allCountryPojoArray.length - 1].getId();//pojo ile cozum
        System.out.println(idPojo1);

        int idJson1 = json.getInt("id[-1]");
        System.out.println(idJson);

        for (CountryPojo w : allCountryPojoArray) { //pojo ile cozum
// System.out.println(w.getName());
        }

        List<String> name = json.getList("name");
// System.out.println(name);

        int count = 0;
        for (CountryPojo w : allCountryPojoArray) { //pojo ile cozum
            if (w.getId() < 30000) {
// System.out.println(w.getId());
                count++;
            }
        }
        System.out.println(count);

        int count1 = 0;
        for (CountryPojo z : allCountryPojoArray) { //pojo ile cozum
            System.out.println(z.getName());
            count1++;
        }
        System.out.println(count1);
    }
}