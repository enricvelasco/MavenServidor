package com.example.MavenServidor.controllers;

import com.example.MavenServidor.domains.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@RestController
@RequestMapping("/posts")
public class PostController {

    //private PostService postService;

    /*@Autowired
    public PostController(PostServiceImpl postService){
        this.postService = postService;
    }*/

    /*@RequestMapping( value = "/", method = RequestMethod.GET )
    public Iterable<Post> list(){
        return postService.list();
    }

    @RequestMapping( value = "/", method = RequestMethod.POST )
    public Post create(@RequestBody Post post){
        return postService.save(post);
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public Post read(@PathVariable(value="id") long id){
        return postService.getPost(id);
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public String update(@PathVariable(value="id") int id){
        return "post.update()";
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public String delete(@PathVariable(value="id") int id){
        return "post.delete()";
    }*/


}
