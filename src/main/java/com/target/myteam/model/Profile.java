package com.target.myteam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Profile {

    private String gender;
    private Float experience;
    private List<Education> education;
    private List<ExternalProfileLinks> externalProfileLinks;
    private List<String> traits;

}
