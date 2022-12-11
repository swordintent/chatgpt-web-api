package org.swordintent.chatgpt.protocol;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public class ChatRequest {

    private String prompt;
    @SerializedName("conversation_id")
    private String conversationId;
    @SerializedName("parent_id")
    private String parentId;

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
