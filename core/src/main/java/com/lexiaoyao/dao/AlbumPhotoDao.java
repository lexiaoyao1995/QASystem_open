package com.lexiaoyao.dao;

import com.lexiaoyao.model.mongo_po.AlbumPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumPhotoDao extends MongoBaseDao<AlbumPhoto> {
    @Autowired
    AlbumPhotoDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }
}
