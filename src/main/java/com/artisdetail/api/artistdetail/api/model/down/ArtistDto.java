package com.artisdetail.api.artistdetail.api.model.down;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
}
