package com.lexiaoyao.service;

import com.lexiaoyao.model.mongo_po.Article;
import com.lexiaoyao.model.mongo_po.Topic;
import com.lexiaoyao.model.mongo_po.TopicArticle;
import com.mongodb.WriteResult;

import java.util.List;

public interface TopicService {

    void insert(Topic topic);

    List<Topic> listAll();

    Boolean isExist(String topicName);

    Topic getTopicByName(String topicName);

    Topic getTopicById(String id);

    WriteResult delete(String name);

    Topic modify(Topic topic);

    TopicArticle insertArticle(Topic topic, Article article);

    List<Article> getArticlesByTopicId(String topicId);

}
