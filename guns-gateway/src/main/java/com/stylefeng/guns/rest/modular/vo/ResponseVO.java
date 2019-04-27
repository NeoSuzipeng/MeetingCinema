package com.stylefeng.guns.rest.modular.vo;

import java.io.Serializable;

/**
 * Created by su on 2019/4/23.\
 * 前端交互模型
 */
public class ResponseVO<T> implements Serializable {

    //返回状态【0-成功, 1-业务失败, 999-表示系统异常】
    private int status;

    //前端消息通知
    private String msg;

    //前端返回数据
    private T data;

    //图片前缀
    private String imgPre;

    private int nowPage;

    private int totalPage;

    private ResponseVO(){}

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public static<T> ResponseVO Success(T data){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(data);
        return responseVO;
    }

    public static<T> ResponseVO Success(T data, String imgPre){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(data);
        responseVO.setImgPre(imgPre);
        return responseVO;
    }

    //分页使用响应对象
    public static<T> ResponseVO Success(int nowPage, int totalPage, T data, String imgPre){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(data);
        responseVO.setImgPre(imgPre);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);
        return responseVO;
    }

    public static<T> ResponseVO Success(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);
        return responseVO;
    }

    public static<T> ResponseVO serviceFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setMsg(msg);
        responseVO.setStatus(1);
        return  responseVO;
    }

    public static<T> ResponseVO appFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setMsg(msg);
        responseVO.setStatus(999);
        return  responseVO;
    }
}
