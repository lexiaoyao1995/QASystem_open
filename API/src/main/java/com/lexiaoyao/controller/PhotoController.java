package com.lexiaoyao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexiaoyao.model.mongo_po.FileInfo;
import com.lexiaoyao.service.PhotoService;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @PostMapping("/album")
    public ResponseEntity insertAlbum(String name, String desc) {
        photoService.createAlbum(name, desc);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{albumId}")
    public ResponseEntity insertPhoto(@PathVariable("albumId") String albumId, @RequestParam(value = "file") MultipartFile file) {
        FileInfo save = photoService.save(file, albumId);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity getPhotosByAlbum(@PathVariable("albumId") String albumId) {
        List<FileInfo> photosByAlbumId = photoService.getPhotosByAlbumId(albumId);
        return ResponseEntity.ok(photosByAlbumId);
    }

    @GetMapping("/{photoId}")
    public void getPhoto(@PathVariable("photoId") String photoId, HttpServletResponse response) {
        GridFSDBFile file = photoService.getPhotoById(photoId);
        if (Objects.isNull(file)) {
            responseFail("404 not found", response);
            return;
        }
        try (OutputStream os = response.getOutputStream()) {
            response.addHeader("Content-Disposition", "attachment;filename=" + file.getFilename());
            response.addHeader("Content-Length", "" + file.getLength());
            response.setContentType(file.getContentType());
            file.writeTo(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
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

}
