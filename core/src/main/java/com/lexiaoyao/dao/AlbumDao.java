package com.lexiaoyao.dao;

import com.lexiaoyao.model.mongo_po.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumDao extends MongoBaseDao<Album> {

    @Autowired
    AlbumDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}
