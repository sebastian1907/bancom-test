package com.bancom.test;

import com.bancom.test.controller.UsuarioController;
import com.bancom.test.model.Usuario;
import com.bancom.test.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    Usuario USER_1 = new Usuario(1,"976433032", "Sebastian", "Contreras", "123");
    Usuario USER_2 = new Usuario(2,"987654321", "Juan", "Perez", "456");
    Usuario USER_3 = new Usuario(3,"123456789", "Jose", "Gonzales", "789");

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void getAllUsers() throws Exception {
        List<Usuario> users = new ArrayList<>(Arrays.asList(USER_1, USER_2, USER_3));
        Mockito.when(usuarioService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/usuario/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    public void createUser_success() throws Exception {
        Usuario user = new Usuario(4, "997713035", "John", "Contreras", "123");

        Mockito.when(usuarioService.createUser(user)).thenReturn(user);
        String content = objectWriter.writeValueAsString(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje", is("Usuario creado con éxito")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuario.name", is("John")));
    }

    @Test
    public void updateUser_success() throws Exception {
        Usuario user = new Usuario(5, "998877554", "Manuel", "Monje", "456");

        Mockito.when(usuarioService.updateUser(user, user.getId())).thenReturn(user);
        String content = objectWriter.writeValueAsString(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/usuario/5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje", is("Usuario actualizado con éxito")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.usuario.name", is("Manuel")));
    }

    @Test
    public void deleteUserById_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
