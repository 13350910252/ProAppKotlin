package com.yinp.proappkotlin.study.wanAndroid.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class WanLoginBean implements Serializable {
    /**
     * admin : false
     * chapterTops : []
     * coinCount : 0
     * collectIds : [18192]
     * email :
     * icon :
     * id : 97982
     * nickname : yinp
     * password :
     * publicName : yinp
     * token :
     * type : 0
     * username : yinp
     */

    private boolean admin;
    private int coinCount;
    private String email;
    private String icon;
    private int id;
    private String nickname;
    private String password;
    private String publicName;
    private String token;
    private int type;
    private String username;
    private List<?> chapterTops;
    private List<Integer> collectIds;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<?> getChapterTops() {
        return chapterTops;
    }

    public void setChapterTops(List<?> chapterTops) {
        this.chapterTops = chapterTops;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }


    private static volatile int getFlag = 0, setFlag = 0;
    public static WanLoginBean userinfo;

    public static WanLoginBean getUserInfo(Context context) {
        if (userinfo == null || getFlag != setFlag) {
            SharedPreferences sp = context.getSharedPreferences("WanLoginBean", Context.MODE_PRIVATE);
            String value = sp.getString("userInfo", "{}");
            getFlag = setFlag;
            userinfo = stringToBean(value);
            return userinfo;
        }
        return userinfo;
    }

    public static void saveUserInfo(WanLoginBean userInfo, Context context) {
        if (userInfo == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences("WanLoginBean", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userInfo", jsonToString(userInfo));
        editor.apply();
        ++setFlag;
    }

    public static String jsonToString(WanLoginBean userInfo) {
        String value = null;
        try {
            Gson gson = new Gson();
            value = gson.toJson(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static WanLoginBean stringToBean(String value) {
        Gson gson = new Gson();
        WanLoginBean wanLoginBean = gson.fromJson(value, WanLoginBean.class);
        try {

        } catch (Exception e) {
            return new WanLoginBean();
        }
        return wanLoginBean;
    }

    public static void clear(Context context) {
        setFlag = 0;
        getFlag = 0;
        userinfo = null;
        SharedPreferences sp = context.getSharedPreferences("WanLoginBean", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userInfo", "{}");
        editor.apply();
    }

    public WanLoginBean() {
    }
}
