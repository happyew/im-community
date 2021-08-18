package com.example.imcommunity.service;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.QuestionFrom;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * question业务层接口
 */
public interface QuestionService {
    /**
     * 查找所有问题DTO
     *
     * @return 问题DTO
     */
    List<QuestionDTO> findAll();

    /**
     * 分页查找所有问题的PageDTO
     *
     * @param page 页码
     * @param size 单页长度
     * @param sort 排序
     * @return 分页DTO
     */
    QuestionPageDTO findAllPage(Integer page, Integer size, Sort sort);

    /**
     * 分页查找对应Gitee用户的问题PageDTO
     *
     * @param page      页码
     * @param size      单页长度
     * @param sort      排序
     * @return 分页DTO
     */
    QuestionPageDTO findPageByUser(Integer page, Integer size, Sort sort, User user);

    /**
     * 保存问题
     *
     * @param question 问题
     * @return 已保存的问题
     */
    Question save(Question question);

    /**
     * 根据id查找问题DTO
     *
     * @param id 问题id
     * @return 问题DTO
     */
    QuestionDTO findQuestionDTOById(Long id);

    /**
     * 根据id查找问题D
     *
     * @param id 问题id
     * @return 问题
     */
    Question findQuestionById(Long id);

    void viewIncrement(Long id);

    Question update(QuestionFrom questionFrom);

    Question create(QuestionFrom questionFrom);
}
