package ada.pokemon.dto;

import java.util.List;

public class PokemonRAW {
    private String name;
    private List<LinksNamesDTO> stats;
    public String getName () {
        return name;
    }
    public List<LinksNamesDTO> getStats () {
        return stats;
    }
}
