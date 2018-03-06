package com.example.MavenServidor.controllers;

import Filter.FilterGenerate;
import com.example.MavenServidor.services.GeneralService;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.transaction.annotation.Transactional;

import static com.example.MavenServidor.MavenServidorApplication.db;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RESTFulController {
    protected String dominio;
    private GeneralService generalService = new GeneralService();

    @Transactional
    public JSONArray list(String query){
        System.out.println("LA QUERY QUE LLEGA "+query);
        MongoCollection<Document> collection = db.getCollection(dominio);
        FindIterable<Document> resultDocument = null;
        if(query != null){
            FilterGenerate filter = new FilterGenerate(query);
            resultDocument = (filter.getFiltroGenerado() == null) ?  collection.find() :  collection.find(filter.getFiltroGenerado());
        }else{
            resultDocument = collection.find(new Document());
        }
        JSONArray list = new JSONArray();
        for(Document doc : resultDocument) {
            JSONObject obj = generalService.montarObjeto(doc);
            list.add(obj);
        }
        return list;
    }
/*
    private JSONObject montarObjeto(Document doc){
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
    }*/


    @Transactional
    public JSONObject read(String id){
        MongoCollection<Document>  collection = db.getCollection(dominio);
        System.out.println("EL ID QUE ENTRA ES: "+id);
        Document myDoc = collection.find(eq("_id", new ObjectId(id))).first();
        Set<String> keys = myDoc.keySet();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id", myDoc.get( "_id" ).toString());
        for(String key : keys){
            if(!key.equals("_id")){
                jsonObj.put(key,myDoc.get(key));
            }
        }
        return jsonObj;
    }

    @Transactional
    public JSONObject create(String jsonObject){
        System.out.println("ENTRA EN CREATE "+ jsonObject);
        JSONObject resp = new JSONObject();

        try {
            List<Document> list = new ArrayList<>();
            MongoCollection<Document> collection = db.getCollection(dominio);
            ArrayList<BasicDBObject> jsonArray = (ArrayList<BasicDBObject>) JSON.parse(jsonObject);
            int cont = 0;
            for(BasicDBObject object:jsonArray){
                System.out.println("EL OBJETO INSERTAR: "+object);
                Document doc = Document.parse(object.toString());
                list.add(doc);
                cont++;
            }
            collection.insertMany(list);
            resp.put("error", "objeto cre ado correctamente");
            resp.put("insertados", cont);
        } catch (Exception e) {
            e.printStackTrace();
            resp.put("error", e);
        }
        return resp;
    }

    @Transactional
    public JSONObject update(String jsonObject){
        System.out.println("ENTRA A UPDATE");
        JSONObject resp = new JSONObject();
        Document doc = Document.parse(jsonObject);

        MongoCollection<Document>  collection = db.getCollection(dominio);
        String idElemento = doc.get("id").toString();

        System.out.println("-------UPDATE SIMPLE");
        UpdateResult updateResult = actualizarRegistro(idElemento, collection, doc);
        resp.put("error", "objeto creado correctamente");
        resp.put("numActualizados", updateResult);

        return resp;
    }

    @Transactional
    public UpdateResult actualizarRegistro(String idElemento, MongoCollection<Document>  collection, Document doc){
        Document objUpdate = new Document();
        for(String key : doc.keySet()){
            if(!key.equals("id")){
                System.out.println("KEY A INSERTAR" + key);
                objUpdate.put(key,doc.get(key));
            }
        }
        UpdateResult updateResult = collection.updateOne(eq("_id", new ObjectId(idElemento)),new Document("$set", objUpdate));
        return updateResult;
    }

    @Transactional
    public String delete(String jsonObject){
        Document doc = Document.parse(jsonObject);
        MongoCollection<Document>  collection = db.getCollection(dominio);
        collection.deleteOne(eq("_id", new ObjectId(doc.get("id").toString())));

        return "ENTRA EN DELETE-----------";
    }
}
