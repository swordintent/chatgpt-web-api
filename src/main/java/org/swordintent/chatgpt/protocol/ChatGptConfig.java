package org.swordintent.chatgpt.protocol;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public class ChatGptConfig {

    private String email;
    private String password;
    @SerializedName("session_token")
    private String sessionToken;
    private String proxy;
    @SerializedName("Authorization")
    private String authorization;

}
