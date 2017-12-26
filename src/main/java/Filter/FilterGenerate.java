package Filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FilterGenerate {
    Bson filterIn;

    public FilterGenerate(String strFiltro) {
        String json = "[{'fieldName':'name', 'operator':'$in', 'valueInList':'[Barcelona, Valencia]'}]";
        //ObjectMapper mapper = new ObjectMapper();
        //Map<String, Object> map = new HashMap<String, Object>();
        //String json = "{\"name\":\"Barcelona\"}";
        //JSONArray array = (JSONArray) JSON.parse(json);

        try {
            JSONParser jsonParser=new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(json);
            System.out.println("JSON ARRAY FILtER: "+jsonArray);
            for (Object object : jsonArray){
                JSONObject jsonObject = (JSONObject) object;
                Set<String> keys = jsonObject.keySet();
                for (String key:keys){
                    System.out.println("KEY: "+key);
                    System.out.println("VALOR: "+jsonObject.get(key));
                    if(key.equals("valueInList")){

                    }
                    if(key.equals("$if")){
                        //this.filterIn = Filters.in(jsonObject.get("name"), jsonObject.get("valueInList"))
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // convert JSON string to Map
        /*try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<String, Object>();
            map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
            System.out.println("MAP EN FILTRO: "+map.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //JSONArray array = (JSONArray) JSON.parse("[{'fieldName'='name', 'operator'='$in', 'value'=[Barcelona, Valencia]}]");
        //JSONObject object = (JSONObject) JSON.parse("{fieldName:'name', operator:'$in', value:['Barcelona', 'Valencia']}");
        //JSONArray array = (JSONArray) JSON.parse(strFiltro);
        /*for (Object it:array){
            System.out.println("LOOP FILTRO: "+it);
        }*/

    }

    public Bson getFilterIn() {
        return filterIn;
    }
}
