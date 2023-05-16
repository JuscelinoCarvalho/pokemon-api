package ada.pokemon;


import ada.pokemon.controller.PokemonController;
import ada.pokemon.dto.BattleResultDTO;
import ada.pokemon.dto.PokemonDTO;
import ada.pokemon.service.PokemonService;
import ada.pokemon.util.BattleResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
@SpringBootTest(classes = PokemonApiApplication.class)
public class PokemonApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetPokemonDetails1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/charmander")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetPokemonDetails2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/arceus")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testGetPokemonDetails3() throws Exception {
		PokemonDTO pokemonDTO = PokemonDTO.builder()
						.id(-1).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/newtwo"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(pokemonDTO.getId().equals(-1))
				.andDo(MockMvcResultHandlers.print());

	}


	@Test
	public void testBattle() throws Exception {

		BattleResultDTO battleResult =  BattleResultDTO.builder()
			.pokemonName("arceus")
			.fightResult(BattleResult.WINNER)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/compare/pikachu/arceus"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
				.json(asJsonString(battleResult)));
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
