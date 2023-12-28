package com.artisdetail.api.artistdetail.api.model.up;

import com.artisdetail.api.artistdetail.api.model.down.Album;
import com.artisdetail.api.artistdetail.api.model.down.ArtistDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.PrimitiveIterator;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDetail {
    @JsonProperty("mbid")
    private String id;
    private String name;
    private String gender;
    private String country;
    private String disambiguation;
    private List<Album> albums;

   public static ArtistDetail from(ArtistDto artist, List<Album> albums){
       return new ArtistDetail(artist.getId(), artist.getName(), artist.getGender(), artist.getCountry(), artist.getDisambiguation(), albums);
   }


}
