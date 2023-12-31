package com.artisdetail.api.artistdetail.api.model.down;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ArtistDto {
    private String id;
    private String name;
    private String gender;
    private String country;
    private String disambiguation;
    @JsonProperty("release-groups")
    private List<ReleaseGroups> releaseGroups;

}
