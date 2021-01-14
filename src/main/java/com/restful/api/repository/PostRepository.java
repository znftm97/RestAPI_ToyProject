package com.restful.api.repository;

import com.restful.api.entity.Board;
import com.restful.api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByBoard(Board board);
}
