package com.lexiaoyao.controller;

import com.lexiaoyao.model.mongo_po.Topic;
import com.lexiaoyao.service.TopicService;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody Topic topic, BindingResult result) {
        if (result.hasErrors()) {
            List<String> collect = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(collect);
        }
        topicService.insert(topic);
        return ResponseEntity.ok(topic);
    }

    @GetMapping("/guest")
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
