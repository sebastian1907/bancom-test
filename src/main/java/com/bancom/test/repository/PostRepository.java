package com.bancom.test.repository;

import com.bancom.test.model.Post;
import com.bancom.test.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findByUsuario(Usuario usuario);
    Post findByUsuarioAndId(Usuario usuario, Integer id);
}
