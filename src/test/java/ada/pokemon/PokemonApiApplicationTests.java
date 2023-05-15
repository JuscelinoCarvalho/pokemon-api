package ada.pokemon;


import ada.pokemon.controller.PokemonController;
import ada.pokemon.dto.PokemonDTO;
import ada.pokemon.service.PokemonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

@AutoConfigureMockMvc
@SpringBootTest(classes = PokemonApiApplication.class)
@ExtendWith(MockitoExtension.class)
public class PokemonApiApplicationTests {

	@Mock
	private PokemonService pokemonService;

	@InjectMocks
	private PokemonController pokemonController;

	private MockMvc mockMvc;


	@BeforeEach
	public void setup(){
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.pokemonController).build();
	}


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
		mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/newtwo"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError())
				.andDo(MockMvcResultHandlers.print());

	}


	@Test
	public void testBattle() throws Exception {
		String pokemonName2 = "pikachu";
		String pokemonName3 = "arceus";

		mockMvc.perform(MockMvcRequestBuilders.post("/compare/" + pokemonName3 + "/" + pokemonName2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
				.json("[{\"pokemonName\": \"arceus\", \"fightResult\": \"WINNER\"}, { \"pokemonName\": \"pikachu\", \"fightResult\": \"LOOSER\"}]"));
	}

}
