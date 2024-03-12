package dev.baraboshkin.doggyfrictions;

import dev.baraboshkin.doggyfrictions.client.SessionClient;
import dev.baraboshkin.doggyfrictions.model.SessionGenerator;
import io.restassured.path.json.JsonPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static dev.baraboshkin.doggyfrictions.model.SessionGenerator.getRandomName;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetSessionTests {
    private SessionClient sessionClient;
    private JsonPath sessionCreationResponse;
    private String sessionName;

    @Before
    public void setUp() {
        sessionClient = new SessionClient();
        sessionName = getRandomName();
    }

    @After
    public void deleteSession() {
        sessionClient
                .delete(sessionCreationResponse.getString("Id"))
                .assertThat()
                .statusCode(SC_OK);
    }

    @Test
    public void userCanGetAllSessions() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        sessionCreationResponse = sessionClient
                .create(session)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .jsonPath();

        JsonPath getSessionResponse = sessionClient
                .getAllSessions()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .jsonPath();

        assertThat(getSessionResponse.get().toString(), containsString(sessionCreationResponse.getString("Id")));
    }
}
