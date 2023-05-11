package ada.pokemon.service;

import ada.pokemon.PokemonApiApplicationConfig;
import ada.pokemon.dto.AbilityDTO;
import ada.pokemon.dto.PokemonDTO;
import ada.pokemon.dto.PokemonInterface;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import com.google.gson.Gson;

import static javax.management.Query.in;

@Service
@Import(PokemonApiApplicationConfig.class)
public class PokemonService implements PokemonInterface {

    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";
    private final RestTemplate restTemplate;

    public PokemonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static PokemonDTO pokemonDTO;

    @Override
    public PokemonDTO getPokemon(String pokemonName) {
        String errorMessage;
         try{
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + URLEncoder.encode(pokemonName));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
             BufferedReader in = null;
             if (100 <= con.getResponseCode() && con.getResponseCode() <= 399){
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }else{
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine = "";
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //System.out.println(getFirstLevelKeysAndValues(response.toString()).toString().replace("=", ":"));
            JSONObject jsonObject = new JSONObject(getFirstLevelKeysAndValues(response.toString()).toString().replace("=", ":"));
            //System.out.println(jsonObject.toString());

            Double height = Double.valueOf(jsonObject.getString("height"));
            Double id = Double.valueOf(jsonObject.getString("id"));
            Double weight = Double.valueOf(jsonObject.getString("weight"));

            return PokemonDTO.builder().height(height)
                    .id(id)
                    .name(jsonObject.getString("name"))
                    .location_area_encounters(jsonObject.getString("location_area_encounters"))
                    .weight(weight).build();
        }
        catch (Exception e){
            e.printStackTrace();
            errorMessage = e.getMessage();
        }
        return PokemonDTO.builder()
                .name("ERRO: " + errorMessage)
                .id(0.0)
                .height(null)
                .weight(null)
                .location_area_encounters(null)
                .build(); //pokemonService.getPokemon(pokemonName);
    }

    public static Map<String, Object> getFirstLevelKeysAndValues(String json) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(json, Map.class);
        Map<String, Object> result = new LinkedHashMap<>(); // Use LinkedHashMap para manter a ordem das chaves
        for (String key : map.keySet()) {
            if(key.equals("id") || key.equals("name") || key.equals("height") || key.equals("weight") || key.equals("location_area_encounters")) {
                //if(key.equals("name") || key.equals("location_area_encounters"))
                if(key.getClass() == String.class){
                    result.put('"' + key + '"', '"' + map.get(key).toString() + '"');
                    //System.out.println('"' + key + '"');
                    //System.out.println('"' + map.get(key).toString() + '"');
                }else{
                    result.put('"' + key + '"', map.get(key));
                    //System.out.println('"' + key + '"');
                    //System.out.println(map.get(key).toString());
                }
            }
        }
        return result;
    }
    
}

