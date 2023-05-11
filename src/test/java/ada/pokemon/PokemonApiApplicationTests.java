package ada.pokemon;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:PokemonApiApplicationContext.xml" })
class PokemonApiApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {
	}


	String pokemonName1 = "charmander";
	String PokemonName2 = "pikachu";
	String pokemonName3 = "mewtwo";

	@Test
	public void getPokemonDetails(){
		//String result = restTemplate.getForObject("/pokemon/pikachu");
	}

}
