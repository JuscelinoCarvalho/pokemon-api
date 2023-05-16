package ada.pokemon.service;

import ada.pokemon.PokemonApiApplicationConfig;
import ada.pokemon.dto.BattleResultDTO;
import ada.pokemon.dto.PokemonDTO;
import ada.pokemon.dto.PokemonFormsDTO;
import ada.pokemon.mapper.MapperPokemon;
import ada.pokemon.util.BattleResult;
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
public class PokemonService  {

    public PokemonService(RestTemplate restTemplate) {
    }

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

    public static PokemonDTO getPokemon(String pokemonName) {
        try{
            String url = "pokemon/" + pokemonName;
            String response = String.valueOf(getUrlReturn(url));
            MapperPokemon mapperPokemon = new MapperPokemon();
            PokemonDTO pokemonDTO = mapperPokemon.ResponseToPokemnonDTO(new JSONObject(response));
            List<String> statNames = new ArrayList<>();
            final Integer[] sumOfStats = {0};

            pokemonDTO.getStats().forEach(
                    poke -> {
                        JSONObject jsonObject = new JSONObject(poke);
                        statNames.add(jsonObject.get("name").toString());
                        sumOfStats[0] = sumOfStats[0] + jsonObject.getInt("base_stat");
                    }
            );

            pokemonDTO.setStats(statNames);
            pokemonDTO.setSumOfStats(sumOfStats[0]);

            return pokemonDTO ;
        }catch (Exception ex){
            PokemonDTO pokemonDTO = PokemonDTO.builder()
                    .id(-1)
                    .name(ex.getMessage())
                    .build();
            return pokemonDTO;
        }

    }

    public static PokemonFormsDTO getPokemonForms(String pokemonName){
        String url = "pokemon/" + pokemonName;
        String response = String.valueOf(getUrlReturn(url));
        MapperPokemon mapperPokemon = new MapperPokemon();
        return mapperPokemon.ResponseToPokemonFormsDTO(new JSONObject(response));
    }


    public BattleResultDTO pokemonBattleService(String challengerPokemonName, String challengedPokemonName){

        Integer challengerResult = getPokemon(challengerPokemonName).getSumOfStats();
        Integer challengedResult = getPokemon(challengedPokemonName).getSumOfStats();
        BattleResultDTO result;

        if (challengerResult > challengedResult) {
            result = BattleResultDTO.builder().pokemonName(challengerPokemonName).fightResult(BattleResult.WINNER).build();
        } else if(challengerResult < challengedResult){
            result = BattleResultDTO.builder().pokemonName(challengedPokemonName).fightResult(BattleResult.WINNER).build();
        }else{
            result = BattleResultDTO.builder().pokemonName(challengerPokemonName).fightResult(BattleResult.DRAW).build();
        }

        return result;
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
