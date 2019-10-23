package com.lexiaoyao.service.impl;

import com.lexiaoyao.dao.TopicDao;
import com.lexiaoyao.model.BusinessException;
import com.lexiaoyao.model.ErrorType;
import com.lexiaoyao.model.mongo_po.Topic;
import com.lexiaoyao.service.TopicService;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {


    @Autowired
    private TopicDao topicDao;

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
        return Optional.ofNullable(topicDao.findOne(new Query().addCriteria(Criteria.where("name").is(name)))).isPresent();
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
}
