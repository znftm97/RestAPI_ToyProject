package com.restful.api.repository;

import com.restful.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.uid = :uid")
    Optional<User> findByUid(@Param("uid") String uid);
}
