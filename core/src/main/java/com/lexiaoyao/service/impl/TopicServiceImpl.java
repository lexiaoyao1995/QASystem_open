package com.lexiaoyao.service.impl;

import com.lexiaoyao.dao.ArticleDao;
import com.lexiaoyao.dao.TopicArticleDao;
import com.lexiaoyao.dao.TopicDao;
import com.lexiaoyao.model.BusinessException;
import com.lexiaoyao.model.ErrorType;
import com.lexiaoyao.model.mongo_po.Article;
import com.lexiaoyao.model.mongo_po.Topic;
import com.lexiaoyao.model.mongo_po.TopicArticle;
import com.lexiaoyao.service.TopicService;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {


    @Autowired
    private TopicDao topicDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private TopicArticleDao topicArticleDao;

    @Override
    public void insert(Topic topic) {
        if (isExist(topic.getName())) {
            throw new BusinessException(ErrorType.TOPIC_EXIST);
        }
        topic.setCreateTime(new Date());

        topicDao.insert(topic);
    }

    @Override
    public List<Topic> listAll() {
        return topicDao.findAll(null);
    }

    @Override
    public Boolean isExist(String name) {
        return Optional.ofNullable(getTopicByName(name)).isPresent();
    }

    @Override
    public Topic getTopicByName(String topicName) {
        return topicDao.findOne(new Query().addCriteria(Criteria.where("name").is(topicName)));
    }

    @Override
    public Topic getTopicById(String id) {
        return topicDao.findOne(new Query().addCriteria(Criteria.where("_id").is(new ObjectId(id))));
    }

    @Override
    public WriteResult delete(String name) {
        return topicDao.remove(new Query().addCriteria(Criteria.where("name").is(name)));

    }

    @Override
    public Topic modify(Topic topic) {
        return topicDao.findAndModify(new Query().addCriteria(Criteria.where("_id").is(topic.getId())),
                new Update().set("name", topic.getName()).set("desc", topic.getDesc()), FindAndModifyOptions.options());
    }

    @Override
    public TopicArticle insertArticle(Topic topic, Article article) {
        articleDao.insert(article);
        TopicArticle topicArticle = new TopicArticle(topic, article);
        topicArticleDao.insert(topicArticle);
        return topicArticle;
    }

    @Override
    public List<Article> getArticlesByTopicId(String topicId) {
        List<TopicArticle> all = topicArticleDao.findAll(new Query().addCriteria(Criteria.where("topic.$id").is(new ObjectId(topicId))));
        return all.stream().map(i -> i.getArticle()).collect(Collectors.toList());
    }
}
