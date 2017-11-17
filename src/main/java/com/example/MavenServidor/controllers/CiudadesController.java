package com.example.MavenServidor.controllers;

import com.example.MavenServidor.domains.Tciudades;
import com.example.MavenServidor.services.CiudadesService;
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
    public String list() {
        return super.list();
    }

    @Override
    public String read(String id) {
        return super.read(id);
    }

    /*@Override
    public String read() {
        return super.read();
        //return "ENTRA EN READ---------*--";
    }*/

    @Override
    public String create() {
        return super.create();
        //return "ENTRA EN CREATE--------*---";
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
