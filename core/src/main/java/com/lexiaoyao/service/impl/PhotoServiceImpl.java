package com.lexiaoyao.service.impl;

import com.lexiaoyao.dao.AlbumDao;
import com.lexiaoyao.dao.AlbumPhotoDao;
import com.lexiaoyao.model.BusinessException;
import com.lexiaoyao.model.ErrorType;
import com.lexiaoyao.model.mongo_po.Album;
import com.lexiaoyao.model.mongo_po.AlbumPhoto;
import com.lexiaoyao.model.mongo_po.FileInfo;
import com.lexiaoyao.service.PhotoService;
import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private AlbumPhotoDao albumPhotoDao;

    private GridFsTemplate gridFsTemplate = null;

    @PostConstruct
    public void init() {
        gridFsTemplate = new GridFsTemplate(mongoDbFactory, mongoTemplate.getConverter());
    }

    @Override
    public FileInfo save(MultipartFile photo, String albumId, Boolean isCover) {

        if (isCover && !Objects.isNull(getCover(albumId))) {
            throw new BusinessException(ErrorType.COVER_ALREADY_EXIST);
        }

        GridFS gridFS = new GridFS(mongoDbFactory.getDb());
        try (InputStream inputStream = photo.getInputStream()) {
            String filename = photo.getOriginalFilename();
            GridFSInputFile file = gridFS.createFile(inputStream);
            file.setFilename(filename);
            file.setContentType(file.getContentType());
            file.save();
            AlbumPhoto albumPhoto = new AlbumPhoto();
            albumPhoto.setFsFile(convert(file));
            if (isCover)
                albumPhoto.getFsFile().setIsCover(true);
            albumPhoto.setAlbum(albumDao.findOne(new Query().addCriteria(Criteria.where("_id").is(new ObjectId(albumId)))));
            albumPhoto.setId(new ObjectId());
            albumPhotoDao.insert(albumPhoto);
            return albumPhoto.getFsFile();
        } catch (Exception e) {
            throw new BusinessException(ErrorType.UPLOAD_FAILED);
        }
    }


    @Override
    public GridFSDBFile getPhotoById(String fileId) {
        GridFS gridFS = new GridFS(mongoDbFactory.getDb());
        return gridFS.findOne(new BasicDBObject("_id", new ObjectId(fileId)));
    }

    @Override
    public void removePhoto(String id) {

    }

    @Override
    public void createAlbum(String albumName, String desc) {
        albumDao.insert(new Album(albumName, desc));
    }

    @Override
    public List<FileInfo> getPhotosByAlbumId(String id) {
        List<AlbumPhoto> all = albumPhotoDao.findAll(new Query().addCriteria(Criteria.where("album.$id").is(new ObjectId(id))));
        List<FileInfo> collect = all.stream().map(AlbumPhoto::getFsFile).collect(Collectors.toList());

        return collect;
    }

    @Override
    public FileInfo getCover(String albumId) {
        AlbumPhoto one = albumPhotoDao.findOne(new Query().addCriteria(Criteria.where("album.$id").is(new ObjectId(albumId)).and("fsFile.isCover").is(true)));
        return Optional.ofNullable(one).map(AlbumPhoto::getFsFile).orElse(null);
    }

    @Override
    public List<Album> listAlbums() {
        return albumDao.findAll(null);
    }

    private FileInfo convert(GridFSInputFile file) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilename(file.getFilename());
        fileInfo.setId((ObjectId) file.getId());
        fileInfo.setLength(file.getLength());
        fileInfo.setUploadData(new Date());
        return fileInfo;


    }
}
