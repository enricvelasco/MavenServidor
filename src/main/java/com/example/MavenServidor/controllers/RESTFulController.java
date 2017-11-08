package com.example.MavenServidor.controllers;

import com.example.MavenServidor.domains.Post;
import com.example.MavenServidor.services.RESTFulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class RESTFulController {

    private RESTFulService restFulService;

    @Autowired
    public RESTFulController(RESTFulService restFulService){
        this.restFulService = restFulService;
    }

        @RequestMapping( value = "/", method = RequestMethod.GET )
        public Iterable<Post> list(){
            return restFulService.list();
        }

        @RequestMapping( value = "/", method = RequestMethod.POST )
        public Post create(@RequestBody Post post){
            return restFulService.save(post);
        }

        @RequestMapping( value = "/{id}", method = RequestMethod.GET )
        public Post read(@PathVariable(value="id") long id){
            return restFulService.getPost(id);
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
