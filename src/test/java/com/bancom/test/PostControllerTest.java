package com.bancom.test;

import com.bancom.test.controller.PostController;
import com.bancom.test.controller.UsuarioController;
import com.bancom.test.model.Post;
import com.bancom.test.model.Usuario;
import com.bancom.test.service.PostService;
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
public class PostControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    Usuario USER_1 = new Usuario(1,"976433032", "Sebastian", "Contreras", "123");
    Usuario USER_2 = new Usuario(2,"987654321", "Juan", "Perez", "456");
    Post POST_1 = new Post(1, "post test 1", USER_1);
    Post POST_2 = new Post(2, "post test 2", USER_1);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void getAllPosts_success() throws Exception {
        List<Post> posts = new ArrayList<>(Arrays.asList(POST_1, POST_2));
        Mockito.when(postService.getAllPosts(1)).thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/post/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.posts", hasSize(2)));
    }

    @Test
    public void createPost_success() throws Exception {
        Post post =  new Post(3, "post test 3", USER_2);

        Mockito.when(postService.createPost(post, USER_2.getId())).thenReturn(post);
        String content = objectWriter.writeValueAsString(post);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/post/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje", is("Post creado con éxito")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.post.text", is("post test 3")));
    }

    @Test
    public void updatePost_success() throws Exception {
        Post post =  new Post(4, "post test 4", USER_2);

        Mockito.when(postService.updatePost(post, USER_2.getId(), post.getId())).thenReturn(post);
        String content = objectWriter.writeValueAsString(post);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/post/2/4")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje", is("Post actualizado con éxito")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.post.text", is("post test 4")));
    }

    @Test
    public void deletePostById_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/post/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
