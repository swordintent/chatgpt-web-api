# chatgpt-web-api
**A Java Version ChatGPT SDK** 

integrate with [acheong08/ChatGPT](https://github.com/acheong08/ChatGPT), use official Api of openAI(2023.2.4).

# update

`2023.2.14 for the acheong08/ChatGPT update reason. you need use this project in this step(the model is not free, but you have $18 free quota)`

```
pip3 install --upgrade revChatGPT
```

# How TO

[中文](https://github.com/swordintent/chatgpt-web-api/wiki/%E7%AE%80%E4%BB%8B)

### Start a python server(python >= 3.7)

you will find it in `src/main/resources/server.py`, and run:

```
    pip3 install flask flask-restful
    pip3 install --upgrade revChatGPT
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

* you need [create](https://platform.openai.com/) your account firstly.
* modify `password`, the `password` is your openAI's api-keys, you can find [here](https://platform.openai.com/account/api-keys).
* set `address` to http://127.0.0.1:5000 or another.


```
    ChatgptClient chatgptClient = ChatgptClientImpl.getInstance();
    ChatGptConfig chatGptConfig = ChatGptConfig.builder()
                                    .password("")
                                    .build();
    String address = "http://127.0.0.1:5000";
    chatgptClient.init(address, chatGptConfig);
```

2. then you can invoke chat `chatgptClient.chat(request)` method to chat. 

* in first round chat, `conversationId` would be null. 
when you want reset multiple rounds set them to null too.


```
    //first round or reset multiple rounds
    ChatRequest request = ChatRequest.builder()
                                    .prompt(content)
                                    .conversationId(null)
                                    .build();
    
    ChatResponse response = chatgptClient.chat(request);

```


* if you want to chat multiple rounds. you need get `conversationId` from response and set them to next chat request. 


```
    //multiple rounds  
    ChatRequest request = ChatRequest.builder()
                                    .prompt(content)
                                    .conversationId(response.getConversationId())
                                    .build();
    ChatResponse response = chatgptClient.chat(request);
```


3. **Notice**.

* the `conversationId` now is the full object of python chatbot object, so maybe it was huge. 

* you must set `conversationId` to null in your java program when you restart your python server.



