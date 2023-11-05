package com.bancom.test.controller;

import com.bancom.test.model.Post;
import com.bancom.test.model.Usuario;
import com.bancom.test.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getAllPosts(@PathVariable Integer userId){
        ResponseEntity responseEntity;
        Map<String, Object> respuesta = new HashMap<>();
        log.info("Iniciando servicio para obtener todos los usuarios");

        try {
            List<Post> posts = postService.getAllPosts(userId);
            respuesta.put("posts", posts);
            if (posts.size() > 0){
                responseEntity = ResponseEntity.ok().body(respuesta);
            } else {
                responseEntity = ResponseEntity.noContent().build();
            }
        } catch (Exception e){
            respuesta.put("error", e.getMessage());
            respuesta.put("timestamp", new Date());
            respuesta.put("status", HttpStatus.BAD_REQUEST.value());
            responseEntity = ResponseEntity.badRequest().body(respuesta);
        }


        return responseEntity;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> createPost(@PathVariable Integer userId, @RequestBody Post post){
        ResponseEntity responseEntity;
        Map<String, Object> respuesta = new HashMap<>();
        log.info("Iniciando servicio para crear Post");
        try {
            Post createdPost = postService.createPost(post, userId);
            respuesta.put("post", createdPost);
            respuesta.put("mensaje", "Post creado con éxito");
            respuesta.put("timestamp", new Date());
            responseEntity = ResponseEntity.created(URI.create("/api/post/".concat(createdPost.getId().toString())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(respuesta);
        } catch (Exception e){
            respuesta.put("error", e.getMessage());
            respuesta.put("timestamp", new Date());
            respuesta.put("status", HttpStatus.BAD_REQUEST.value());
            responseEntity = ResponseEntity.badRequest().body(respuesta);
        }

        return responseEntity;
    }

    @PutMapping("/{userId}/{postId}")
    public ResponseEntity<Usuario> updatePost(@RequestBody Post post, @PathVariable Integer userId, @PathVariable Integer postId){

        ResponseEntity responseEntity;
        Map<String, Object> respuesta = new HashMap<>();
        log.info("Iniciando servicio para actualizar Post");

        try {
            Post updatedPost = postService.updatePost(post, userId, postId);
            respuesta.put("post", updatedPost);
            respuesta.put("mensaje", "Post actualizado con éxito");
            respuesta.put("timestamp", new Date());
            responseEntity = ResponseEntity.created(URI.create("/api/post/".concat(updatedPost.getId().toString())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(respuesta);
        } catch (Exception e){
            respuesta.put("error", e.getMessage());
            respuesta.put("timestamp", new Date());
            respuesta.put("status", HttpStatus.BAD_REQUEST.value());
            responseEntity = ResponseEntity.badRequest().body(respuesta);
        }

        return responseEntity;
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer userId, @PathVariable Integer postId){
        ResponseEntity responseEntity;
        Map<String, Object> respuesta = new HashMap<>();
        log.info("Iniciando servicio para eliminar Post");

        try {
            postService.deletePost(userId, postId);
            responseEntity = new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            respuesta.put("error", e.getMessage());
            respuesta.put("timestamp", new Date());
            respuesta.put("status", HttpStatus.BAD_REQUEST.value());
            responseEntity = ResponseEntity.badRequest().body(respuesta);
        }

        return responseEntity;
    }
}
