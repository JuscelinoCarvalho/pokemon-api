package ada.pokemon.dto;

import ada.pokemon.util.BattleResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class BattleResultDTO {

    private String pokemonName;
    private BattleResult fightResult;

}
