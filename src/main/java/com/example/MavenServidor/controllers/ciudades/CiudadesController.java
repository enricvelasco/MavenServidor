package com.example.MavenServidor.controllers.ciudades;

import com.example.MavenServidor.controllers.RESTFulController;
import com.example.MavenServidor.services.CiudadesService;
import com.mongodb.client.FindIterable;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/ciudades")
public class CiudadesController extends RESTFulController {
    private CiudadesService ciudadesService;
    public CiudadesController(){
        dominio = "Tciudades";
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray list(@RequestParam(value = "query", required = false) String query){
        return super.list(query);
    }

    @Override
    @CrossOrigin
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    @ResponseBody
    public JSONObject read(@PathVariable("id") String id) {
        return super.read(id);
    }

    @Override
    @CrossOrigin
    @RequestMapping( value = "/", method = RequestMethod.POST )
    public JSONObject create(@RequestBody String jsonObject) {
        return super.create(jsonObject);
    }

    @Override
    @CrossOrigin
    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public JSONObject update(@RequestBody String jsonObject) {
        return super.update(jsonObject);
    }

    @Override
    @CrossOrigin
    @RequestMapping( value = "/", method = RequestMethod.DELETE )
    public String delete(@RequestBody String jsonObject) {
        return super.delete(jsonObject);
    }
}
