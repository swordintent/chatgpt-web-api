# chatgpt-web-api
**A Java Version ChatGPT SDK**

integrate with [acheong08/ChatGPT](https://github.com/acheong08/ChatGPT)

# How TO

### Start a python server(python >= 3.7)

you will find it in `src/main/resources/server.py`, and `acheong08/ChatGPT` is active，you need run `pip3 install revChatGPT --upgrade` frequently.

```
    pip3 install flask flask-restful
    pip3 install revChatGPT --upgrade
    python3 server.py
```

by default, it listen on http://127.0.0.1:5000 


### Import maven jar

https://search.maven.org/artifact/com.swordintent.chatgpt/web-api/

Maven
```
    <dependency>
      <groupId>com.swordintent.chatgpt</groupId>
      <artifactId>web-api</artifactId>
      <version>1.0.0</version>
    </dependency>
```

Gradle

```
    implementation 'com.swordintent.chatgpt:web-api:1.0.0'
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

* Advanced: you also can set `session_token` or `Authorization` when you init, for OpenAI update auth reasons, it's not recommend.

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

* For one account to support multi user and multiple rounds reason, when you invoke `chatgptClient.chat(request)`, we will **DO NOT** log in openai every times, we will **ONLY** log in when you invoke `chatgptClient.init(address, chatGptConfig)` method, and then use it globally.

* So the login status may sometimes be invalidated by OpenAI, then you must invoke init method `chatgptClient.chat(request)` again.



