package com.example.imcommunity.repository;

import com.example.imcommunity.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Question实体类JPA接口
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}