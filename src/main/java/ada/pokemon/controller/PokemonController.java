package ada.pokemon.controller;

import ada.pokemon.PokemonApiApplicationConfig;
import ada.pokemon.dto.PokemonDTO;
import ada.pokemon.service.PokemonService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicReference;

@Import(PokemonApiApplicationConfig.class)
@RestController
@RequestMapping("")
public class PokemonController  {

    @Autowired
    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }


    @GetMapping("/ability/{pokemonName}")
    public void getPokemonAbility(@PathVariable String pokemonName) throws IOException {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/ability/" + URLEncoder.encode(pokemonName));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @GetMapping("/pokemon/{pokemonName}")
    public PokemonDTO getPokemon(@PathVariable String pokemonName)  /*throws IOException*/ {
        return pokemonService.getPokemon(pokemonName);
    }

    @GetMapping("/location/{pokemonName}")
    public void getLocation(@PathVariable String pokemonName)  throws IOException {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/location/" + URLEncoder.encode(pokemonName));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @GetMapping("")
    public String fetchingPokemonList(){
        AtomicReference<String> retString = new AtomicReference<>("");
        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon?limit=151");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                if(inputLine != "Response Code : 200") {
                    response.append(inputLine);
                }
            }
            in.close();
            System.out.println(response.toString());
            JSONObject json = new JSONObject(response.toString());
            JSONArray jsonArray = json.getJSONArray("results");
            jsonArray.forEach(
                    v -> {
                        //System.out.println(v);
                        JSONObject obj = new JSONObject(v.toString());
                        retString.set(retString + " " + obj.get("name").toString() + "<br>\n");
                        //System.out.println(retString.get());
                    }
            );
        }catch (IOException e){
            System.out.println("Ocorreu um erro!");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return retString.get();
    }

    private static String getPokemonData(URL url){
        return "url";
    }
}
