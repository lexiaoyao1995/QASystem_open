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
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "topic_article")
public class TopicArticle {
    @Id
    @JsonProperty("_id")
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;

    @DBRef
    private Article article;

    @DBRef
    private Topic topic;

    public TopicArticle(Topic topic, Article article) {
        this.topic = topic;
        this.article = article;
        this.id = new ObjectId();
    }

}
