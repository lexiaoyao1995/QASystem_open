package com.lexiaoyao.model.mongo_po;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lexiaoyao.model.ObjectIdDeserializer;
import com.lexiaoyao.model.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "album")
public class Album {
    @Id
    @JsonProperty("_id")
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;


    @NotBlank(message = "主题名字不能为空")
    private String name;

    private String desc;

    private Date createTime;

    public Album(String name, String desc) {
        this.id = new ObjectId();
        this.name = name;
        this.desc = desc;
        this.createTime = new Date();
    }
}
