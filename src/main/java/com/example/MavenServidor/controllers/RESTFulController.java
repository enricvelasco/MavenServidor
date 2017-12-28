package com.example.MavenServidor.controllers;

import Filter.FilterGenerate;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.transaction.annotation.Transactional;
import static com.example.MavenServidor.MavenServidorApplication.db;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RESTFulController {
    protected String dominio;

    @Transactional
    public JSONArray list(String query){
        System.out.println("LA QUERY QUE LLEGA "+query);
        MongoCollection<Document> collection = db.getCollection(dominio);

        FilterGenerate filter = new FilterGenerate(query);
        FindIterable<Document> resultDocument = (filter.getFiltroGenerado() == null) ?  collection.find() :  collection.find(filter.getFiltroGenerado());
        JSONArray list = new JSONArray();
        for(Document doc : resultDocument) {
            System.out.println("EL DOCUMENTO: "+doc.toJson());
            JSONObject jsonObj = read(doc.get( "_id" ).toString());
            list.add(jsonObj);
        }
        return list;
    }

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
