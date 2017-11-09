package com.example.MavenServidor.controllers;

import com.example.MavenServidor.domains.Tciudades;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CiudadesController {
    @RequestMapping( value = "/", method = RequestMethod.GET )
    //public Iterable<Tciudades> list(){
    public String list(){
        //return restFulService.list();
        //Retornar la lista de las ciudades

        return "LISTA CIUDADES RESPUESTA";
    }

    @RequestMapping( value = "/", method = RequestMethod.POST )
    public Tciudades create(@RequestBody Tciudades ciudad){
        //return restFulService.save(post);
        //insetar en base de datos
        return ciudad;
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public Tciudades read(@PathVariable(value="id") long id){
        //return restFulService.getPost(id);
        //retorna una ciudad conccreta
        Tciudades ciudadRespuesta = new Tciudades("ciudad retornada");
        return ciudadRespuesta;
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public String update(@PathVariable(value="id") int id){
        return "post.update()";
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public String delete(@PathVariable(value="id") int id){
        return "post.delete()";
    }
}
