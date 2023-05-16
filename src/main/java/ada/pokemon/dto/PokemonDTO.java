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

    private Double id;
    private String name;
    private Double weight;
    private Double height;
    private List<String> stats;
    private List<String> types;
    private List<String> location_area_encounters;
    private Integer sumOfStats;
}
