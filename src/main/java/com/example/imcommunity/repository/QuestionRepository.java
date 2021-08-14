package com.example.imcommunity.repository;

import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Question实体类JPA接口
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByGiteeUser(GiteeUser giteeUser, Pageable pageable);
}