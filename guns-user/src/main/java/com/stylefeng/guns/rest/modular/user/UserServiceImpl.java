package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MeetUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MeetUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by 10353 on 2019/4/23.
 * 用户模块服务提供者
 */
@Component
@Service(interfaceClass = UserAPI.class)
public class UserServiceImpl implements UserAPI{

    @Autowired
    private MeetUserTMapper meetUserTMapper;

    @Override
    public int login(String userName, String password) {
        // 根据登录账号获取数据库信息
        MeetUserT meetUserT = new MeetUserT();
        meetUserT.setUserName(userName);
        MeetUserT result = meetUserTMapper.selectOne(meetUserT);
        // 根据结果与加密后的密码进行对比
        if(result != null && result.getUuid() > 0){
            String md5Password = MD5Util.encrypt(password);
            if (result.getUserPwd().equals(md5Password))
                return  result.getUuid();

        }
        return 0;
    }

    @Override
    public boolean register(UserModel user) {
        // 将注册信息实体转化为数据实体
        MeetUserT meetUserT = new MeetUserT();
        meetUserT.setUserName(user.getUsername());

        String md5Password = MD5Util.encrypt(user.getPassword());
        meetUserT.setUserPwd(md5Password);
        meetUserT.setAddress(user.getAddress());
        meetUserT.setUserPhone(user.getPhone());
        meetUserT.setEmail(user.getEmail());
        // 数据实体存入数据库
        Integer effectRows = meetUserTMapper.insert(meetUserT);
        return effectRows > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 检查用户名是否存在
     * @param userName
     * @return Boolean.FALSE 用户名存在
     *         Boolean.True 用户名不存在
     */
    @Override
    public boolean checkUserName(String userName) {
        EntityWrapper<MeetUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", userName);
        int effectRows = meetUserTMapper.selectCount(entityWrapper);
        return effectRows > 0 ? Boolean.FALSE : Boolean.TRUE ;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        // 根据uuid查询用户信息
        MeetUserT meetUserT = meetUserTMapper.selectById(uuid);
        // MeetUserT转化为UserInfoModel
        UserInfoModel user = meetUserModelIntoUserInfoModel(meetUserT);
        return user;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        MeetUserT meetUserT = new MeetUserT();

        meetUserT.setUuid(userInfoModel.getUuid());
        meetUserT.setUserName(userInfoModel.getUsername());
        meetUserT.setEmail(userInfoModel.getEmail());
        meetUserT.setAddress(userInfoModel.getAddress());
        meetUserT.setBirthday(userInfoModel.getBirthday());
        meetUserT.setBiography(userInfoModel.getBiography());
        meetUserT.setHeadUrl(userInfoModel.getHeadAddress());
        meetUserT.setBeginTime(null);
        meetUserT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        meetUserT.setNickName(userInfoModel.getNickname());
        meetUserT.setUserPhone(userInfoModel.getPhone());
        meetUserT.setUserSex(userInfoModel.getSex());
        meetUserT.setUpdateTime(timeToDate(System.currentTimeMillis()));

        int isSuccess = meetUserTMapper.updateById(meetUserT);
        if(isSuccess > 0){
            UserInfoModel userInfo = getUserInfo(meetUserT.getUuid());
            return userInfo;       // 返回更新后的用户信息
        }
        return userInfoModel;       // 返回更新前用户信息但不入库
    }

    private Date timeToDate(long time){
        Date date = new Date(time);
        return date;
    }

    /**
     * 数据层模型转换为业务层模型
     * @param meetUserT
     * @return
     */
    private UserInfoModel meetUserModelIntoUserInfoModel(MeetUserT meetUserT){
        UserInfoModel userInfoModel = new UserInfoModel();

        userInfoModel.setUuid(meetUserT.getUuid());
        userInfoModel.setUsername(meetUserT.getUserName());
        userInfoModel.setSex(meetUserT.getUserSex());
        userInfoModel.setEmail(meetUserT.getEmail());
        userInfoModel.setAddress(meetUserT.getAddress());
        userInfoModel.setUpdateTime(meetUserT.getUpdateTime().getTime());
        userInfoModel.setBeginTime(meetUserT.getBeginTime().getTime());
        userInfoModel.setBiography(meetUserT.getBiography());
        userInfoModel.setBirthday(meetUserT.getBirthday());
        userInfoModel.setLifeState(""+meetUserT.getLifeState());
        userInfoModel.setNickname(meetUserT.getNickName());
        userInfoModel.setPhone(meetUserT.getUserPhone());
        userInfoModel.setHeadAddress(meetUserT.getHeadUrl());

        return userInfoModel;
    }


    //TODO 注销用户


}
