package com.swordintent.chatgpt;

import com.swordintent.chatgpt.protocol.ChatGptConfig;
import com.swordintent.chatgpt.protocol.ChatRequest;
import com.swordintent.chatgpt.protocol.ChatResponse;

public interface ChatgptClient {
    /**
     * invoke carefully, only when your app start or chat error, you need invoke
     */
    void init(String agentAddress, ChatGptConfig config) throws Exception;

    ChatResponse chat(ChatRequest request) throws Exception;
}
