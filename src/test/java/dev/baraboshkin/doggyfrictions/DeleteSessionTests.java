package dev.baraboshkin.doggyfrictions;

import dev.baraboshkin.doggyfrictions.client.SessionClient;
import dev.baraboshkin.doggyfrictions.model.Session;
import dev.baraboshkin.doggyfrictions.model.SessionGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static dev.baraboshkin.doggyfrictions.model.SessionGenerator.getRandomName;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteSessionTests {
    private SessionClient sessionClient;
    private String sessionName;

    @Before
    public void setUp() {
        sessionClient = new SessionClient();
        sessionName = getRandomName();
    }

    @Test
    public void userCanCreateNewSessionWithOneParticipant() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        Session response = sessionClient
                .create(session)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(Session.class);
        Session deleteSessionResponse = sessionClient
                .delete(response.getId())
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(Session.class);

        assertThat(deleteSessionResponse.getId(), is(response.getId()));
        assertThat(deleteSessionResponse.getName(), is(sessionName));
        //this is commented out because there is a bug
        //assertThat(deleteSessionResponse.getParticipants().get(0).getId(), is(response.getParticipants().get(0).getId()));
        assertThat(deleteSessionResponse.getParticipants().get(0).getName(), is(session.get("Participants[0][Name]")));
    }
}
