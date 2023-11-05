package com.bancom.test.service;

import com.bancom.test.model.Post;
import com.bancom.test.model.Usuario;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts(Integer userId);
    Post createPost(Post post, Integer userId);
    Post updatePost(Post post, Integer userId, Integer postId);
    void deletePost(Integer userId, Integer postId);
}
