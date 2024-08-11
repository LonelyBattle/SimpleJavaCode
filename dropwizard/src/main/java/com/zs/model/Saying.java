package com.zs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Saying {
    private String content;

    public Saying() {
        // Jackson需要默认构造函数
    }

    public Saying(String content) {
        this.content = content;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    @JsonProperty
    public void setContent(String content) {
        this.content = content;
    }
}
