package com.bancom.test.controller;

import com.bancom.test.model.Post;
import com.bancom.test.model.Usuario;
import com.bancom.test.service.UsuarioService;
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
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getAllUsers(){
        ResponseEntity responseEntity;
        log.info("Iniciando servicio para obtener todos los usuarios");
        List<Usuario> usuarios = usuarioService.getAllUsers();

        if (usuarios.size() > 0){
            responseEntity = ResponseEntity.ok().body(usuarios);
        } else {
            responseEntity = ResponseEntity.noContent().build();
        }

        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Usuario usuario){
        ResponseEntity responseEntity;
        Map<String, Object> respuesta = new HashMap<>();
        log.info("Iniciando servicio para crear usuario");
        Usuario createdUser = usuarioService.createUser(usuario);
        if (createdUser != null){
            respuesta.put("usuario", createdUser);
            respuesta.put("mensaje", "Usuario creado con éxito");
            respuesta.put("timestamp", new Date());
            responseEntity = ResponseEntity.created(URI.create("/api/usuario/".concat(createdUser.getId().toString())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(respuesta);
        } else {
            respuesta.put("error", "Error al crear usuario");
            respuesta.put("timestamp", new Date());
            respuesta.put("status", HttpStatus.BAD_REQUEST.value());
            responseEntity = ResponseEntity.badRequest().body(respuesta);
        }

        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@RequestBody Usuario usuario, @PathVariable Integer id){

        ResponseEntity responseEntity;
        Map<String, Object> respuesta = new HashMap<>();
        log.info("Iniciando servicio para actualizar Usuario");

        try {
            Usuario updatedUser = usuarioService.updateUser(usuario, id);
            respuesta.put("usuario", updatedUser);
            respuesta.put("mensaje", "Usuario actualizado con éxito");
            respuesta.put("timestamp", new Date());
            responseEntity = ResponseEntity.created(URI.create("/api/usuario/".concat(updatedUser.getId().toString())))
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        ResponseEntity responseEntity;
        Map<String, Object> respuesta = new HashMap<>();
        log.info("Iniciando servicio para eliminar Usuario");

        try {
            usuarioService.deleteUser(id);
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
