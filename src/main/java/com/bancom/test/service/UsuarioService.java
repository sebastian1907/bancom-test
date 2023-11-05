package com.bancom.test.service;

import com.bancom.test.model.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> getAllUsers();
    Usuario createUser(Usuario usuario);
    Usuario updateUser(Usuario usuario, Integer id);
    void deleteUser(Integer id);
}
