package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertTrue;

public class US_02_Countries {

    Response response;
    JsonPath json;
    protected RequestSpecification spec02;

    @BeforeTest
    public void tc00(){
        spec02 = new RequestSpecBuilder().
                setBaseUri("https://www.gmibank.com/api/tp-countries").
                build();

        response = given().
                accept(ContentType.JSON).
                spec(spec02).
                auth().oauth2(ConfigReader.getProperty("token1")).
                when().
                get();

// response.prettyPrint();
        response.
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK);



    }
    @Test
    public void tc01() {

//-- name'i "Russland" olan 2 sehir oldugunu dogrulayiniz
//System.out.println(response.prettyPrint());
        json= response.jsonPath();
        List<String> nameList = json.getList("name");
        System.out.println(nameList);
        int count=0;
        for (int i=0; i<nameList.size();i++){
            System.out.println(nameList.get(i));
            String get = nameList.get(i);
            if(get.equals("Russland")){
                count++;
            }else{
                break;
            }

        }
        System.out.println("count= "+count);
        Assert.assertTrue(count==2);



    }
    @Test
    public void tc02() {


        json=response.jsonPath();

        List<String> statesList = json.getList("states");

        for (String x: statesList){
            Assert.assertEquals(x,null);
        }


    }

    @Test
    public void tc03(){

//---3. soru:
//id'si 24000'den kucuk olan ulkelerin icinde "Chekoslavaikia" oldugunu dogrulayiniz



        json= response.jsonPath();

        List<String> nameList = json.getList("findAll{Integer.valueOf(it.id)<24000}.name");
        System.out.println(nameList);
        assertTrue(nameList.contains("Chekoslavaikia"));


    }
}