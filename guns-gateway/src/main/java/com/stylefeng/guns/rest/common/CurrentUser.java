package com.stylefeng.guns.rest.common;


/**
 * Created by su on 2019/4/23.
 * 保存已登录用户信息（类似于缓存），线程绑定
 */
public class CurrentUser {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

//    public static void saveUserInfo(UserInfoModel userInfoModel){  //浪费内存
//        threadLocal.set(userInfoModel);
//    }
//
//    public static UserInfoModel getUserInfo(){
//        return threadLocal.get();
//    }

    public static void saveUserId(String  userId){
        threadLocal.set(userId);
    }

    public static String getUserId(){
        return threadLocal.get();
    }

}
