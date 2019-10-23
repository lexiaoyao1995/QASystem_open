package com.lexiaoyao.dao;

import com.lexiaoyao.model.mongo_po.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDao extends MongoBaseDao<Topic> {
    @Autowired
    TopicDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}
