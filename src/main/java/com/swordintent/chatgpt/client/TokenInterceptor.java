package com.swordintent.chatgpt.client;

import com.swordintent.chatgpt.protocol.ChatGptConfig;
import com.swordintent.chatgpt.utils.JsonUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author liuhe
 */
public class TokenInterceptor implements Interceptor {

    private ChatGptConfig chatGptConfig;

    public void setChatGptConfig(ChatGptConfig chatGptConfig) {
        this.chatGptConfig = chatGptConfig;
    }

    public TokenInterceptor(ChatGptConfig chatGptConfig) {
        this.chatGptConfig = chatGptConfig;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest;
        newRequest = request.newBuilder()
//                .addHeader("accept", "application/json")
                .addHeader("chatgpt-config", JsonUtils.toJson(chatGptConfig))
                .addHeader("Content-Type", "application/json")
                .build();
        return chain.proceed(newRequest);
    }
}
