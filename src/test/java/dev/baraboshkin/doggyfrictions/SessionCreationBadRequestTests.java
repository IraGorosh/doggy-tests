package dev.baraboshkin.doggyfrictions;

import dev.baraboshkin.doggyfrictions.client.SessionClient;
import dev.baraboshkin.doggyfrictions.model.SessionGenerator;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static dev.baraboshkin.doggyfrictions.model.SessionGenerator.getRandomName;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SessionCreationBadRequestTests {
    private SessionClient sessionClient;
    private String sessionName;

    private static final String ERROR_TITLE = "One or more validation errors occurred.";
    private static final String ID_ERROR = "The Id field is required.";
    private static final String SESSION_NAME_ERROR = "Please specify session name";
    private static final String NAME_ERROR = "The Name field is required.";

    @Before
    public void setUp() {
        sessionClient = new SessionClient();
        sessionName = getRandomName();
    }

    @Test
    public void userCannotCreateSessionWithoutId() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        session.remove("Id");
        checkResponseParametersForId(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithoutName() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        session.remove("Name");
        checkResponseParametersForName(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithoutParticipantId() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        session.remove("Participants[0][Id]");
        checkResponseParametersForParticipantId(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithoutParticipantName() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        session.remove("Participants[0][Name]");
        checkResponseParametersForParticipantName(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithoutIdOfSecondParticipant() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 2);
        session.remove("Participants[1][Id]");
        checkResponseParametersForParticipantId(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithoutNameOfSecondParticipant() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 2);
        session.remove("Participants[1][Name]");
        checkResponseParametersForParticipantName(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithEmptyId() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        session.put("Id", "");
        checkResponseParametersForId(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithEmptyName() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        session.put("Name", "");
        checkResponseParametersForName(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithEmptyParticipantId() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        session.put("Participants[0][Id]", "");
        checkResponseParametersForParticipantId(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithEmptyParticipantName() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 1);
        session.put("Participants[0][Name]", "");
        checkResponseParametersForParticipantName(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithEmptyIdOfSecondParticipant() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 2);
        session.put("Participants[1][Id]", "");
        checkResponseParametersForParticipantId(sessionClient, session);
    }

    @Test
    public void userCannotCreateSessionWithEmptyNameOfSecondParticipant() {
        HashMap<String, String> session = SessionGenerator.getHashMapSession(sessionName, 2);
        session.put("Participants[1][Name]", "");
        checkResponseParametersForParticipantName(sessionClient, session);
    }

    private static JsonPath getResponse(SessionClient sessionClient,HashMap<String, String> session) {
        return sessionClient
                .create(session)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .jsonPath();
    }

    private static void checkStandardResponseParameters(JsonPath response){
        assertThat(response.getString("title"), is(ERROR_TITLE));
        assertThat(response.getString("status"), is("400"));
        assertThat(response.getString("traceId"), notNullValue());
    }

    private static void checkResponseParametersForId(SessionClient sessionClient,HashMap<String, String> session) {
        JsonPath response = getResponse(sessionClient, session);
        checkStandardResponseParameters(response);
        assertThat(response.getString("errors.Id[0]"), is(ID_ERROR));
    }

    private static void checkResponseParametersForName(SessionClient sessionClient,HashMap<String, String> session) {
        JsonPath response = getResponse(sessionClient, session);
        checkStandardResponseParameters(response);
        assertThat(response.getString("errors.Name[0]"), is(SESSION_NAME_ERROR));
    }

    private static void checkResponseParametersForParticipantId(SessionClient sessionClient,HashMap<String, String> session) {
        JsonPath response = getResponse(sessionClient, session);
        checkStandardResponseParameters(response);
        assertThat(response.getString("errors."), containsString(ID_ERROR));
    }

    private static void checkResponseParametersForParticipantName(SessionClient sessionClient,HashMap<String, String> session) {
        JsonPath response = getResponse(sessionClient, session);
        checkStandardResponseParameters(response);
        assertThat(response.getString("errors."), containsString(NAME_ERROR));
    }
}
