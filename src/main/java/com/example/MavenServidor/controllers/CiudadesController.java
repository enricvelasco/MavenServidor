package com.example.MavenServidor.controllers;

import com.example.MavenServidor.services.CiudadesService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ciudades")
public class CiudadesController extends RESTFulController{
    private CiudadesService ciudadesService;
    //private String tabla;
    /*@Override
    public String list(String dominio) {
        return super.list();
        //return "ENTRA EN LIST--------*---";
    }*/
    public CiudadesController(){
        dominio = "Tciudades";
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray list() {
        return super.list();
    }

    @Override
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    @ResponseBody
    public JSONObject read(@PathVariable("id") String id) {
        return super.read(id);
    }

    /*@Override
    public String read() {
        return super.read();
        //return "ENTRA EN READ---------*--";
    }*/

    /*@Override
    public String create() {
        return super.create();
        //return "ENTRA EN CREATE--------*---";
    }*/



    @Override
    @RequestMapping( value = "/", method = RequestMethod.POST )
    public String create(@RequestBody String jsonObject) {
        return super.create(jsonObject);
    }

    @Override
    public String update() {
        return super.update();
        //return "ENTRA EN UPDATE---------*--";
    }

    @Override
    public String delete() {
        return super.delete();
        //return "ENTRA EN DELETE---------*--";
    }
}
