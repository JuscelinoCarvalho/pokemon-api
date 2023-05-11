package ada.pokemon.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokeStatDTO {
    private String name;
    private Integer value;

    public PokeStatDTO(){}
}
