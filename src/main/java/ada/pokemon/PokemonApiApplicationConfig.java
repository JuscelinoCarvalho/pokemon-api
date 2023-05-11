package ada.pokemon;

import ada.pokemon.mapper.MapperPokemon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.client.ClientBuilder;

@Configuration
public class PokemonApiApplicationConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


/*
    @Bean
    public ClientBuilder clientBuilder() {
        return ClientBuilder.newBuilder();
    }

    @Bean
    public MapperPokemon mapperPokemon(){
        return new MapperPokemon();
    }
    */
}
