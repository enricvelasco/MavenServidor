package com.example.MavenServidor.controllers;

import org.springframework.web.bind.annotation.*;
@RequestMapping("/")
public class RESTFulController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String list(){
        System.out.println("ENTRA EN LIST-----------");
        return "ENTRA EN LIST-----------";
        //return restFulService.list();
        //Retornar la lista de las ciudades
        //@RequestMapping( value = "/", method = RequestMethod.GET )
        //DBCollection collection = db.getCollection("ciudades");
        //DBCursor dbo = collection.find();
        /*while (dbo.hasNext()) {
            DBObject obj = dbo.next();
            System.out.println(obj);
            //do your thing
        }*/
        //return String.valueOf("HOLA");
    }
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String read(/*@PathVariable(value="_id") long id*/){
        return "ENTRA EN READ-----------";
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
