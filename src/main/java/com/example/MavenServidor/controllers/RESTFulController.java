package com.example.MavenServidor.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import static com.example.MavenServidor.MavenServidorApplication.db;
import static com.mongodb.client.model.Filters.eq;

import java.lang.reflect.*;
import java.util.Iterator;
import java.util.Set;

//@RequestMapping("/")
public class RESTFulController {
    protected String dominio;
    //protected Object objetoDominio;


    //@RequestMapping(value = "/", method = RequestMethod.GET)
    //@ResponseBody
    public JSONArray list(){
        MongoCollection<Document> collection = db.getCollection(dominio);

        //MongoIterable<Document> iterator = collection.find();

        //DBObject obj = collection.first;
        Document myDoc = collection.find().first();
        Set<String> keys = myDoc.keySet();
        System.out.println("KEYS DEL ELEMENTO"+ keys);
        //Iterator iteratorKey = keys.iterator();//encontrar las keys

        MongoCursor<Document> iterator = collection.find().iterator();

        /*Set<String> keys = iterator.find;
        Iterator iteratorKey = keys.iterator();*/

        JSONArray list = new JSONArray();
        while (iterator.hasNext()) {
            //System.out.println("ITERATOR NAME: "+ iterator);
            Document doc = iterator.next();
            JSONObject jsonObj = read(doc.get( "_id" ).toString());
            /*ObjectId id = (ObjectId)doc.get( "_id" );
            jsonObj.put("id", id.toString());

            for(String key : keys){
                if(!key.equals("_id")){
                    jsonObj.put(key,doc.get(key));
                }
            }*/
            list.add(jsonObj);
        }
        System.out.println(JSON.serialize(list));
        return list;
    }

    public JSONObject read(String id){
        MongoCollection<Document>  collection = db.getCollection(dominio);
        System.out.println("EL ID QUE ENTRA ES: "+id);
        Document myDoc = collection.find(eq("_id", new ObjectId(id))).first();
        Set<String> keys = myDoc.keySet();
        JSONObject jsonObj = new JSONObject();
        //ObjectId id = (ObjectId)myDoc.get( "_id" );
        jsonObj.put("id", myDoc.get( "_id" ).toString());
        for(String key : keys){
            if(!key.equals("_id")){
                jsonObj.put(key,myDoc.get(key));
            }
        }
        return jsonObj;
    }

    //@RequestMapping( value = "/", method = RequestMethod.POST )
    public JSONObject create(/*@RequestBody*/ String jsonObject){
        System.out.println("ENTRA EN CREATE "+ jsonObject);
        //JSONObject jsonObj = new JSONObject(jsonObject);Charset.forName("UTF-8").encode(json)
        //DBCollection collection = (DBCollection) db.getCollection(dominio);
        MongoCollection<Document> collection = db.getCollection(dominio);
        Document doc = Document.parse(jsonObject);
        //DBObject dbObject = (DBObject)JSON.parse(jsonObject);;

        collection.insertOne(doc);

        JSONObject resp = new JSONObject();
        resp.put("error", "objeto creado correctamente");
        return resp;
    }

    //@RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public JSONObject update(String jsonObject){
        System.out.println("ENTRA A UPDATE");
        Document doc = Document.parse(jsonObject);

        //JSONObject jsonGet = read(doc.get("id").toString());
        //Document docGet = Document.parse(String.valueOf(jsonGet));

        MongoCollection<Document>  collection = db.getCollection(dominio);
        //collection.update(searchQuery, newDocument);
        //collection.updateOne(docGet, doc);
        //collection.updateOne(eq("_id", doc.get("id").toString()), new Document("$set", new Document("population", doc.get("population").toString())));
        //collection.updateOne(new BasicDBObject("_id", "5a11c68e36642eef8121ad7e"),new BasicDBObject("$set", new BasicDBObject("population", "4000")));
        //collection.updateOne({ "name" , "Central Perk Cafe" },{ $set: { "violations" , 3 } });

        //collection.updateOne({ "name" , "Central Perk Cafe" });
        UpdateResult updateResult = collection.updateOne(eq("_id", new ObjectId("5a11c68e36642eef8121ad7e")),new Document("$set", new Document("population", 5000)));
        System.out.println("Number of record updated:- " + updateResult.getModifiedCount());

        JSONObject resp = new JSONObject();
        resp.put("error", "objeto creado correctamente");
        return resp;

        //return "post.update()";
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public String delete(/*@PathVariable(value="id") int id*/){
        return "ENTRA EN DELETE-----------";

        //return "post.delete()";
    }
}
