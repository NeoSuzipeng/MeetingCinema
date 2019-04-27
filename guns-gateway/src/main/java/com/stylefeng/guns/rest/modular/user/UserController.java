package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by su on 2019/4/24.
 * 用户模块后端控制器
 *
 */
@RequestMapping("/user/")
@RestController
public class UserController {

    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;



    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseVO register(UserModel user){
        //检验用户名和密码有效性
        if(!checkValidString(user.getUsername()))
            return ResponseVO.serviceFail("用户名不能为空");

        if(!checkValidString(user.getPassword()))
            return ResponseVO.serviceFail("密码不能为空");

        // 用户注册
        boolean isSuccess = userAPI.register(user);

        return isSuccess ? ResponseVO.Success("注册成功") : ResponseVO.serviceFail("注册失败");
    }



    @RequestMapping(value = "check", method = RequestMethod.POST)
    public ResponseVO checkUserName(String userName){
        //检验用户名有效性
        if(!checkValidString(userName))
            return ResponseVO.serviceFail("用户名不能为空");

        // 用户名检查
        boolean noExists = userAPI.checkUserName(userName);

        return noExists ? ResponseVO.Success("用户名不存在") : ResponseVO.serviceFail("用户名存在");
    }






    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ResponseVO logout(){
        /*
          应用：
            1.前端存储JWT（七天）：JWT刷新
            2.服务器会存储活动用户信息（30分钟）
            3.JWT里的UserId为key，查找活跃用户
          退出：
            1.前端删除JWT
            2.后端删除活跃用户缓存
          现状：
            1.前端删除JWT
         */
        //TODO Redis删除活跃用户
        return ResponseVO.Success("用户退出成功");
    }





    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public ResponseVO getUserInfo(){
        //获取当前用户
        String userId = CurrentUser.getUserId();
        if (!checkValidString(userId))
            return ResponseVO.serviceFail("用户未登录");
        //将用户ID传入后端进行查询
        int uuid = Integer.parseInt(userId);
        UserInfoModel userInfoModel = userAPI.getUserInfo(uuid);

        return userInfoModel != null ? ResponseVO.Success(userInfoModel) : ResponseVO.serviceFail("用户信息查询失败");
    }









    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public ResponseVO updateUserInfo(UserInfoModel user){
        //获取当前用户
        String userId = CurrentUser.getUserId();
        if (!checkValidString(userId))
            return ResponseVO.serviceFail("用户未登录");
        //将用户信息传入后端进行更新
        int uuid = Integer.parseInt(userId);
        //检验用户ID是否一致
        if(uuid != user.getUuid())
            return ResponseVO.serviceFail("请修改您个人的信息");

        UserInfoModel userInfoModel = userAPI.updateUserInfo(user);

        return userInfoModel != null ? ResponseVO.Success(userInfoModel) : ResponseVO.serviceFail("用户信息修改失败");
    }




    /**
     * 字符串有效性检查
     * @param param
     * @return
     */
    private boolean checkValidString(String param){
        if (param == null || param.trim().length() == 0)
            return Boolean.FALSE;
        return Boolean.TRUE;
    }

}
