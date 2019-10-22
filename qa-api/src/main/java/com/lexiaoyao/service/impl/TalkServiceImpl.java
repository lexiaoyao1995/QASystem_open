package com.lexiaoyao.service.impl;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.lexiaoyao.common.ConstantInfo;
import com.lexiaoyao.service.TalkService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TalkServiceImpl implements TalkService {
    private Context context = null;

    private Assistant assistant = null;


    @PostConstruct
    protected void init() {
        IamOptions iamOptions = new IamOptions.Builder()
                .apiKey(ConstantInfo.apikey)
                .build();

        assistant = new Assistant("2018-08-30", iamOptions);
        assistant.setEndPoint(ConstantInfo.Url);
    }

    @Override
    public MessageResponse talk(String question) {
        InputData input = new InputData.Builder(question).build();
        MessageOptions options = new MessageOptions.Builder(ConstantInfo.workspaceId)
                .input(input).context(context)
                .build();
        return assistant.message(options).execute();
    }
}
