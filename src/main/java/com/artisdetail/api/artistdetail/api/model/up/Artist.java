package com.artisdetail.api.artistdetail.api.model.up;


import com.artisdetail.api.artistdetail.api.model.down.ArtistDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @JsonProperty("MBID")
    private String id;
    private String name;
    private String gender;
    private String country;
    private String disambiguation;



    public static Artist from(ArtistDto todoDto){
        return new Artist(todoDto.getId(), todoDto.getName(), todoDto.getGender(), todoDto.getCountry(), todoDto.getDisambiguation());
    }
}
