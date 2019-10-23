package com.lexiaoyao.service;

import com.lexiaoyao.model.mongo_po.Topic;
import com.mongodb.WriteResult;

import java.util.List;

public interface TopicService {

    void insert(Topic topic);

    List<Topic> listAll();

    Boolean isExist(String name);

    WriteResult delete(String name);

    Topic modify(Topic topic);

}
