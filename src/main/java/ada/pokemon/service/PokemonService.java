package ada.pokemon.service;

import ada.pokemon.PokemonApiApplicationConfig;
import ada.pokemon.dto.PokemonDTO;
import ada.pokemon.dto.PokemonInterface;
import ada.pokemon.mapper.MapperPokemon;
import org.json.JSONObject;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import com.google.gson.Gson;

@Service
@Import(PokemonApiApplicationConfig.class)
public class PokemonService implements PokemonInterface {

    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";
    private final RestTemplate restTemplate;

    public PokemonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static PokemonDTO pokemonDTO;

    public static StringBuffer getUrlReturn(String pUrl) {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/" + URLEncoder.encode(pUrl));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader in = null;
            if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine = "";
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return StringBuffer.class.cast(e.getMessage());
        }
    }

    @Override
    public PokemonDTO getPokemon(String pokemonName) {
        String url = "pokemon/" + pokemonName;
        String response = String.valueOf(getUrlReturn(url));
        MapperPokemon mapperPokemon = new MapperPokemon();
        return mapperPokemon.ResponseToPokemnonDTO(new JSONObject(response));
    }

    public static Map<String, Object> getFirstLevelKeysAndValues(String json) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(json, Map.class);
        Map<String, Object> result = new LinkedHashMap<>(); // Use LinkedHashMap para manter a ordem das chaves
        for (String key : map.keySet()) {
            if (key.equals("id") || key.equals("name") || key.equals("height") ||
                    key.equals("weight") || key.equals("location_area_encounters") ||
                    key.equals("types") || key.equals("stats")) {

                if (key.getClass() == String.class) {
                    result.put('"' + key + '"', '"' + map.get(key).toString() + '"');
                    //System.out.println('"' + key + '"');
                    //System.out.println('"' + map.get(key).toString() + '"');
                } else {
                    result.put('"' + key + '"', map.get(key));
                    //System.out.println('"' + key + '"');
                    //System.out.println(map.get(key).toString());
                }
            }
        }
        return result;
    }
}
