package dev.baraboshkin.doggyfrictions.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Session {
    private String id;
    private String name;
    private List<Participant> participants;

    @JsonCreator
    public Session(@JsonProperty("Id") String id,
                   @JsonProperty("Name") String name,
                   @JsonProperty("Participants") List<Participant> participants) {
        this.id = id;
        this.name = name;
        this.participants = participants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
