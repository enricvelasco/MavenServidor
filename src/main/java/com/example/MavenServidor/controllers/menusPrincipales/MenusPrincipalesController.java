package com.example.MavenServidor.controllers.menusPrincipales;

import com.example.MavenServidor.controllers.RESTFulController;
import com.example.MavenServidor.services.CiudadesService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.bson.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menusPrincipales")
public class MenusPrincipalesController  extends RESTFulController {
    //private CiudadesService ciudadesService;
    public MenusPrincipalesController(){
        dominio = "TmenusPrincipales";
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray list(String query) {
        return super.list(query);
    }

    @Override
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    @ResponseBody
    public JSONObject read(String id) {
        return super.read(id);
    }

    @Override
    @RequestMapping( value = "/", method = RequestMethod.POST )
    public JSONObject create(String jsonObject) {
        return super.create(jsonObject);
    }

    @Override
    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public JSONObject update(String jsonObject) {
        return super.update(jsonObject);
    }

    @Override
    @RequestMapping( value = "/", method = RequestMethod.DELETE )
    public String delete(String jsonObject) {
        return super.delete(jsonObject);
    }
}
