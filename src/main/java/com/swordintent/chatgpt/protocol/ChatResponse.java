package com.swordintent.chatgpt.protocol;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ChatResponse {

    private String message;
    @SerializedName("conversation_id")
    private String conversationId;
    @SerializedName("parent_id")
    private String parentId;

}
