package org.swordintent.chatgpt;

import org.swordintent.chatgpt.client.DataClient;
import org.swordintent.chatgpt.client.TokenInterceptor;
import org.swordintent.chatgpt.protocol.ChatGptConfig;
import org.swordintent.chatgpt.protocol.ChatRequest;
import org.swordintent.chatgpt.protocol.ChatResponse;

public class ChatgptClientImpl implements ChatgptClient{

    private static final ChatgptClientImpl instance = new ChatgptClientImpl();

    private ChatgptClientImpl(){

    }

    public static ChatgptClientImpl getInstance(){
        return instance;
    }

    private static final String LOGIN_URL_PATH = "%s/login";
    private static final String CHAT_URL_PATH = "%s/chat";

    private String loginUrlPath;
    private String chatUrlPath;

    private DataClient client;

    private TokenInterceptor tokenInterceptor;

    @Override
    public void init(String agentAddress, ChatGptConfig config) throws Exception {
        initUrls(agentAddress);
        initClient(config);
        login();
    }

    @Override
    public ChatResponse chat(ChatRequest request) throws Exception {
        return client.getData(request, chatUrlPath, ChatResponse.class);
    }

    private void initClient(ChatGptConfig config) {
        tokenInterceptor = new TokenInterceptor(config);
        client = new DataClient(tokenInterceptor);
        client.init();
    }

    private void initUrls(String agentAddress) {
        loginUrlPath = String.format(LOGIN_URL_PATH, agentAddress);
        chatUrlPath = String.format(CHAT_URL_PATH, agentAddress);
    }

    private void login() throws Exception {
        ChatGptConfig loginConfig = client.getData(this.loginUrlPath, ChatGptConfig.class);
        tokenInterceptor.setChatGptConfig(loginConfig);
    }
}
