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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FilterGenerate {
    Bson filtroGenerado;

    public FilterGenerate(String strFiltro) {
        //String json = "[{'fieldName':'name', 'operator':'$in', 'valueInList':['Barcelona', 'Valencia', 'Madrid']}]";
        try {
            JSONParser jsonParser=new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(strFiltro);
            //System.out.println("JSON ARRAY FILtER: "+jsonArray);

            ArrayList<String>  valoresList = new ArrayList<>();
            for (Object object : jsonArray){
                JSONObject jsonObject = (JSONObject) object;
                Set<String> keys = jsonObject.keySet();
                for (String key:keys){
                    System.out.println("KEY: "+key);
                    System.out.println("VALOR: "+jsonObject.get(key));
                    if(key.equals("valueInList")){
                        ArrayList<String> valoresObjList = (ArrayList<String>) jsonObject.get("valueInList");
                        for(String valor:valoresObjList){
                            valoresList.add(valor);
                        }
                    }else if(key.equals("value")){

                    }

                    String condition = jsonObject.get("operator").toString();
                    if(condition.equals("$in")){
                        //Coincide con cualquiera de los valores especificados en una matriz.
                        this.filtroGenerado = Filters.in(jsonObject.get("fieldName").toString(), valoresList);
                    }else if(condition.equals("$eq")){
                        //Coincide con valores que son iguales a un valor especificado.
                    }else if(condition.equals("$gt")){
                        //Coincide con valores que son mayores que un valor especificado.
                    }else if(condition.equals("$gte")){
                        //Coincide con valores que son mayores o iguales a un valor especificado.
                    }else if(condition.equals("$lt")){
                        //Coincide con valores que son menores que un valor especificado.
                    }else if(condition.equals("$lte")){
                        //Coincide con los valores que son menores o iguales que un valor especificado.
                    }else if(condition.equals("$ne")){
                        //Coincide con todos los valores que no son iguales a un valor especificado.
                    }else if(condition.equals("$nin")){
                        //No coincide con ninguno de los valores especificados en una matriz.
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

    public Bson getFiltroGenerado() {
        return filtroGenerado;
    }

}
