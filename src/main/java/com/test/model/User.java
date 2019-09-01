package com.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


@Document(collection = "user")
public class User {

    @Id
    private String id;

    @Field("username")
    private String username;

    @Field("password")
    private String password;

    private String repassword;

    @Field("sex")
    private String sex;        //男,女，隐藏

    @Field("birthday")
    private Date birthday;        //出生日期

    @Field("region")
    private String region;        //地址

    @Field("state")
    private String state;      //用户状态，0为普通用户，1为禁言状态,2为管理员

    @Field("motto")
    private String motto;       //个性签名

    public User(String username,String password){
        this.username = username;
        this.password = password;
        this.sex = "隐藏";
        Calendar calendar=Calendar.getInstance();
        calendar.set(1990,Calendar.JANUARY,1,0,0,0);
        this.birthday = calendar.getTime();
        this.region = "北京";
        this.state = "0";
        this.motto = "个性签名";
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return "username:"+getUsername()+"\npassword:"+getPassword();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String personalSignature) {
        this.motto = personalSignature;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepassword() {
        return repassword;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
