package com.example.imcommunity.service;

import com.example.imcommunity.entity.Reply;
import com.example.imcommunity.model.ReplyFrom;

public interface ReplyService {
    Reply create(ReplyFrom replyFrom);
}
