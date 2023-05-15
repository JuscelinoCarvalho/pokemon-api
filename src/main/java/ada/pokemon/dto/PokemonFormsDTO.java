package ada.pokemon.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@Builder
public class PokemonFormsDTO {

    private List<String> forms;

}
