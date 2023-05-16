package ada.pokemon.mapper;


import ada.pokemon.dto.PokemonFormsDTO;
import ada.pokemon.dto.PokemonDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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



        for(int i=0; i < myArray.length(); ++i){
            JSONObject myObject = myArray.getJSONObject(i);
            retList.add(myObject.getJSONObject("type").get("name").toString());
        }



        return retList;
    }

    public List<String> getStats(JSONObject jsonObject){
        List<String> retList = new ArrayList<>();
        JSONArray myArray = jsonObject.getJSONArray("stats");

        for(int i = 0; i < myArray.length(); ++i){
            JSONObject myObject = myArray.getJSONObject(i).getJSONObject("stat");
            myObject.put("base_stat", myArray.getJSONObject(i).get("base_stat"));
            retList.add(myObject.toString());
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

            JSONArray jsonArray = new JSONArray(response.toString());

            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
               lstRet.add(String.valueOf(jsonObject.getJSONObject("location_area").get("name")));
            }

/*
            for (Object jobj :  new JSONArray(response.toString())) {
                lstRet.add(((JSONObject)jobj).getJSONObject("location_area").get("name").toString());
            }
*/

            return lstRet;
        } catch (Exception e) {
            e.printStackTrace();
            lstRet.add(e.getMessage());
            return lstRet;
        }
    }

    public PokemonFormsDTO ResponseToPokemonFormsDTO(JSONObject jsonObject){
        String errorMessage = "";
        try{

            JSONArray myArray = jsonObject.getJSONArray("forms");
            List<String> lstFormName = new ArrayList<>();

            for(int i = 0; i < myArray.length(); ++i){
                JSONObject myObject = myArray.getJSONObject(i);
                lstFormName.add(myObject.get("name").toString());
            }

            return PokemonFormsDTO.builder()
                    .forms(lstFormName)
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
            errorMessage = e.getMessage();
            List<String> retList = new ArrayList<>();
            retList.add(errorMessage);

            return PokemonFormsDTO.builder()
                    .forms(retList)
                    .build();
        }

    }


}
