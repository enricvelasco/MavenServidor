package com.example.MavenServidor.controllers;

import Filter.FilterGenerate;
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

import javax.print.Doc;

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
        FindIterable<Document> resultDocument = null;
        if(query != null){
            FilterGenerate filter = new FilterGenerate(query);
            resultDocument = (filter.getFiltroGenerado() == null) ?  collection.find() :  collection.find(filter.getFiltroGenerado());
        }else{
            resultDocument = collection.find(new Document());
        }
        JSONArray list = new JSONArray();
        //list = asignarIdsCorrectosString(list, resultDocument);
        for(Document doc : resultDocument) {
            JSONObject obj = montarObjeto(doc);
            list.add(obj);
            /*JSONObject jsonObj = new JSONObject();
            jsonObj.put("_id", doc.get("_id").toString();
            System.out.println("ID RECORRE: ");
            for(Object valor:doc.values()){
                System.out.println("DENTRO DEL FOR "+ valor);
                if(valor instanceof ArrayList){
                    JSONArray sublist = new JSONArray();

                }
            }*/

            //System.out.println("EL DOCUMENTO: "+doc.toJson());
            //recorrer campos documento
            //JSONObject jsonObj = recorrerObjetoDevolverIdsCorrectos(doc);
            //JSONObject jsonObj = read(doc.get( "_id" ).toString());



            //devolverTodosLosIdsString(jsonObj);


            //devolverTodosLosIdsString(jsonObj);

            //list.add(jsonObj);
        }
        return list;
    }

    private JSONObject montarObjeto(Document doc){
        JSONObject jsonObject = new JSONObject();
        Set<String> keys = doc.keySet();
        for(String key : keys){
            if (key.equals("_id")) {
                jsonObject.put(key, doc.get(key).toString());
            } else {
                //jsonObject.put(key, doc.get(key));
                if(doc.get(key) instanceof ArrayList){
                    JSONArray lista = new JSONArray();

                    System.out.println("VA A RECURSIVO::: "+doc.get(key));

                    JSONArray arrayValue = montarArrayJson(((ArrayList) doc.get(key)).toArray(), lista);

                    jsonObject.put(key, arrayValue);
                }else{
                    jsonObject.put(key, doc.get(key));
                }

            }
        }

        return jsonObject;
    }

    private JSONArray montarArrayJson(Object[] obj, JSONArray lista) {
        //System.out.println("OBJETO QUE ENTRA "+obj);
        //JSONArray lista = new JSONArray();
        JSONObject objCarga = new JSONObject();

        for(Object val : obj){
            //JSONObject objCarga = new JSONObject();
            Document object = (Document) val;
            System.out.println("RECORRE: "+object);

            Set<String> keys = object.keySet();
            for(String key : keys){
                if (key.equals("_id")) {
                    objCarga.put(key, object.get(key).toString());
                } else {
                    if(object.get(key) instanceof ArrayList){
                        System.out.println("CUANDO ES UN ARRAY::"+object.get(key));
                        JSONArray arrayValue = montarArrayJson(((ArrayList) object.get(key)).toArray(), lista);
                        System.out.println("RESPUESTA AÑADIR::"+arrayValue);
                        //JSONObject qqq = montarObjeto(object);
                        //System.out.println("EL QQQQ:: "+qqq);
                        //objCarga.put(key, arrayValue);
                        //System.out.println("EN ARRAY: "+arrayValue);
                        //lista.add(arrayValue);
                    }else{
                        objCarga.put(key, object.get(key));
                    }
                }
                //System.out.println("-----------AÑADE OBJETO: "+objCarga);
            }
            System.out.println("-----------AÑADE OBJETO: "+objCarga);
            //lista.add(objCarga);

            //lista.add(objCarga);
            //System.out.println("LA LISTA: "+lista);
            //return lista;
        }
        System.out.println("-------------------");
        return lista;
    }

    private void devolverTodosLosIdsString(JSONObject jsonObj){
        System.out.println("ENTRA A DEVOLVER ID"+ jsonObj.toString());
    }

    /*private JSONObject recorrerObjetoDevolverIdsCorrectos(Document doc) {
        //Document jsonObj = Document.parse(doc.get( "_id" ).toString());
        //doc.get("_id").to2String();
        System.out.println("QUE ENTRA: "+ doc);
        JSONObject jsonObj = read(doc);
        for(Object valor:doc.values()){

            if(valor instanceof ArrayList){
                System.out.println("Tiene hijo "+ valor);
                Iterator<Document> itDoc= ((ArrayList) valor).iterator();
                while (itDoc.hasNext()){

                    Document dc = itDoc.next();
                    System.out.println("EL IT DOC "+dc.get("description"));
                    //JSONObject sub = read(dc.get( "_id" ).toString());
                    JSONObject sub = recorrerObjetoDevolverIdsCorrectos(dc);
                }

                //Document dev = recorrerObjetoDevolverIdsCorrectos((Document) a);

            }
         }
        return jsonObj;
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
