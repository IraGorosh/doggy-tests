package dev.baraboshkin.doggyfrictions.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Participant {
    private String id;
    private String name;

    @JsonCreator
    public Participant(@JsonProperty("Id") String id,
                       @JsonProperty("Name") String name) {
        this.id = id;
        this.name = name;
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
}
