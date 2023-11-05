package com.bancom.test.service.impl;

import com.bancom.test.model.Usuario;
import com.bancom.test.repository.UsuarioRepository;
import com.bancom.test.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
	
	@Autowired
	PostRepository postRepository;

    @Override
    public List<Usuario> getAllUsers(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    @Override
    public Usuario createUser(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUser(Usuario usuario, Integer id){
        Usuario foundUser = usuarioRepository.findById(id.toString()).orElse(null);

        if (foundUser != null){
            foundUser.setCellphone(usuario.getCellphone());
            foundUser.setName(usuario.getName());
            foundUser.setLastName(usuario.getLastName());
            foundUser.setPassword(usuario.getPassword());
            foundUser.setUpdatedAt();
        } else {
            throw new RuntimeException("El usuario con id "+ id + " no existe");
        }

        return usuarioRepository.save(foundUser);
    }

    @Override
    public void deleteUser(Integer id){
        Usuario foundUser = usuarioRepository.findById(id.toString()).orElse(null);

        if (foundUser == null){
            throw new RuntimeException("El usuario con id "+ id + " no existe");
        }
		
		List<Post> foundPosts = postRepository.findByUsuario(foundUser);
		
		if (foundPosts.size() > 0){
            throw new RuntimeException("El usuario posee posts existentes.");
        }

        usuarioRepository.delete(foundUser);
    }

}
