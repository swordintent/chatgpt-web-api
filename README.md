# chatgpt-web-api
**A Java Version ChatGPT SDK**

integrate with https://github.com/acheong08/ChatGPT 

# How TO

### Start a python server

you will find it in `src/main/resources/server.py`

```
    pip3 install revChatGPT --upgrade
    python3 server.py
```

by default, it listen on http://127.0.0.1:5000 


### Import maven jar


```
    <dependency>
      <groupId>com.swordintent.chatgpt</groupId>
      <artifactId>web-api</artifactId>
      <version>1.0.0</version>
    </dependency>
```

### Enjoy it in your project


1. first, you can invoke `chatgptClient.init(address, chatGptConfig)` method to init client.

* You need modify `email`,`password`, and `address`.

```
    ChatgptClient chatgptClient = ChatgptClientImpl.getInstance();
    ChatGptConfig chatGptConfig = ChatGptConfig.builder()
                                    .email("")
                                    .password("")
                                    .build();
    String address = "http://127.0.0.1:5000";
    chatgptClient.init(address, chatGptConfig);
```

* Advanced: you also can set `session_token` or `Authorization`, when you init, but it's not recommend.

2. then you can invoke chat `chatgptClient.chat(request)` method to chat. 

* in first round chat, `conversationId` and `parentId` can be null. 
when you want reset multiple rounds set them to null too.


```
    //first round or reset multiple rounds
    ChatRequest request = ChatRequest.builder()
                                    .prompt(content)
                                    .conversationId(null)
                                    .parentId(null)
                                    .build();
    
    ChatResponse response = chatgptClient.chat(request);

```


* if you want to chat multiple rounds. you need get `conversationId` and `parentId` from response and set them to next chat request. 


```
    //multiple rounds  
    ChatRequest request = ChatRequest.builder()
                                    .prompt(content)
                                    .conversationId(response.getConversationId())
                                    .parentId(response.getParentId())
                                    .build();
    ChatResponse response = chatgptClient.chat(request);
```


3. **Notice**.

* For multiple rounds reason, when you invoke `chatgptClient.chat(request)`, we will **DO NOT** log in openai every times.

* So the login status may sometimes be invalidated by openai, then you must invoke init method `chatgptClient.chat(request)` again.



