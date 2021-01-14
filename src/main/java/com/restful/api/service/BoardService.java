package com.restful.api.service;

import com.restful.api.config.CacheKey;
import com.restful.api.entity.Board;
import com.restful.api.entity.Post;
import com.restful.api.entity.User;
import com.restful.api.exception.CUserNotFoundException;
import com.restful.api.model.PostDto;
import com.restful.api.repository.BoardRepository;
import com.restful.api.repository.PostRepository;
import com.restful.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CacheService cacheService;

    public Board insertBoard(String boardName) {
        return boardRepository.save(Board.builder().name(boardName).build());
    }

    @Cacheable(value = CacheKey.BOARD, key = "#boardName", unless = "#result == null")
    public Board findBoard(String boardName) {
        return Optional.ofNullable(boardRepository.findByName(boardName)).orElseThrow(CUserNotFoundException::new);
    }

    // 게시판 이름으로 게시글 리스트 조회.
    @Cacheable(value = CacheKey.POSTS, key = "#boardName", unless = "#result == null")
    public List<Post> findPosts(String boardName) {
        return postRepository.findByBoard(findBoard(boardName));
    }

    // 게시글ID로 게시글 단건 조회. 없을경우 CResourceNotExistException 처리
    @Cacheable(value = CacheKey.POST, key = "#postId", unless = "#result == null")
    public Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(CUserNotFoundException::new);
    }

    // 게시글을 등록합니다. 게시글의 회원UID가 조회되지 않으면 CUserNotFoundException 처리합니다.
    @CacheEvict(value = CacheKey.POSTS, key = "#boardName")
    public Post writePost(String uid, String boardName, PostDto paramsPost) {
        Board board = findBoard(boardName);
        Post post = new Post(userRepository.findByUid(uid).orElseThrow(CUserNotFoundException::new), board, paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
        return postRepository.save(post);
    }

    // 게시글을 수정합니다. 게시글 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    public Post updatePost(long postId, String uid, PostDto paramsPost) {
        Post post = getPost(postId);
        User user = post.getUser();

        post.setUpdate(paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
        cacheService.deleteBoardCache(post.getPostId(), post.getBoard().getName());
        return post;
    }

    // 게시글을 삭제합니다. 게시글 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    public boolean deletePost(long postId, String uid) {
        Post post = getPost(postId);
        User user = post.getUser();

        postRepository.delete(post);
        return true;
    }
}

