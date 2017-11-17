package com.example.MavenServidor.controllers;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import static com.example.MavenServidor.MavenServidorApplication.db;

//@RequestMapping("/")
public class RESTFulController {
    protected String dominio;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String list(){
        MongoCollection<Document> collection = db.getCollection(dominio);

        MongoCursor<Document> iterator = collection.find().iterator();

        BasicDBList list = new BasicDBList();
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            list.add(doc);
        }
        System.out.println(JSON.serialize(list));

        return JSON.serialize(list);
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String read(@PathVariable(value="_id") String id){
        MongoCollection<Document>  collection = db.getCollection(dominio);

        /*BasicDBObject searchObject = new BasicDBObject();
        searchObject.put("name", "Barcelona");
        FindIterable<Document> resultSubset = collection.find(searchObject);*/

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        FindIterable<Document> resultSubset = collection.find();

        //Document resultSubset = collection.find(eq("_id", new ObjectId(id))).first();

        /*while(resultSubset.hasNext()){
            System.out.println(resultSubset.next());*/

        //BasicDBObject query=new BasicDBObject("_id",new ObjectId(id));
        //FindIterable<Document> cursor = collection.find();
        //DBObject query = new BasicDBObject();
        //query.put("_id", new ObjectId(id));

        //Object cursor = collection.find(query);

        return JSON.serialize(resultSubset);
        //return String.valueOf(resultSubset);
        //return restFulService.getPost(id);
        //retorna una ciudad conccreta
        //Tciudades ciudadRespuesta = new Tciudades("ciudad retornada");
        //return ciudadRespuesta;
        //return "ADIOS";
    }

    @RequestMapping( value = "/", method = RequestMethod.POST )
    public String create(/*@RequestBody Tciudades ciudad*/){
        return "ENTRA EN CREATE-----------";
        //return restFulService.save(post);
        //insetar en base de datos
        //return "creado";
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public String update(/*@PathVariable(value="id") int id*/){
        return "ENTRA EN UPDATE-----------";

        //return "post.update()";
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public String delete(/*@PathVariable(value="id") int id*/){
        return "ENTRA EN DELETE-----------";

        //return "post.delete()";
    }
}
