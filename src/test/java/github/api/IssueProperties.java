package github.api;

import io.restassured.http.Header;

public interface IssueProperties {

    String URL = "https://api.github.com/repos/andreicontan/Selenium3Days/issues";

    String ACCESS_TOKEN = "2a31c0a32a4f4a975cbe8dbd9f4f51d82cb95db9";

    Header AUTHORIZATION_HEADER = new Header("Authorization", "token " + ACCESS_TOKEN);

    String SCHEMA = "issue_schema.json";
}
