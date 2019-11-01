package com.lexiaoyao.controller;

import com.lexiaoyao.model.mongo_po.Article;
import com.lexiaoyao.model.mongo_po.Topic;
import com.lexiaoyao.model.mongo_po.TopicArticle;
import com.lexiaoyao.service.TopicService;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/{topicId}")
    public ResponseEntity insertArticle(@PathVariable("topicId") String topicId, @RequestBody Article article) {
        article.setCreateTime(new Date());
        article.setId(new ObjectId());
        TopicArticle topicArticle = topicService.insertArticle(topicService.getTopicById(topicId), article);
        return ResponseEntity.ok(topicArticle);
    }

    @GetMapping("/{topicId}")
    public ResponseEntity getArticles(@PathVariable("topicId") String topicId) {
        List<Article> articlesByTopicId = topicService.getArticlesByTopicId(topicId);
        return ResponseEntity.ok(articlesByTopicId);
    }


    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody Topic topic, BindingResult result) {
        if (result.hasErrors()) {
            List<String> collect = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(collect);
        }
        topicService.insert(topic);
        return ResponseEntity.ok(topic);
    }

    @GetMapping
    public ResponseEntity listAll() {
        return ResponseEntity.ok(topicService.listAll());
    }

    @PutMapping
    public ResponseEntity modify(@Valid @RequestBody Topic topic, BindingResult result) {
        if (result.hasErrors()) {
            List<String> collect = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(collect);
        }
        return ResponseEntity.ok(topicService.modify(topic));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        WriteResult delete = topicService.delete(name);
        return ResponseEntity.ok(delete);
    }

}
