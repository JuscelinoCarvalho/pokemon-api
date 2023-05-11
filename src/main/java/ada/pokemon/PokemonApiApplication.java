package ada.pokemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class PokemonApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(PokemonApiApplication.class, args);
	}
}
