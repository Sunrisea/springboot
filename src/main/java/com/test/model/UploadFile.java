package com.test.model;

import java.util.Date;

import org.bson.types.Binary;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "uploadfile")
public class UploadFile {

    @Id
    private String id;

    @Field("username")
    private String username;

    @Field("name")
    private String name;

    @Field("createdTime")
    private Date createdTime;

    @Field("content")
    private Binary content;

    @Field("contentType")
    private String contentType;

    @Field("size")
    private long size;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Binary getContent() {
        return content;
    }
    public void setContent(Binary content) {
        this.content = content;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
}