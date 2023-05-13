package ada.pokemon.mapper;


import ada.pokemon.service.PokemonService;
import ada.pokemon.dto.PokemonDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MapperPokemon {

    public PokemonDTO ResponseToPokemnonDTO(JSONObject jsonObject){
        String errorMessage = "";

        JSONObject stringObject = new JSONObject((jsonObject.toString()).toString().replace("=", ":"));
        try{
            Double height = stringObject.getDouble("height");
            Double id = stringObject.getDouble("id");
            Double weight = stringObject.getDouble("weight");
            List<String> location_area_encounters = getUrlLocation(new URL(String.valueOf(stringObject.get("location_area_encounters"))));;
            List<String> types = getTypes(jsonObject);
            List<String> stats = getStats(jsonObject);

            return PokemonDTO.builder().height(height)
                    .id(id)
                    .name(jsonObject.getString("name"))
                    .weight(weight)
                    .height(height)
                    .location_area_encounters(location_area_encounters)
                    .stats(stats)
                    .types(types)
                    .build();
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
                .stats(null)
                .types(null)
                .location_area_encounters(null)
                .location_area_encounters(null)
                .build(); //pokemonService.getPokemon(pokemonName);
    }

    public List<String> getTypes(JSONObject jsonObject) {
        List<String> retList = new ArrayList<>();
        JSONArray myArray = jsonObject.getJSONArray("types");
        JSONObject myObject = new JSONObject();
        myObject.put("types", myArray);

        for (Object nome : myObject.getJSONArray("types")) {
            retList.add(String.valueOf(((JSONObject) nome).getJSONObject("type").get("name")));
        }
        return retList;
    }

    public List<String> getStats(JSONObject jsonObject){
        List<String> retList = new ArrayList<>();
        JSONArray myArray = jsonObject.getJSONArray("stats");
        JSONObject myObject = new JSONObject();
        myObject.put("stats", myArray);

        for (Object nome : myObject.getJSONArray("stats")) {
            retList.add(String.valueOf(((JSONObject) nome).getJSONObject("stat").get("name")));
        }
        return retList;
    }

    public List<String> getUrlLocation(URL url){
        List<String> lstRet = new ArrayList<>();
        try {
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

            for ( Object jobj :  new JSONArray(response.toString())  ) {
                lstRet.add(((JSONObject)jobj).getJSONObject("location_area").get("name").toString());
            }


            return lstRet;
        } catch (Exception e) {
            e.printStackTrace();
            lstRet.add(e.getMessage());
            return lstRet;
        }
    }

}
