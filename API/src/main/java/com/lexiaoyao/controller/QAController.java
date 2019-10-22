package com.lexiaoyao.controller;

import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.lexiaoyao.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qa")
public class QAController {
    @Autowired
    private TalkService talkService;

    @PostMapping("question")
    public ResponseEntity question(String question) {
        MessageResponse hello = talkService.talk(question);
        return ResponseEntity.ok(hello);
    }
}
