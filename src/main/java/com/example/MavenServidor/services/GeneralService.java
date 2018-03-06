package com.example.MavenServidor.services;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Set;

public class GeneralService {
    public JSONObject montarObjeto(Document doc){
        JSONObject jsonObject = new JSONObject();
        Set<String> keys = doc.keySet();
        for(String key : keys){
            if (key.equals("_id")) {
                jsonObject.put(key, doc.get(key).toString());
            } else {
                if(doc.get(key) instanceof ArrayList){
                    JSONArray arrayValue = montarArrayJson(((ArrayList) doc.get(key)).toArray());
                    jsonObject.put(key, arrayValue);
                }else{
                    jsonObject.put(key, doc.get(key));
                }
            }
        }
        return jsonObject;
    }

    private JSONArray montarArrayJson(Object[] obj) {
        JSONArray lista = new JSONArray();
        for(Object val : obj){
            Document object = (Document) val;
            JSONObject resp = recorrerObjeto(object);
            lista.add(resp);
        }
        return lista;
    }

    private JSONObject recorrerObjeto(Document object){
        JSONObject jsonRespuesta = new JSONObject();
        Set<String> keys = object.keySet();
        for(String key : keys) {
            if (key.equals("_id")) {
                jsonRespuesta.put(key, object.get(key).toString());
            } else {
                if (object.get(key) instanceof ArrayList) {
                    JSONArray jsonArray = new JSONArray();
                    ArrayList<Object> arr = (ArrayList<Object>) object.get(key);
                    for(Object a : arr){
                        JSONObject resp = recorrerObjeto((Document) a);
                        jsonArray.add(resp);
                    }
                    jsonRespuesta.put(key, jsonArray);
                } else {
                    jsonRespuesta.put(key, object.get(key));
                }
            }
        }
        return jsonRespuesta;
    }
}
