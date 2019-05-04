package github.api;

import static github.api.IssueProperties.AUTHORIZATION_HEADER;
import static github.api.IssueProperties.URL;
import static io.restassured.RestAssured.given;

import io.restassured.http.Header;
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

    private int issuesSize = 0;
    private Header acceptHeader = new Header("Accept", "application/vnd.github.sailor-v-preview+json");

    @Parameterized.Parameters(name = "{index}: lock issue type {0} expect statusCode {1}")
    public static Collection<Object[]> inputData() {

        Object[][] data = new Object[][]{{"off-topic", 204}, {"madeUp-topic", 422}, {"", 422}};
        return Arrays.asList(data);
    }


    @Before
    public void create_Issue_check_size() {
        given().header(AUTHORIZATION_HEADER).body("{\"title\": \"Found a bug \"}")

            .when().post(URL)
            .then().statusCode(201);
        issuesSize = Arrays.asList(given()
            .header(AUTHORIZATION_HEADER)
            .when().get(URL)
            .then().extract().body().as(Issue[].class)).size();
    }


    @Test
    public void put_locks_check_different_topics() {
        String issueNumber = String.valueOf(issuesSize - 1);
        given().header(AUTHORIZATION_HEADER)
            .header(acceptHeader)
            .body("{\"lock_reason\": \"" + topic + "\"}")
            .when().put(URL + issueNumber + "/lock")
            .then().statusCode(statusCode);
    }


}
