package com.bancom.test.service.impl;

import com.bancom.test.model.Post;
import com.bancom.test.model.Usuario;
import com.bancom.test.repository.PostRepository;
import com.bancom.test.repository.UsuarioRepository;
import com.bancom.test.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Post> getAllPosts(Integer userId){

        Usuario usuario = usuarioRepository.findById(userId.toString()).orElse(null);
        if (usuario == null){
            throw new RuntimeException("El usuario no existe");
        }
        List<Post> posts = postRepository.findByUsuario(usuario);
        return posts;
    }

    @Override
    public Post createPost(Post post, Integer userId){

        Usuario usuario = usuarioRepository.findById(userId.toString()).orElse(null);

        if (usuario != null){
            post.setUsuario(usuario);
        } else {
            throw new RuntimeException("El usuario no existe");
        }

        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post, Integer userId, Integer postId){

        Usuario foundUser = usuarioRepository.findById(userId.toString()).orElse(null);

        if (foundUser == null){
            throw new RuntimeException("El usuario con id "+ userId + " no existe");
        }

        Post foundPost= postRepository.findByUsuarioAndId(foundUser, postId);

        if (foundPost == null){
            throw new RuntimeException("El post con id "+ postId + " no existe o no pertenece a este usuario");
        }

        foundPost.setText(post.getText());
        foundPost.setUpdatedAt();

        return postRepository.save(foundPost);
    }

    @Override
    public void deletePost(Integer userId, Integer postId){
        Usuario foundUser = usuarioRepository.findById(userId.toString()).orElse(null);

        if (foundUser == null){
            throw new RuntimeException("El usuario con id "+ userId + " no existe");
        }

        Post foundPost= postRepository.findByUsuarioAndId(foundUser, postId);

        if (foundPost == null){
            throw new RuntimeException("El post con id "+ postId + " no existe o no pertenece a este usuario");
        }

        postRepository.delete(foundPost);
    }
}
