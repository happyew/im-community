package com.example.imcommunity.service;

import com.example.imcommunity.entity.Reply;
import com.example.imcommunity.model.ReplyForm;

public interface ReplyService {
    Reply create(ReplyForm replyForm);
}
