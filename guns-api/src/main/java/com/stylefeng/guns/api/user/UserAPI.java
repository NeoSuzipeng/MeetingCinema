package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;

/**
 * Created by su on 2019/4/23.
 * 用户模块API接口
 *
 */
public interface UserAPI {

    /**
     * 用户登录接口
     * @param userName
     * @param password
     * @return 用户ID （验证登录成功后返回JWT内用户ID,每次访问系统都查找缓存来检验是否登录）
     */
    int login(String userName, String password);

    /**
     * 用户注册接口
     * @param user
     * @return
     */
    boolean register(UserModel user);

    /**
     * 用户名合法性接口
     * @param userName
     * @return
     */
    boolean checkUserName(String userName);

    /**
     * 用户信息获取接口
     * @param uuid
     * @return
     */
    UserInfoModel getUserInfo(int uuid);

    /**
     * 用户信息更新接口
     *
     * 注意：需要JWT验证
     * @param userInfoModel
     * @return
     */
    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);
}
