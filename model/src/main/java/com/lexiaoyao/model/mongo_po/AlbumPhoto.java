package com.lexiaoyao.model.mongo_po;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lexiaoyao.model.ObjectIdDeserializer;
import com.lexiaoyao.model.ObjectIdSerializer;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "album_photo")
public class AlbumPhoto {

    @Id
    @JsonProperty("_id")
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;

    @DBRef
    private Album album;

    private FileInfo fsFile;//图片

}
