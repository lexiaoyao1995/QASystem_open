package com.lexiaoyao.service;

import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;

public interface TalkService {

    MessageResponse talk(String question);
}
