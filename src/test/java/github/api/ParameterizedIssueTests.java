package github.api;

import static io.restassured.RestAssured.given;

import io.restassured.http.Header;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import model.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ParameterizedIssueTests {

    @Parameterized.Parameter(0)
    public String topic;
    @Parameterized.Parameter(1)
    public Integer statusCode;

    public String ACCESS_TOKEN = "2a31c0a32a4f4a975cbe8dbd9f4f51d82cb95db9";
    int issuesSize = 0;
    Header header = new Header("Authorization", "token " + ACCESS_TOKEN);
    Header acceptHeader = new Header("Accept", "application/vnd.github.sailor-v-preview+json");

    @Parameterized.Parameters(name = "{index}: lock issue type {0} expect statusCode {1}")
    public static Collection<Object[]> inputData() {

        Object[][] data = new Object[][]{{"off-topic", 204}, {"madeUp-topic", 422}, {"", 422}};
        return Arrays.asList(data);
    }


    @Before
    public void create_Issue_check_size() {
        given().header(header).body("{\"title\": \"Found a bug \"}")

            .when().post(IssueProperties.URL)
            .then().statusCode(201);
        issuesSize = given()
            .header(header)
            .when().get(IssueProperties.URL)
            .then().extract().jsonPath().getList("$").size();
    }


    @Test
    public void put_locks_check_different_topics() {
        String issueNumber = String.valueOf(issuesSize - 1);
        given().header(header)
            .header(acceptHeader)
            .body("{\"lock_reason\": \"" + topic + "\"}")
            .when().put(IssueProperties.URL +"/" +issueNumber + "/lock")
            .then().statusCode(statusCode);
    }


}
