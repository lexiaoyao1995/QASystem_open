package com.lexiaoyao.dao;

import com.lexiaoyao.model.mongo_po.TopicArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TopicArticleDao extends MongoBaseDao<TopicArticle> {
    @Autowired
    TopicArticleDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}

