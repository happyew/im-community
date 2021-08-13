package com.example.imcommunity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionPageDTO {
    private Integer previousNumber;
    private Integer currentNumber;
    private Integer nextNumber;
    private Integer startNumber;
    private Integer endNumber;
    List<QuestionDTO> questionDTOList= new ArrayList<>();
}
