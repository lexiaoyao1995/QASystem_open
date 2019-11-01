package com.lexiaoyao.dao;

import com.lexiaoyao.model.mongo_po.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDao extends MongoBaseDao<Article> {
    @Autowired
    public ArticleDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}
