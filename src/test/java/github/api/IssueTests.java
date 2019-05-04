package github.api;

import static github.api.IssueProperties.AUTHORIZATION_HEADER;
import static github.api.IssueProperties.SCHEMA;
import static github.api.IssueProperties.URL;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import model.Issue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IssueTests {

    @Before
    public void setup_create_issue() {
        given().header(AUTHORIZATION_HEADER).body("{\"title\": \"Found a bug \"}")
            .when().post(URL)
        .then().statusCode(201);
    }

    @Test
    public void create_issue_with_invalid_body_returns_parsing_error() {
        given().header(AUTHORIZATION_HEADER).body("{\"titles\": \"Found a bug \"}")
            .when().post(URL)
            .then().statusCode(422);
    }


    @Test
    public void patch_issue() {
        given().header(AUTHORIZATION_HEADER)
            .body(new File("src/test/resources/patch_issue_body.json"))
            .when().patch(URL+"/1")
            .then().statusCode(200)
            .and().body("title",equalTo("Modified title"))
            .and().body("body",equalTo("I'm having a problem with this."));
    }

    @Test
    public void get_single_issue(){
        given().header(AUTHORIZATION_HEADER)
            .when().get(URL +"/1")
            .then()
            .assertThat().body("number",equalTo(1));

    }

    @Test
    public void check_issues_validate_existing_schema(){
        given().header(AUTHORIZATION_HEADER)
            .when().get(URL +"/1")
            .then().assertThat()
            .body(matchesJsonSchemaInClasspath(SCHEMA));

    }
}
