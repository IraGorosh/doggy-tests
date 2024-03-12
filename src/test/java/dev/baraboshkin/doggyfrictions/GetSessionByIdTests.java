package dev.baraboshkin.doggyfrictions;

import dev.baraboshkin.doggyfrictions.client.SessionClient;
import dev.baraboshkin.doggyfrictions.model.Session;
import dev.baraboshkin.doggyfrictions.model.SessionGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static dev.baraboshkin.doggyfrictions.model.SessionGenerator.getRandomName;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetSessionByIdTests {
    private SessionClient sessionClient;
    private Session response;
    private String sessionName;

    @Before
    public void setUp() {
        sessionClient = new SessionClient();
        sessionName = getRandomName();
    }

    @After
    public void deleteSession() {
        sessionClient
                .delete(response.getId())
                .assertThat()
                .statusCode(SC_OK);
    }

    @Test
    public void userCanGetSessionInformation() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        response = sessionClient
                .create(session)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(Session.class);

        Session getSessionResponse = sessionClient
                .get(response.getId())
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(Session.class);

        assertThat(getSessionResponse.getId(), is(response.getId()));
        assertThat(getSessionResponse.getName(), is(sessionName));
        //assertThat(getSessionResponse.getParticipants().get(0).getId(), is(response.getParticipants().get(0).getId()));
        assertThat(getSessionResponse.getParticipants().get(0).getName(), is(session.get("Participants[0][Name]")));
    }
}
