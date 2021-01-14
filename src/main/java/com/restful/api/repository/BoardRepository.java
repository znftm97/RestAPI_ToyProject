package com.restful.api.repository;

import com.restful.api.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findByName(String name);
}
