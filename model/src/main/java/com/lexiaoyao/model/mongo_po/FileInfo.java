package com.lexiaoyao.model.mongo_po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lexiaoyao.model.ObjectIdDeserializer;
import com.lexiaoyao.model.ObjectIdSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileInfo implements Serializable {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;

    private String filename;

    private Long length;

    private Date uploadData;

    private Boolean isCover = false;
}
