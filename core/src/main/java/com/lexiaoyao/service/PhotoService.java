package com.lexiaoyao.service;

import com.lexiaoyao.model.mongo_po.Album;
import com.lexiaoyao.model.mongo_po.FileInfo;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    FileInfo save(MultipartFile photo, String albumId, Boolean isCover);

    GridFSDBFile getPhotoById(String fileId);

    Album createAlbum(String albumName, String desc);

    List<FileInfo> getPhotosByAlbumId(String id);

    FileInfo getCover(String albumId);

    List<Album> listAlbums();

    WriteResult removePhotoById(String photoId);

    Album getAlbumById(String albumId);

    WriteResult deleteAlbum(String albumId);


}
