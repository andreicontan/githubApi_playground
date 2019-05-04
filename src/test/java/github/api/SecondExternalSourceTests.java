package github.api;

import static github.api.IssueProperties.URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.Header;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.Test;

public class SecondExternalSourceTests {

    public String ACCESS_TOKEN = "2a31c0a32a4f4a975cbe8dbd9f4f51d82cb95db9";

    Header header = new Header("Authorization", "token " + ACCESS_TOKEN);

    @Test
    public void create_issue_from_JSON_external_source() throws FileNotFoundException {
        given().header(header).body(new File("src/test/resources/issue_valid_body.json"))
            .when().post(URL)
            .then().statusCode(201)
            .and().assertThat().body("title", equalTo("Found a bug"));
    }

}
