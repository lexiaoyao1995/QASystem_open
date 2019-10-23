package com.lexiaoyao.dao;

import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class MongoBaseDao<T> {

    private final Class<T> typeClass;

    protected final MongoTemplate mongoTemplate;

    public MongoBaseDao(MongoTemplate mongoTemplate) {
        this.typeClass = getTypeClass();
        this.mongoTemplate = mongoTemplate;
    }

    /*=***************查询方法*****************/

    public T findById(String id) {
        return mongoTemplate.findById(new ObjectId(id), typeClass);
    }

    public T findOne(Query query) {
        return mongoTemplate.findOne(query, typeClass);
    }

    public List<T> findAll(Query query) {
        return mongoTemplate.find(query, typeClass);
    }

    public T findByStringField(String fieldName, String fieldValue) {
        return mongoTemplate.findOne(getFieldQuery(fieldName, fieldValue), typeClass);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, typeClass);
    }

    public Boolean exists(Query query) {
        return mongoTemplate.exists(query, typeClass);
    }

    /*=***************插入方法*****************/

    public void save(T t) {
        mongoTemplate.save(t);
    }

    public void insert(T t) {
        mongoTemplate.insert(t);
    }

    public void batchInsert(List<T> listObj) {
        mongoTemplate.insert(listObj, typeClass);
    }

    /*=***************删除方法*****************/

    public void removeById(String id) {
        mongoTemplate.remove(getFieldQuery("_id", id), typeClass);
    }

    public void removeOne(T t) {
        mongoTemplate.remove(t);
    }

    public WriteResult remove(Query query) {
        return mongoTemplate.remove(query, typeClass);
    }

    /*=***************更新方法*****************/

    public WriteResult update(Query query, Update update) {
        return mongoTemplate.updateFirst(query, update, typeClass);
    }

    public WriteResult updateMulti(Query query, Update update) {
        return mongoTemplate.updateMulti(query, update, typeClass);
    }

    public T findAndModify(Query query, Update update, FindAndModifyOptions findAndModifyOptions) {
        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, typeClass);
    }

    private Query getFieldQuery(String fieldName, Object fieldValue) {
        return new Query(Criteria.where(fieldName).is(fieldValue));
    }

    private Class<T> getTypeClass() {
        Type genericSuperClass = getClass().getGenericSuperclass();

        ParameterizedType parametrizedType = null;
        while (parametrizedType == null) {
            if ((genericSuperClass instanceof ParameterizedType)) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }

        return (Class<T>) parametrizedType.getActualTypeArguments()[0];
    }
}
