package dev.baraboshkin.doggyfrictions.model;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;

public class SessionGenerator {

    public static String getRandomName() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static HashMap<String, String> getHashMapSession(String sessionName, int numberOfParticipants) {
        HashMap<String, String> session = new HashMap<>();
        session.put("Id", "0");
        session.put("Name", sessionName);
        for (int i = 0; i < numberOfParticipants; i++) {
            session.put(returnFieldName(i, "Id"), "0");
            session.put(returnFieldName(i, "Name"), getRandomName());
        }
        return session;
    }

    public static String returnFieldName(int participantCount, String idOrName) {
            return "Participants[" + participantCount + "][" + idOrName + "]";
    }
}
