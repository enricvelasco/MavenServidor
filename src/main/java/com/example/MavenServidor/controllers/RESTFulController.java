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
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
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
    public FindIterable list(String query){
        System.out.println("LA QUERY QUE LLEGA "+query);
        MongoCollection<Document> collection = db.getCollection(dominio);

        Bson filter = montarFiltro(query);
        FindIterable<Document> resultDocument = (filter == null) ?  collection.find() :  collection.find(filter);

        for(Document doc : resultDocument) {
            System.out.println("EL DOCUMENTO: "+doc.toJson());
        }

        /*MongoCursor<Document> iterator = (MongoCursor<Document>) resultDocument;
        JSONArray list = new JSONArray();
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            JSONObject jsonObj = read(doc.get( "_id" ).toString());
            list.add(jsonObj);
        }
        System.out.println(JSON.serialize(list));*/
        //return list;
        return resultDocument;
    }

    private Bson montarFiltro(String query){
        //Bson filter = null;
        System.out.println("QUERY STRING: "+query);
        FilterGenerate fil = new FilterGenerate(query);
        System.out.println("EL FILTRO GENERADO: "+fil.getFiltroGenerado());
        ArrayList<String> a1= new ArrayList<>();
        a1.add("Barcelona");
        a1.add("Valencia");
        Bson filter = Filters.in("name", a1);
        System.out.println("EL FILTRO BSON ES "+filter.toString());
        return fil.getFiltroGenerado();
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
        MongoCollection<Document> collection = db.getCollection(dominio);
        Document doc = Document.parse(jsonObject);
        collection.insertOne(doc);
        JSONObject resp = new JSONObject();
        resp.put("error", "objeto creado correctamente");
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
