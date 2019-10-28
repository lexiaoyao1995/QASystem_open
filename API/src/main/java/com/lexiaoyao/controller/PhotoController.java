package com.lexiaoyao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexiaoyao.model.mongo_po.Album;
import com.lexiaoyao.model.mongo_po.FileInfo;
import com.lexiaoyao.service.PhotoService;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @GetMapping("/albums")
    public ResponseEntity getAlbums() {

        List<Album> albums = photoService.listAlbums();
        List<AlbumWithCover> list = new LinkedList<>();
        for (Album album : albums) {
            FileInfo cover = photoService.getCover(album.getId().toString());
            list.add(new AlbumWithCover(album, cover));
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/album")
    public ResponseEntity insertAlbum(String name, String desc) {
        photoService.createAlbum(name, desc);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{albumId}")
    public ResponseEntity insertPhoto(@PathVariable("albumId") String albumId, @RequestParam(value = "file") MultipartFile file) {
        FileInfo save = photoService.save(file, albumId, false);
        return ResponseEntity.ok(save);
    }

    @PostMapping("/{albumId}/cover")
    public ResponseEntity insertCover(@PathVariable("albumId") String albumId, @RequestParam(value = "file") MultipartFile file) {
        FileInfo save = photoService.save(file, albumId, true);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/{albumId}/cover")
    public ResponseEntity getCover(@PathVariable("albumId") String albumId) {
        return ResponseEntity.ok(photoService.getCover(albumId));

    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity getPhotosByAlbum(@PathVariable("albumId") String albumId) {
        List<FileInfo> photosByAlbumId = photoService.getPhotosByAlbumId(albumId);
        return ResponseEntity.ok(photosByAlbumId);
    }

    /**
     * 下载图片
     *
     * @param photoId
     * @param response
     */
    @PatchMapping("/{photoId}")
    public void getPhoto(@PathVariable("photoId") String photoId, HttpServletResponse response) {
        GridFSDBFile file = photoService.getPhotoById(photoId);
        if (Objects.isNull(file)) {
            responseFail("404 not found", response);
            return;
        }

        try (OutputStream os = response.getOutputStream()) {
            response.addHeader("Content-Disposition", "attachment;filename=" + file.getFilename());
            response.addHeader("Content-Length", "" + file.getLength());
            response.addHeader("Content-Type", "image/jpg");
            response.setContentType(file.getContentType());
            file.writeTo(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 预览图片
     *
     * @param photoId
     * @param response
     */
    @GetMapping("/{photoId}")
    public void viewPhoto(@PathVariable("photoId") String photoId, HttpServletResponse response) {
        GridFSDBFile file = photoService.getPhotoById(photoId);
        if (Objects.isNull(file)) {
            responseFail("404 not found", response);
            return;
        }
        try {
            InputStream is = file.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] imagenEnBytes = buffer.toByteArray();
            response.setHeader("Accept-ranges", "bytes");
            response.setContentType("image/jpeg");
            response.setContentLength(imagenEnBytes.length);
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Content-Description", "File Transfer");
            response.setHeader("Content-Transfer-Encoding:", "binary");
            OutputStream out = response.getOutputStream();
            out.write(imagenEnBytes);
            out.flush();
            out.close();
        } catch (Exception e) {

        }

    }

    private void responseFail(String msg, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String res = mapper.writeValueAsString(msg);
            out = response.getWriter();
            out.append(res);
        } catch (Exception e) {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
            }
        }
    }

    private class AlbumWithCover {
        private Album album;
        private FileInfo cover;

        public AlbumWithCover(Album album, FileInfo cover) {
            this.album = album;
            this.cover = cover;
        }

        public AlbumWithCover() {
        }

        public Album getAlbum() {
            return album;
        }

        public void setAlbum(Album album) {
            this.album = album;
        }

        public FileInfo getCover() {
            return cover;
        }

        public void setCover(FileInfo cover) {
            this.cover = cover;
        }
    }
}
