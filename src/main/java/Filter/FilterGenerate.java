package Filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.*;

public class FilterGenerate {
    Bson filtroGenerado;

    public FilterGenerate(String strFiltro) {
        //String json = "[{'fieldName':'name', 'operator':'$in', 'listValues':[{value:'Barcelona'}, {value:'Valencia'}, {value:'Madrid'}]}]";
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
                    if(key.equals("listValues")){
                        ArrayList<BasicDBObject> listaValores = (ArrayList<BasicDBObject>) JSON.parse(jsonObject.get("listValues").toString());
                        for(BasicDBObject doc:listaValores){
                            System.out.println("DOCUMENT: "+doc.get("value").toString());
                            valoresList.add(doc.get("value").toString());
                        }
                    }else if(key.equals("value")){

                    }

                    String condition = jsonObject.get("operator").toString();
                    if(condition.equals("$in")){
                        //Coincide con cualquiera de los valores especificados en una matriz.
                        this.filtroGenerado = Filters.in(jsonObject.get("fieldName").toString(), valoresList);
                    }else if(condition.equals("$eq")){
                        //Coincide con valores que son iguales a un valor especificado.
                        this.filtroGenerado = Filters.eq(jsonObject.get("fieldName").toString(), valoresList.get(0));
                    }else if(condition.equals("$gt")){
                        //Coincide con valores que son mayores que un valor especificado.
                        this.filtroGenerado = Filters.gt(jsonObject.get("fieldName").toString(), valoresList.get(0));
                    }else if(condition.equals("$gte")){
                        //Coincide con valores que son mayores o iguales a un valor especificado.
                        this.filtroGenerado = Filters.gte(jsonObject.get("fieldName").toString(), valoresList.get(0));
                    }else if(condition.equals("$lt")){
                        //Coincide con valores que son menores que un valor especificado.
                        this.filtroGenerado = Filters.lt(jsonObject.get("fieldName").toString(), valoresList.get(0));
                    }else if(condition.equals("$lte")){
                        //Coincide con los valores que son menores o iguales que un valor especificado.
                        this.filtroGenerado = Filters.lte(jsonObject.get("fieldName").toString(), valoresList.get(0));
                    }else if(condition.equals("$ne")){
                        //Coincide con todos los valores que no son iguales a un valor especificado.
                        this.filtroGenerado = Filters.ne(jsonObject.get("fieldName").toString(), valoresList.get(0));
                    }else if(condition.equals("$nin")){
                        //No coincide con ninguno de los valores especificados en una matriz.
                        this.filtroGenerado = Filters.nin(jsonObject.get("fieldName").toString(), valoresList);
                    }

                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Bson getFiltroGenerado() {
        return filtroGenerado;
    }

}
