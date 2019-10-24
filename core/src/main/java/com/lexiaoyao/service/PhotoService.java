package com.lexiaoyao.service;

import com.lexiaoyao.model.mongo_po.FileInfo;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    FileInfo save(MultipartFile photo, String albumId);

    GridFSDBFile getPhotoById(String fileId);

    void removePhoto(String id);

    void createAlbum(String albumName, String desc);

    List<FileInfo> getPhotosByAlbumId(String id);


}
