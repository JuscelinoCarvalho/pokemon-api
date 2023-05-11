package ada.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class PokemonDTO {

    @JsonProperty("height")
    private Double height;

    @JsonProperty("id")
    private Double id;

    @JsonProperty("location_area_encounters")
    private String location_area_encounters;

    @JsonProperty("name")
    private String name;

    @JsonProperty("weight")
    private Double weight;

}
