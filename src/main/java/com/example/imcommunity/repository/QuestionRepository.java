package com.example.imcommunity.repository;

import com.example.imcommunity.entity.Question;
import com.example.imcommunity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Question实体类JPA接口
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByUser(User user, Pageable pageable);
}