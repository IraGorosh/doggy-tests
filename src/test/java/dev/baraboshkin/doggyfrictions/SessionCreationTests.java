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
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SessionCreationTests {
    private SessionClient sessionClient;
    private String sessionName;
    private Session response;

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
    public void userCanCreateNewSessionWithOneParticipant() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        response = sessionClient
                .create(session)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(Session.class);

        assertThat(response.getId(), notNullValue());
        assertThat(response.getName(), is(sessionName));
        assertThat(response.getParticipants().get(0).getId(), notNullValue());
        assertThat(response.getParticipants().get(0).getName(), is(session.get("Participants[0][Name]")));
    }

    @Test
    public void userCanCreateNewSessionWithTwoParticipants() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 2);
        response = sessionClient
                .create(session)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(Session.class);

        assertThat(response.getId(), notNullValue());
        assertThat(response.getName(), is(sessionName));
        assertThat(response.getParticipants().get(0).getId(), notNullValue());
        assertThat(response.getParticipants().get(0).getName(), is(session.get("Participants[0][Name]")));
        assertThat(response.getParticipants().get(1).getId(), notNullValue());
        assertThat(response.getParticipants().get(1).getName(), is(session.get("Participants[1][Name]")));
    }

    @Test
    public void userCanCreateDoubleSession() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        response = sessionClient
                .create(session)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(Session.class);
        Session responseFromDuplicateRequest = sessionClient
                .create(session)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .as(Session.class);

        assertThat(response.getId(), not(responseFromDuplicateRequest.getId()));
        assertThat(response.getName(), is(responseFromDuplicateRequest.getName()));
        assertThat(response.getParticipants().get(0).getId(), not(responseFromDuplicateRequest.getParticipants().get(0).getId()));
        assertThat(response.getParticipants().get(0).getName(), is(responseFromDuplicateRequest.getParticipants().get(0).getName()));
    }
}
